package brijesh.bhatt.controller;

import brijesh.bhatt.service.OrderService;
import brijesh.bhatt.to.OrderTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/online")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/orders", produces = "application/json")
    public List<OrderTO> getItems() {
        return orderService.getAllOrderItems();
    }

    @GetMapping(path = "/orders/{id}", produces = "application/json")
    public OrderTO getItemsById(@PathVariable("id") int id) {
        return orderService.getOrderById(id);
    }

    @PostMapping("/orders")
    public OrderTO saveItem(@Valid @RequestBody OrderTO orderTO) {
        System.out.println("saveItemsaveItem  &&&&&");
        System.out.println(orderTO.getItemTOList().size());
        return orderService.createItem(orderTO);
    }

    @PutMapping("/orders")
    public OrderTO updateItem(@Valid @RequestBody OrderTO orderTO) {
        return orderService.update(orderTO);
    }

    @DeleteMapping("/orders/{id}")
    private int deleteBook(@PathVariable("id") int id) {
        return orderService.delete(id);
    }
}
