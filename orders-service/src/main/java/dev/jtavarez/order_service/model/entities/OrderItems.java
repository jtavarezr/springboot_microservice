package dev.jtavarez.order_service.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItems {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String sku;
  private Double price;
  private Long quantity;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
}