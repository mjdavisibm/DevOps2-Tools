package devopsTools.application.service;

/*
 * This test uses a cobbled together in-memory service implementation that uses Json as
 * its basis.
 * It was designed to understand the Spring "Services" component better
 */
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import devopsTools.application.domain.medical.DoctorDTO;
import devopsTools.application.domain.medical.JsonTestTools;
import devopsTools.application.domain.medical.MedicalTestBase;
import devopsTools.application.domain.medical.PatientDTO;
import devopsTools.application.serviceJSONImpl.DoctorServiceJsonImpl;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@Slf4j
public class DoctorServiceTest extends MedicalTestBase {

	@TestConfiguration
	static class TestContextConfiguration {
		@Bean
		public DoctorService doctorService() {
			return new DoctorServiceJsonImpl();
		}
	}
	
	@Autowired
	private DoctorService doctorService;

	@Before
	public void setUp() throws Exception {
	}
	
	/*
	 * Used to hold a test patient that can be used in tests
	 */
	protected DoctorDTO testDoctor;
	protected int testCount;
	
	/*
	 * Used to get from a json file some test doctors
	 */
	protected List<DoctorDTO> getTestDoctors() {
		log.debug("Getting test patients from JSON FIle");
		TypeReference<List<DoctorDTO>> typeRef = new TypeReference<List<DoctorDTO>>() {
		};
		@SuppressWarnings("unchecked")
		List<DoctorDTO> doctors = (List<DoctorDTO>) (Object) JsonTestTools.getObjectsFromJsonFile(typeRef);

		testDoctor = doctors.get(0);
		testCount = doctors.size();

		return doctors;
	}

	@Test
	@DirtiesContext
	public void createDoctor_test() {
		getTestDoctors();
		doctorService.deleteAllDoctors();
		doctorService.createDoctor(testDoctor);

		assertTrue(doctorService.getNumberOfDoctors() == 1);
		DoctorDTO c = doctorService.findByName(testDoctor.getName());
		assertThat(c.getName()).isEqualTo(testDoctor.getName());
	}

	@Test
	@DirtiesContext
	public void updateDoctor_test() {
		getTestDoctors();

		DoctorDTO c = doctorService.findByName(testDoctor.getName());
		AddressDTO a = new AddressDTO(AddressType.BUSINESS, "99 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.addAddress(a);
		doctorService.updateDoctor(c);

		DoctorDTO newC = doctorService.findByName(testDoctor.getName());
		assertThat(c.getName()).isEqualTo(testDoctor.getName());

		assertThat(newC.hasAddress(a));
	}

	@Test
	@DirtiesContext
	public void deleteDoctor_test() {
		getTestDoctors();
		long count = doctorService.getNumberOfDoctors();

		doctorService.deleteDoctor(testDoctor);
		assertTrue(doctorService.getNumberOfDoctors() == count - 1);
	}

	@Test
	@DirtiesContext
	public void getDoctors_test() {
		getTestDoctors();
		List<DoctorDTO> patients = doctorService.getDoctors();
		assertTrue(patients.size() == testCount);
	}

	@Test
	@DirtiesContext
	public void getNumberOfDoctors_test() {
		getTestDoctors();
		int num = doctorService.getNumberOfDoctors();
		assertTrue(num == testCount);
	}
	
	@Test
	public void findByName_test() {
		getTestDoctors();
		DoctorDTO c = doctorService.findByName(testDoctor.getName());
		assertThat(c.getName()).isEqualTo(testDoctor.getName());
	}
		
	// Need to finish
	@Test
	public void addPatients_test() {
		getTestDoctors();
		List<PatientDTO> patients = getTestPatients();
		DoctorDTO doc = doctorService.findByName(testDoctor.getName());
		doctorService.addPatients(doc,patients);
	}
	
	// Need to finish
	@Test
	@DirtiesContext
	public void addPatient_test() {
		getTestDoctors();
		getTestPatients();
		DoctorDTO doc = doctorService.findByName(testDoctor.getName());
		doctorService.addPatient(doc,testPatient);
	}
	
	// Need to finish
	@Test
	@DirtiesContext
	public void removePatient_test() {
		getTestDoctors();
		getTestPatients();
		DoctorDTO doc = doctorService.findByName(testDoctor.getName());
		doctorService.addPatient(doc,testPatient);
		doctorService.removePatient(doc,testPatient);
	}

}
