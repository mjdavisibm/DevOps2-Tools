/**
 * 
 */
package devopsTools.application.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mattdavis
 *
 */
@Slf4j
public class NameTest extends DomainBaseTest {

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
	public void name_test() {

		NameDTO name1 = new NameDTO("James", "M", "Test", "Jim");
		log.info("Name --> {}", name1);
		NameDTO name2 = new NameDTO("Samantha", "Jane", "Test", "Sam");
		assertFalse(name1.equals(name2));

		// First and Last name are the ones tested for
		name2 = new NameDTO("James", "Test");
		assertTrue(name1.equals(name2));

		// First and Last name are the ones tested for
		name2 = new NameDTO("James", "R", "Test");
		assertFalse(name1.equals(name2));
	}

}
