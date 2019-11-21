package tejaswini.myretail.services;

import tejaswini.myretail.domain.price.CurrentPrice;
import tejaswini.myretail.domain.price.Product;

public interface ProductService {

  Product getProduct(int productId);

  void putProductPrice(CurrentPrice currentPrice);

}
