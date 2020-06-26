package devopsTools.application.data;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import devopsTools.application.domain.Customer;
import devopsTools.application.domain.db.NameDB;

@Repository
@Transactional
@RepositoryRestResource(path = "customers", collectionResourceRel = "customers")
public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomizedCustomerRepository {

	public Optional<Customer> findByName(NameDB name);

	@Query("Select c From Customer c where street like '%Avenue'")
	List<Customer> customersOnAvenues();

}
