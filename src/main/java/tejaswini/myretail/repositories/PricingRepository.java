package tejaswini.myretail.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import tejaswini.myretail.domain.price.CurrentPrice;

@Repository
public interface PricingRepository extends CassandraRepository<CurrentPrice, Integer> { }
