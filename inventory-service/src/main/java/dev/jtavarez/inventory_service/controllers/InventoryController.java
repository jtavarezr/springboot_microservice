package dev.jtavarez.inventory_service.controllers;

import dev.jtavarez.inventory_service.model.dtos.BaseResponse;
import dev.jtavarez.inventory_service.model.dtos.OrderItemsRequest;
import dev.jtavarez.inventory_service.services.InventoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
  private final InventoryService inventoryService;

  @GetMapping("/{sku}")
  @ResponseStatus(HttpStatus.OK)
  public boolean inStock(@PathVariable String sku) {
    return inventoryService.isInstock(sku);
  }

  @PostMapping("/in-stock")
  @ResponseStatus(HttpStatus.OK)
  public BaseResponse areInStock(@RequestBody List<OrderItemsRequest> orderItemRequests) {
    return inventoryService.areInStock(orderItemRequests);
  }

  @GetMapping("/quantity/{sku}")
  @ResponseStatus(HttpStatus.OK)
  public String getQuantity(@PathVariable String sku) {
    return inventoryService.itemStock(sku);
  }


}
