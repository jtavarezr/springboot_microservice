package dev.jtavarez.inventory_service.services;

import dev.jtavarez.inventory_service.model.dtos.BaseResponse;
import dev.jtavarez.inventory_service.model.dtos.OrderItemsRequest;
import dev.jtavarez.inventory_service.model.entities.Inventory;
import dev.jtavarez.inventory_service.repositories.InventoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
  private final InventoryRepository inventoryRepository;

  public boolean isInstock(String sku) {
    var inventory = inventoryRepository.findBySku(sku);
    return inventory.filter(v -> v.getQuantity() > 0).isPresent();
  }

  public String itemStock(String sku) {
    var inventory = inventoryRepository.findBySku(sku);
    return inventory.stream().map(m -> m.getQuantity().toString()).reduce("", (a, b) -> a + b);
  }

  public BaseResponse areInStock(List<OrderItemsRequest> orderItemRequests) {
    var errorList = new ArrayList<String>();

    List<String> skus = orderItemRequests.stream().map(OrderItemsRequest::getSku).toList();
    List<Inventory> inventoryList = inventoryRepository.findBySkuIn(skus);

    orderItemRequests.forEach(orderItemRequest -> {
      var inventory = inventoryList.stream().filter(v -> v.getSku().equals(orderItemRequest.getSku())).findFirst();
      if (inventory.isEmpty()) {
        errorList.add("SKU " + orderItemRequest.getSku() + " not found");
      } else if (inventory.get().getQuantity() < orderItemRequest.getQuantity()) {
        errorList.add("SKU " + orderItemRequest.getSku() + " is out of stock");
      }
    });
    return errorList.size() > 0 ? new BaseResponse(errorList.toArray(new String[0])) : new BaseResponse(null);

  }
}
