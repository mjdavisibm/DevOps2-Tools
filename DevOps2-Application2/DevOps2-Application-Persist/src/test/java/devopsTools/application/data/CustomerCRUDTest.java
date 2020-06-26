package devopsTools.application.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.domain.AddressTest;
import devopsTools.application.domain.AddressTest.State;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.CustomerDataTestBase;
import lombok.extern.slf4j.Slf4j;

/*
 * Test that the data persistence layer is working
 * 1) It uses the domain layer objects
 * 2) Tests the CRUD level
 * 		Creates - Creates a Customer
 * 		Read - Read all Customer and Individual Customer
 * 		Update - Test different forms of update in preparation for PUT/PATCH
 * 			PUT - Update entire Customer Data, therefore entire Customer object is needed. 
 * 			PATCH - Updates a piece of the Customer, e.g. just the Address or Name
 * 		Delete - Delete a Customer using just Id
 */

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
//Used to force saves before gets, before update, before delete
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerCRUDTest extends CustomerDataTestBase {

	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * Note that due to forced ordering of tests we do not indicate that each test
	 * dirties the context, i.e. we do not mark each test with @DirtiesContext, as
	 * we want to use the inserting, and updates in future test
	 */
	@Test
	// Tests Create of CRUD
	public void checkA_create() {
		/*
		 * We force the Customer table to be cleared of all data
		 */
		customerRepository.deleteAll();
		// Saves all Test customers and confirms count
		List<Customer> customers = getTestCustomers();
		customerRepository.saveAll(customers);
		assertTrue(customerRepository.count() == testCount);
	}

	@Test
	// Tests Read of CRUD
	public void checkB_read() {
		getTestCustomers();

		// Read all test Customers from repository and confirm they match the count
		List<Customer> customers = (List<Customer>) customerRepository.findAll();
		assertTrue(customers.size() > 0);

		// Get the Test Customer by Id from the repository
		Optional<Customer> c = customerRepository.findById(testCustomer.getId());
		assertThat(c.isPresent());
		log.debug("Test Customer: {}", testCustomer.toPrettyPrintJson());
		log.trace("DB Customer: {}", c.get().toPrettyPrintJson());
		assertThat(c.get()).isEqualTo(testCustomer);

		// Get the Test Customer by Name from the repository
		c = customerRepository.findByName(testCustomer.getName());
		assertTrue(c.isPresent());
		assertThat(c.get()).isEqualTo(testCustomer);
	}

	@Test
	// Tests Update of CRUD
	public void checkC_update() {
		getTestCustomers();
		long initialCount = customerRepository.count();
		log.debug("Number of customers before update: {}", initialCount);
		assertTrue(initialCount > 0);

		// Reads the entire Customer, changes the address and Updates the entire
		// customer
		Optional<Customer> ocust = customerRepository.findById(testCustomer.getId());
		assertTrue(ocust.isPresent());
		Customer c = ocust.get();
		AddressTest a = new AddressTest("100 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.setAddress(a);
		c = customerRepository.save(c);

		long newCount = customerRepository.count();
		log.debug("Number of Customers after update: {}", newCount);
		assertThat(initialCount == newCount);

		Optional<Customer> ocust2 = customerRepository.findById(testCustomer.getId());
		assertTrue(ocust2.isPresent());
		c = ocust.get();
		log.debug("Changed Customer: {}", c);
		assertThat(c.getAddress()).isEqualTo(a);

		// Using the same customer, update the Name and triggers that save
		NameDB name = new NameDB("Test", "My", "Name", "Tester");
		customerRepository.save(c.getId(), name);

		newCount = customerRepository.count();
		log.debug("Number of Customers after Name update: {}", newCount);
		assertThat(initialCount == newCount);

		Optional<Customer> updateC = customerRepository.findById(c.getId());
		assertThat(updateC.isPresent());
		c = updateC.get();
		assertThat(c.getName()).isEqualTo(name);

	}

	@Test
	// Test Delete of CRUD
	public void checkD_delete() {
		getTestCustomers();
		long count = customerRepository.count();
		// Delete the testCustomer and ensure count has gone down by one
		customerRepository.delete(testCustomer);
		assertTrue(customerRepository.count() == count - 1);
	}

}
