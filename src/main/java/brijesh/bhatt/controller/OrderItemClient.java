package brijesh.bhatt.controller;

import brijesh.bhatt.to.ItemTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "item-client", url = "http://localhost:8081/online")
public interface OrderItemClient {

    @GetMapping(path = "/items", produces = "application/json")
    List<ItemTO> getItems();

    @GetMapping(path = "/items/{id}", produces = "application/json")
    ItemTO getItemsById(@PathVariable("id") int id);

    @PostMapping("/items")
    ItemTO saveItem(@Valid @RequestBody ItemTO itemTO);

    @PutMapping("/items")
    ItemTO updateItem(@Valid @RequestBody ItemTO itemTO) ;

    @DeleteMapping("/items/{id}")
    int deleteBook(@PathVariable("id") int id) ;
}