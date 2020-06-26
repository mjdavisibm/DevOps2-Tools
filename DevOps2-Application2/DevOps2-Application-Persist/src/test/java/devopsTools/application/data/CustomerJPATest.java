package devopsTools.application.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.domain.AddressTest;
import devopsTools.application.domain.AddressTest.State;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.CustomerDataTestBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerJPATest extends CustomerDataTestBase {

	@Autowired
	private CustomerRepository repo;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void checkA_findById_CustomerExists() throws Exception {
		getTestCustomers();
		long id = testCustomer.getId();
		Optional<Customer> c = repo.findById(id);
		assertTrue(c.isPresent());
	}

	@Test
	public void checkA_findById_CustomerNotExist() throws Exception {
		Optional<Customer> c = repo.findById(77777L);
		assertFalse(c.isPresent());
	}

	@Test
	public void checkA_findAll() throws Exception {
		List<Customer> c = (List<Customer>) repo.findAll();
		assertTrue(c.size() > 0);
	}

	@Test
	public void checkA_pagination() throws Exception {
		PageRequest pageRequest = PageRequest.of(0, 3);
		Page<Customer> firstPage = repo.findAll(pageRequest);
		log.debug("First Page -> {}", firstPage.getContent());
		assertEquals(firstPage.getNumberOfElements(),3);

		Pageable secondPageable = firstPage.nextPageable();
		Page<Customer> secondPage = repo.findAll(secondPageable);
		assertEquals(secondPage.getNumberOfElements(),3);
		log.debug("Second Page -> {}", secondPage.getContent());
		
		Pageable thirdPageable = secondPage.nextPageable();
		Page<Customer> thirdPage = repo.findAll(thirdPageable);
		assertEquals(thirdPage.getNumberOfElements(),2);
		log.debug("Third Page -> {}", thirdPage.getContent());

	}

	@Test
	public void checkA_findByName() throws Exception {
		getTestCustomers();
		Optional<Customer> c = repo.findByName(testCustomer.getName());
		assertTrue(c.isPresent());
		assertEquals(c.get(), testCustomer);
	}
	
	@Test
	public void checkA_findCustomersOnAvenues() {
		List<Customer> c = repo.customersOnAvenues();
		assertTrue(c.size() == 2);
	}

	/// Test below this line dirty the context
	
	@Test
	@DirtiesContext
	public void checkB_sortedCustomers() throws Exception {
		Sort sort = Sort.by(Sort.Direction.DESC, "name.lastName");
		List<Customer> customers = repo.findAll(sort);
		Customer lastInAlphabet = customers.get(0);
		assertEquals(lastInAlphabet.getName().getLastName(), "Zulu");
		log.debug("Sorted Customers -> {}", customers);
	}
	
	@Test
	@DirtiesContext
	public void checkB_save() throws Exception {
		getTestCustomers();
		long initialCount = repo.count();
		log.debug("Number of customers before save: {}", initialCount);

		// Ensure the Database creates a new entity
		testCustomer.setId(0L);
		Customer c = repo.save(testCustomer);
		log.debug("Saved Customer: {}", c);

		long newCount = repo.count();
		log.debug("Number of Customers after save: {}", newCount);
		assertTrue(newCount == initialCount + 1);
	}

	@Test
	@DirtiesContext
	public void checkC_update() throws Exception {
		getTestCustomers();
		long initialCount = repo.count();
		log.debug("Number of customers before update: {}", initialCount);

		// Retrieve a Customer, change Address, update DB
		Optional<Customer> oc = repo.findById(testCustomer.getId());
		assertTrue(oc.isPresent());
		Customer c = oc.get();
		AddressTest addr = new AddressTest("100 I have been changed", "somewhere", State.CALIFORNIA, "00000");
		c.setAddress(addr);
		repo.save(c);
		
		long newCount = repo.count();
		log.debug("Number of Customers after update: {}", newCount);
		assertEquals(newCount,initialCount);		
		

		// Now just update the Name
		NameDB name = new NameDB("New", "To", "Us", "Newbie");
		repo.save(c.getId(),name);

		newCount = repo.count();
		log.debug("Number of Customers after Second update: {}", newCount);
		assertEquals(newCount,initialCount);
		
	}

}
