package dev.jtavarez.order_service.controllers;

import dev.jtavarez.order_service.model.dtos.OrderRequest;
import dev.jtavarez.order_service.model.dtos.OrderResponse;
import dev.jtavarez.order_service.services.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String placeOrder(@RequestBody OrderRequest orderRequest) {
    this.orderService.placeOrder(orderRequest);
    return "Order placed successfully";
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<OrderResponse> getOrders() {
    return this.orderService.getAllOrders();

  }
}
