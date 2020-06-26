/**
 * 
 */
package devopsTools.application.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mattdavis
 *
 */
@Slf4j
public class PersonTest extends DomainBaseTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	private PersonDTO generateTestPerson() {
		NameDTO name1 = new NameDTO("James", "M", "Test", "Jim");
		AddressDTO addr1 = new AddressDTO(AddressType.HOME, "123 My Street", "Denver", State.COLORADO, "80201");
		PersonDTO person = new PersonDTO("550-55-8800",name1,addr1);
		log.info("Person --> {}", person);
		return person;
	}

	@Test
	public void personAddresses_test() {
		PersonDTO person = generateTestPerson();
		AddressDTO addr1 = new AddressDTO(AddressType.BUSINESS, "987 Colorado Blvd", "Denver", State.COLORADO, "80237");
		person.addAddress(addr1);
		assertTrue(person.getAddresses().size() == 2);

		person.removeAddress(addr1);
		assertTrue(person.getAddresses().size() == 1);
	}
	
	@Test
	public void personSsn_test() {
		PersonDTO person = generateTestPerson();
		assertTrue(person.isValidSSN(person.getSsn()));
		String testSSN = "12345-56-999";;
		assertFalse(person.isValidSSN(testSSN));
	}

}
