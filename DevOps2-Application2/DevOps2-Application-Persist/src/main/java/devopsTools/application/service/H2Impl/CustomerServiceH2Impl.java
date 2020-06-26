package devopsTools.application.service.H2Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import devopsTools.application.data.CustomerRepository;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.service.PatientService;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerServiceH2Impl implements PatientService {

	@Autowired
	private CustomerRepository customerRepo;

	public boolean createCustomer(Customer customer) {
		Customer savedCustomer = customerRepo.save(customer);
		return savedCustomer == customer;
	}

	public void updateCustomer(Customer customer) {
		customerRepo.save(customer);
	}

	public void deleteCustomer(Customer customer) {
		customerRepo.delete(customer);

	}

	public List<Customer> getCustomers() {
		return (List<Customer>) customerRepo.findAll();
	}

	public void createCustomers(List<Customer> customers) {
		customerRepo.saveAll(customers);

	}

	public Customer findByName(NameDB name) {
		Optional<Customer> ocust = customerRepo.findByName(name);
		if (ocust.isPresent())
			return ocust.get();
		return null;
	}

	public int getNumberOfCustomers() {
		return (int) customerRepo.count();
	}

	public void deleteAllCustomers() {
		customerRepo.deleteAll();
	}

}
