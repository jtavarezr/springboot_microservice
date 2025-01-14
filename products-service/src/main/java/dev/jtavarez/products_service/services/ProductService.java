package dev.jtavarez.products_service.services;

import dev.jtavarez.products_service.model.dtos.ProductRequest;
import dev.jtavarez.products_service.model.dtos.ProductResponse;
import dev.jtavarez.products_service.model.entities.Product;
import dev.jtavarez.products_service.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  public void addProduct(ProductRequest productRequest) {
    var product = Product.builder()
        .sku(productRequest.getSku())
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .price(productRequest.getPrice())
        .status(productRequest.getStatus())
        .build();

    productRepository.save(product);
    log.info("Added product: {}", product);
  }

  public List<ProductResponse> getAllProducts() {
    var products = productRepository.findAll();

    return products.stream().map(this::mapToProductResponse).toList();
  }

  private ProductResponse mapToProductResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .sku(product.getSku())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .status(product.getStatus())
        .build();
  }

}
