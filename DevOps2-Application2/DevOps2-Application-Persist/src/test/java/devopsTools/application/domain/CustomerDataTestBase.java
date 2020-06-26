package devopsTools.application.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.BeforeClass;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import devopsTools.application.domain.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CustomerDataTestBase {

	/*
	 * Used to hold a test customer that can be used in tests
	 */
	protected Customer testCustomer;
	protected int testCount;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	/*
	 * Used to get test customers from a json File
	 */
	
	private List<Customer> loadJSONData() {
		String filename = "/json/Customers.json";
		List<Customer> allObjs = null;
		log.debug("Loading '" + Customer.class + "' from Json file: " + filename);
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Customer>> typeReference = new TypeReference<List<Customer>>() {
		};
		InputStream inputStream = TypeReference.class.getResourceAsStream(filename);
		try {
			allObjs = mapper.readValue(inputStream, typeReference);
			if (log.isDebugEnabled()) {
				for (Customer obj : allObjs) {
					log.trace("Line Item: " + obj);
				}
			}
		} catch (IOException e) {
			log.warn("Unable to load JSON file: " + filename + "\n Error Message: " + e.getMessage());
		}
		return allObjs;
	} 
	/*
	 * Used to get from a json file some test customers
	 */
	protected List<Customer> getTestCustomers() {
		log.debug("Getting test customers from JSON FIle");
		List<Customer> customers = loadJSONData();
		testCustomer = customers.get(0);
		testCount = customers.size();
		return customers;
	}
	
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
