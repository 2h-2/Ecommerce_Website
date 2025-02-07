package com.ecomerce.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.ecomerce.backend.model.entity.Order;
import com.ecomerce.backend.model.entity.OrderItem;
import com.ecomerce.backend.repository.OrderItemRepo;
import com.ecomerce.backend.repository.OrderRepo;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    public enum OrderStatusEnum{
      NEW(0, "New OrderMain"),
      FINISHED(1, "Finished"),
      CANCELED(2, "Canceled");

    private  int code;
    private String msg;

    public int getCode(){
      return code;
    }

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    }

    public List<Order> getOrders(Long id){
      List<Order> orders =  orderRepo.findByUserId(id);
        if(orders.isEmpty()){
            return null;
        }
        return orders;
    }

    public List<Order> getAllOrders(){
      List<Order> orders =  orderRepo.findAll();
        if(orders.isEmpty()){
            return null;
        }
        return orders;
    }

    public Order findOne(Long orderId) {
      Optional<Order> orderMain = orderRepo.findById(orderId);
      if(orderMain == null) {
        throw new IllegalArgumentException("Order Not Found!");
      }
      return orderMain.get();
  }

    public Order get(Long id){
        return findOne(id);
    }

    @Transactional
    public Order finish(Long orderId) {
        Order orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new IllegalArgumentException("ORDER_STATUS_ERROR");
        }

        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepo.save(orderMain);
        return orderRepo.findById(orderId).get();
    }

    public Order cancel(Long orderId) {
      Order orderMain = findOne(orderId);
      if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
          throw new IllegalArgumentException("ORDER_STATUS_ERROR");
      }

      orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
      orderRepo.save(orderMain);

      return orderRepo.findById(orderId).get();
  }

  public List<OrderItem> getAllItems(Long id){
      return orderItemRepo.findByOrderId(id);
  }
}
