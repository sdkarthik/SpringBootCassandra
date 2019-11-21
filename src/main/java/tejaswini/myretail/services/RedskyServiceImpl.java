package tejaswini.myretail.services;

import static tejaswini.myretail.ExceptionConstants.NO_REDSKY_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tejaswini.myretail.domain.Redsky.RedskyResponse;

@Service
public class RedskyServiceImpl implements RedSkyService {

  private static final String REDSKY_HOST = "redsky.target.com";
  private static final String REDSKY_BASE = "/v2/pdp/tcin";
  private static final String EXCLUSIONS = "taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

  private final RestTemplate restTemplate;

  @Autowired
  public RedskyServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public RedskyResponse getRedSkyProduct(int id) {
    String uri = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(REDSKY_HOST)
        .path(REDSKY_BASE)
        .pathSegment(String.valueOf(id))
        .query("excludes=" + EXCLUSIONS).toUriString();
    try {
    	RedskyResponse body = restTemplate.getForEntity(uri, RedskyResponse.class).getBody();
      return body;
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new IllegalArgumentException(NO_REDSKY_FOUND + id, e);
      } else throw e;
    }
  }
}
