package tejaswini.myretail.controllers;

import tejaswini.myretail.domain.price.CurrentPrice;
import tejaswini.myretail.domain.price.CurrentPriceJson;
import tejaswini.myretail.domain.price.Product;
import tejaswini.myretail.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyRetailController {

  private final ProductService productService;

  @Autowired
  public MyRetailController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("products/{id}")
  public Product getProduct(@PathVariable("id") int id) {
    return productService.getProduct(id);
  }

  @PutMapping("products/{id}")
  public void updateProductPricing(@PathVariable("id") int id, @RequestBody CurrentPriceJson currentPriceJson) {
    productService.putProductPrice(new CurrentPrice(id, currentPriceJson.getValue(), currentPriceJson.getCurrencyCode()));
  }

}
