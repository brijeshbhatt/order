package brijesh.bhatt.service;

import brijesh.bhatt.controller.OrderItemClient;
import brijesh.bhatt.entity.Order;
import brijesh.bhatt.exception.OrderNotFoundException;
import brijesh.bhatt.repository.OrderRepository;
import brijesh.bhatt.to.ItemTO;
import brijesh.bhatt.to.OrderTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        logger.info("getOrderById is being called.");
        Optional<Order> result = orderRepository.findById(id);
        if (result.isPresent()) {
            return loadOrderTO(result.get());
        } else {
            throw new OrderNotFoundException(id);
        }
    }

    private OrderTO loadOrderTO(Order order) {
        List<Integer> itemIds = orderRepository.getItemId(order.getId());
        List<ItemTO> itemTOS = new ArrayList<>();
        for (Integer itemId : itemIds) {
            itemTOS.add(itemClient.getItemsById(itemId));
        }
        OrderTO response = mapOrderTO(order);
        response.setItemTOList(itemTOS);
        return response;
    }

    public OrderTO createItem(OrderTO orderTO) {
        Order order = orderRepository.save(mapOrderEntity(orderTO));
        OrderTO response = mapOrderTO(order);
        List<ItemTO> items = new ArrayList<>();
        if (orderTO.getItemTOList().size() > 0) {
            System.out.println("orderTO.getItemTOList().size() > 0");
            for (ItemTO item : orderTO.getItemTOList()) {
                ItemTO itemTO = itemClient.saveItem(item);
                orderRepository.insertOrderItemMapping(order.getId(), itemTO.getId());
                items.add(itemTO);
            }
            response.setItemTOList(items);
        }
        return response;
    }

    public OrderTO update(OrderTO orderTO) {
        if (orderRepository.existsById(orderTO.getOrderId())) {
            Order order = mapOrderEntity(orderTO);
            order.setId(orderTO.getOrderId());
            Order orderItem = orderRepository.save(order);
            return mapOrderTO(orderItem);
        } else {
            throw new OrderNotFoundException(orderTO.getOrderId());
        }
    }

    public int delete(int id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return id;
        } else {
            throw new OrderNotFoundException(id);
        }
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
