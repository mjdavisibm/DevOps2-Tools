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
public class AddressTest extends DomainBaseTest {

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

	@Test
	public void address_test() {
		AddressDTO addr1 = new AddressDTO(AddressType.HOME, "123 My Street", "Denver", State.COLORADO, "80201");
		log.info("Address --> {}", addr1);
		AddressDTO addr2 = new AddressDTO(AddressType.HOME, "123 My Street", "Denver", State.COLORADO, "80202");
		assertFalse(addr1.equals(addr2));

		// If zip code is missing in either address then just use the street, city,
		// state
		addr2 = new AddressDTO(AddressType.HOME, "123 My Street", "Denver", State.COLORADO);
		assertTrue(addr1.equals(addr2));
	}

}
