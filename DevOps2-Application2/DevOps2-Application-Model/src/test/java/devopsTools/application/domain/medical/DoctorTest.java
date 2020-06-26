package devopsTools.application.domain.medical;

/*
 * Just test that the load from JSON file is working and
 * uses this to check patients
 */
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import devopsTools.application.domain.NameDTO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mattdavis
 *
 */
@Slf4j
public class DoctorTest extends MedicalTestBase {

	/*
	 * Used to hold a test patient that can be used in tests
	 */
	protected DoctorDTO testDoctor;
	protected int testCount;
	
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
	/*
	 * Used to get from a json file some test doctor
	 */
	protected List<DoctorDTO> getTestDoctors() {
		log.debug("Getting test doctors from JSON FIle");
		TypeReference<List<DoctorDTO>> typeRef = new TypeReference<List<DoctorDTO>>() {
		};
		@SuppressWarnings("unchecked")
		List<DoctorDTO> doctors = (List<DoctorDTO>) (Object) JsonTestTools.getObjectsFromJsonFile(typeRef);

		testDoctor = doctors.get(0);
		testCount = doctors.size();

		return doctors;
	}
	
	@Test
	public void checkDoctor_test() {
		getTestDoctors();
		log.info("Doctor --> {}",testDoctor.toPrettyPrintJson());
		NameDTO name = new NameDTO("George", "", "Frankenstein", "Geo");
		assertTrue(testDoctor.getName().equals(name));
		AddressDTO addr = new AddressDTO(AddressType.BUSINESS, "1 Medical Blvd", "New York", State.NEW_YORK);
		assertTrue(testDoctor.hasAddress(addr));
	}

}
