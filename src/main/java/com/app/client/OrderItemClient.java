package com.app.client;

import com.app.to.ItemTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "order-item", url = "${itemservice.url}")
public interface OrderItemClient {

    @GetMapping(path = "/items", produces = "application/json")
    public List<ItemTO> getItems();

    @GetMapping(path = "/items/{id}", produces = "application/json")
    public ItemTO getItemsById(@PathVariable("id") Integer id);

    @PostMapping("/items")
    public ItemTO saveItem(@Valid @RequestBody ItemTO itemTO);

    @PutMapping("/items")
    public ItemTO updateItem(@Valid @RequestBody ItemTO itemTO);

    @DeleteMapping("/items/{id}")
    public int deleteItem(@PathVariable("id") Integer id);
}