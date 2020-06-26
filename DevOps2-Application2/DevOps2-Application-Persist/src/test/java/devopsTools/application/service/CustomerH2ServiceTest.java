package devopsTools.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.domain.AddressTest;
import devopsTools.application.domain.AddressTest.State;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.CustomerDataTestBase;
import devopsTools.application.service.H2Impl.CustomerServiceH2Impl;

@RunWith(SpringRunner.class)
@DataJpaTest
//@ContextConfiguration(classes = ConfigureH2ServiceImpl.class)
public class CustomerH2ServiceTest extends CustomerDataTestBase {

	@TestConfiguration
	static class TestContextConfiguration {
		@Bean
		public PatientService customerService() {
			return new CustomerServiceH2Impl();
		}
	}
	
	@Autowired
	private PatientService customerService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@DirtiesContext
	public void create_test() throws Exception {
		List<Customer> customers = getTestCustomers();
		customerService.deleteAllCustomers();
		customerService.createCustomers(customers);

		assertTrue(customerService.getNumberOfCustomers() == testCount);
		Customer c = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	public void read_test() throws Exception {
		getTestCustomers();
		Customer c = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
	}

	@Test
	@DirtiesContext
	public void update_test() throws Exception {
		getTestCustomers();

		Customer c = customerService.findByName(testCustomer.getName());
		AddressTest a = new AddressTest("99 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.setAddress(a);
		customerService.updateCustomer(c);

		Customer newC = customerService.findByName(testCustomer.getName());
		assertThat(c.getName()).isEqualTo(testCustomer.getName());
		assertThat(newC.getAddress()).isEqualTo(a);
	}

	@Test
	@DirtiesContext
	public void delete_test() throws Exception {
		getTestCustomers();
		long count = customerService.getNumberOfCustomers();

		customerService.deleteCustomer(testCustomer);
		assertTrue(customerService.getNumberOfCustomers() == count - 1);
	}

}
