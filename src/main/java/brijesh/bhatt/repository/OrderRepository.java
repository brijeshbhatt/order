package brijesh.bhatt.repository;

import brijesh.bhatt.entity.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO order_item_mapping(order_id, item_id) \n"
                   + "VALUES (:orderId, :itemId)", nativeQuery = true)
    void insertOrderItemMapping(@Param("orderId") Integer orderId, @Param("itemId") Integer itemId);

    @Query(value = "SELECT item_id  FROM ORDER_ITEM_MAPPING  where order_id=:orderId", nativeQuery = true)
    List<Integer> getItemId(@Param("orderId") Integer orderId);

}