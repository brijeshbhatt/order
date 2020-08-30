package com.app.service;

import com.app.client.OrderItemClient;
import com.app.entity.Order;
import com.app.exception.OrderNotFoundException;
import com.app.repository.OrderRepository;
import com.app.to.ItemTO;
import com.app.to.OrderTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemClient itemClient;

    public List<OrderTO> getAllOrderItems() {
        logger.info("getAllOrderItems is being called.");
        List<OrderTO> items = new ArrayList();
        orderRepository.findAll().forEach(item -> items.add(loadOrderTO(item))
        );
        return items;
    }

    public OrderTO getOrderById(int id) {
        logger.info("getOrderById is being called for id:{}", id);
        Optional<Order> result = orderRepository.findById(id);
        if (result.isPresent()) {
            return loadOrderTO(result.get());
        } else {
            throw new OrderNotFoundException(id);
        }
    }

    @Transactional
    public OrderTO createItem(OrderTO orderTO) {
        logger.info("createItem is being called.");
        Order order = orderRepository.save(mapOrderEntity(orderTO));
        OrderTO response = mapOrderTO(order);
        List<ItemTO> items = new ArrayList<>();
        if (orderTO.getItemTOList().size() > 0) {
            for (ItemTO item : orderTO.getItemTOList()) {
                ItemTO itemTO = itemClient.saveItem(item);
                orderRepository.insertOrderItemMapping(order.getId(), itemTO.getId());
                items.add(itemTO);
            }
            response.setItemTOList(items);
        }
        return response;
    }

    @Transactional
    public OrderTO update(OrderTO orderTO) {
        if (orderRepository.existsById(orderTO.getOrderId())) {
            Order order = mapOrderEntity(orderTO);
            order.setId(orderTO.getOrderId());
            orderRepository.save(order);
            updateOrderItem(orderTO);
            return orderTO;
        } else {
            throw new OrderNotFoundException(orderTO.getOrderId());
        }
    }

    @Transactional
    public int delete(Integer orderId) {
        if (orderRepository.existsById(orderId)) {
            deleteAllExistingOrderItems(orderId);
            orderRepository.deleteOrderItemMapping(orderId);
            orderRepository.deleteById(orderId);
            return orderId;
        } else {
            throw new OrderNotFoundException(orderId);
        }
    }

    private void updateOrderItem(OrderTO orderTO) {
        deleteAllExistingOrderItems(orderTO.getOrderId());
        orderRepository.deleteOrderItemMapping(orderTO.getOrderId());
        List<ItemTO> items = new ArrayList<>();
        for (ItemTO item : orderTO.getItemTOList()) {
            ItemTO itemTO = itemClient.saveItem(item);
            orderRepository.insertOrderItemMapping(orderTO.getOrderId(), itemTO.getId());
            items.add(itemTO);
        }
        orderTO.setItemTOList(items);
    }

    private void deleteAllExistingOrderItems(Integer orderId) {
        getOrderItems(orderId).stream().forEach(item -> itemClient.deleteItem(item.getId()));
    }

    private OrderTO loadOrderTO(Order order) {
        OrderTO response = mapOrderTO(order);
        response.setItemTOList(getOrderItems(order.getId()));
        return response;
    }

    private List<ItemTO> getOrderItems(Integer orderId) {
        List<Integer> itemIds = orderRepository.getItemId(orderId);
        List<ItemTO> itemTOS = new ArrayList<>();
        for (Integer itemId : itemIds) {
            itemTOS.add(itemClient.getItemsById(itemId));
        }
        return itemTOS;
    }

    private OrderTO mapOrderTO(Order order) {
        return new OrderTO(order.getId(), order.getCustomerName(), order.getShippingAddress(), order.getTotal());
    }

    private Order mapOrderEntity(OrderTO to) {
        Order order = new Order();
        order.setCustomerName(to.getCustomerName());
        order.setShippingAddress(to.getShippingAddress());
        order.setTotal(to.getTotal());
        order.setOrderDate(new Date(System.currentTimeMillis()).toString());
        return order;
    }

}
