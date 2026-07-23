package com.ecommerce.controller.order_controller;

import com.ecommerce.entity.Order;
import com.sendgrid.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
