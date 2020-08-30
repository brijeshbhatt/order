package com.app.exception;

public class OrderNotFoundException extends RuntimeException {

   public OrderNotFoundException(int id){
       super("Order with id: "+ id +" does not present in DB.");
   }

}
