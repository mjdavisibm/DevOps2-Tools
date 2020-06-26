package devopsTools.application.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import devopsTools.application.DevOpsApplicationDomainApplication;
import devopsTools.application.domain.AddressTest;
import devopsTools.application.domain.AddressTest.State;
import devopsTools.application.domain.Customer;
import devopsTools.application.domain.CustomerDataTestBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DevOpsApplicationDomainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerRestTest extends CustomerDataTestBase {

	@LocalServerPort
	private int port;

	private TestRestTemplate template = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	@Before
	public void setupJSONAcceptType() {
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}

	private String createUrl(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	public void getGETCustomer() throws Exception {
		getTestCustomers();
		String uri = "/customers/" + testCustomer.getId();

		ResponseEntity<String> response = template.exchange(createUrl(uri), HttpMethod.GET,
				new HttpEntity<String>(null, headers), String.class);
		int status = response.getStatusCodeValue();
		assertEquals(200, status);

		log.trace("Actual customer: {}", response.getBody());
		log.trace("Expected customer: {}", testCustomer.toPrettyPrintJson());
		String expected = super.mapToJson(testCustomer).replaceAll("\"id\":[0-9]*,", "");
		log.trace("Expected json: {}", expected);
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@DirtiesContext
	public void createPOSTCustomer() throws Exception {
		getTestCustomers();
		String uri = "/customers";

		testCustomer.setId(0L);
		ResponseEntity<String> response = template.exchange(createUrl(uri), HttpMethod.POST,
				new HttpEntity<Customer>(testCustomer, headers), String.class);
		int status = response.getStatusCodeValue();
		assertEquals(201, status);

		log.trace("Actual customer: {}", response.getBody());
		log.trace("Expected customer: {}", testCustomer.toPrettyPrintJson());
		String expected = super.mapToJson(testCustomer).replaceAll("\"id\":[0-9]*,", "");
		log.trace("Expected json: {}", expected);
		JSONAssert.assertEquals(expected, response.getBody(), false);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		assertTrue(actual.contains(uri + "/" + 1));
	}

	@Test
	@DirtiesContext
	public void updatePUTCustomer() throws Exception {
		getTestCustomers();
		String uri = "/customers/" + testCustomer.getId();

		AddressTest a = new AddressTest("100 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		testCustomer.setAddress(a);
		ResponseEntity<String> response = template.exchange(createUrl(uri), HttpMethod.PUT,
				new HttpEntity<Customer>(testCustomer, headers), String.class);
		int status = response.getStatusCodeValue();
		assertEquals(200, status);

		log.trace("Actual customer: {}", response.getBody());
		log.trace("Expected customer: {}", testCustomer.toPrettyPrintJson());
		String expected = super.mapToJson(testCustomer).replaceAll("\"id\":[0-9]*,", "");
		log.trace("Expected json: {}", expected);
		JSONAssert.assertEquals(expected, response.getBody(), false);

		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		assertTrue(actual.contains(uri));

	}

	@Test
	public void updatePATCHCustomer() throws Exception {
		getTestCustomers();
		String uri = "/customers/" + testCustomer.getId();
		AddressTest a = new AddressTest("100 Changed Address", "Change Me", State.CALIFORNIA, "99999");

	}

	@Test
	@DirtiesContext
	public void deleteDELETECustomer() throws Exception {
		getTestCustomers();
		String uri = "/customers/" + testCustomer.getId();
		ResponseEntity<String> response = template.exchange(createUrl(uri), HttpMethod.DELETE,
				new HttpEntity<String>(null, headers), String.class);
		int status = response.getStatusCodeValue();
		assertEquals(204, status);
		// need to see if delete should indeed return object deleted)
//		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
//		assertTrue(actual.contains(uri));
	}

}
