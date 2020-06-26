package devopsTools.application.data.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import devopsTools.application.data.CustomerRepository;
import devopsTools.application.data.CustomizedCustomerRepository;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.db.NameDB;

public class CustomerRepositoryImpl implements CustomizedCustomerRepository{

	@Autowired
	private CustomerRepository customerRepo;
	
	public CustomerRepositoryImpl() {
		// TODO Auto-generated constructor stub
	}

	public Customer save(long customerId, NameDB name) {
		Optional<Customer> oCustomer = customerRepo.findById(customerId);
		if (!oCustomer.isPresent())
			return null;
		else {
			Customer customer = oCustomer.get();
			customer.setName(name);
			return customerRepo.save(customer);
		}
	}

}
