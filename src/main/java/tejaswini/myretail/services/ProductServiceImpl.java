package tejaswini.myretail.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Service;

import tejaswini.myretail.domain.Redsky.RedskyProduct;
import tejaswini.myretail.domain.price.*;

import static tejaswini.myretail.ExceptionConstants.*;

import java.util.Collection;

@Service
public class ProductServiceImpl implements ProductService {

  private final CassandraRepository<CurrentPrice, Integer> pricingRepository;
  private final RedSkyService redskyGateway;
  private final Collection<String> validCurrencyCodes;

  @Autowired
  public ProductServiceImpl(CassandraRepository<CurrentPrice, Integer> pricingRepository,
                            RedSkyService redskyGateway,
                            Collection<String> validCurrencyCodes) {
    this.pricingRepository = pricingRepository;
    this.redskyGateway = redskyGateway;
    this.validCurrencyCodes = validCurrencyCodes;
  }

  @Override
  public Product getProduct(int productId) {
    checkProductId(productId);
    RedskyProduct redskyProduct = redskyGateway.getRedSkyProduct(productId).getProduct();
    CurrentPrice currentPrice = pricingRepository.findById(productId).get();
    checkPricing(currentPrice);
    
    return new Product(
        productId,
        redskyProduct.getItem().getProductDescription().getTitle(),
        new CurrentPriceJson(currentPrice));
  }

  @Override
  public void putProductPrice(CurrentPrice currentPrice) {
    checkPricing(currentPrice);
    //make sure it's valid
    redskyGateway.getRedSkyProduct(currentPrice.getId());
    pricingRepository.save(currentPrice);
  }

  private void checkProductId(int productId) {
    if (productId < 1) throw new IllegalArgumentException(BAD_ID_MSG);
  }

  private void checkPricing(CurrentPrice currentPrice) {
    if (currentPrice.getId() < 1) throw new IllegalArgumentException(BAD_ID_MSG);
    if (currentPrice.getValue() <= 0) throw new IllegalArgumentException(BAD_PRICE_MSG);
    if (!validCurrencyCodes.contains(currentPrice.getCurrencyCode())) throw new IllegalArgumentException(BAD_CODE_MSG);
  }
}
