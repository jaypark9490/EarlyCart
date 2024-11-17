package server.earlycart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.earlycart.model.Order;
import server.earlycart.model.OrderItem;
import server.earlycart.service.OrderService;

import java.util.ArrayList;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("order")
    public String newOrder(@RequestParam String session, String cartId, String itemList) {
        return orderService.newOrder(session, cartId, itemList);
    }

    @GetMapping("order/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("order/list/{session}")
    public ArrayList<Order> getOrderListBysession(@PathVariable String session) {
        return orderService.getOrderListBySession(session);
    }

    @GetMapping("order/itemlist/{orderId}")
    public ArrayList<OrderItem> getOrderItemListByOrderId(@PathVariable String orderId) {
        return orderService.getOrderItemListByOrderId(orderId);
    }



}
