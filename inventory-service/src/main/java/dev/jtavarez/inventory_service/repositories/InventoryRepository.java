package dev.jtavarez.inventory_service.repositories;

import dev.jtavarez.inventory_service.model.entities.Inventory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

  Optional<Inventory> findBySku(String sku);

  List<Inventory> findBySkuIn(List<String> skus);
}
