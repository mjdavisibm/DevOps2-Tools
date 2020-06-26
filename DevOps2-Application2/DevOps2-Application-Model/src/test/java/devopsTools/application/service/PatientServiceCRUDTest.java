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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import devopsTools.application.domain.medical.MedicalTestBase;
import devopsTools.application.domain.medical.PatientDTO;
import devopsTools.application.serviceJSONImpl.PatientServiceJsonImpl;

@RunWith(SpringRunner.class)
//Used to force saves before gets, before update, before delete
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PatientServiceCRUDTest extends MedicalTestBase {

	@TestConfiguration
	static class TestContextConfiguration {
		@Bean
		public PatientService patientService() {
			return new PatientServiceJsonImpl();
		}
	}

	@Autowired
	private PatientService patientService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	@DirtiesContext
	public void checkA_create() throws Exception {
		List<PatientDTO> patients = getTestPatients();
		patientService.deleteAllPatients();
		patientService.createPatients(patients);

		assertTrue(patientService.getNumberOfPatients() == testCount);
		PatientDTO c = patientService.findByName(testPatient.getName());
		assertThat(c.getName()).isEqualTo(testPatient.getName());
	}

	@Test
	public void checkB_read() throws Exception {
		getTestPatients();
		PatientDTO c = patientService.findByName(testPatient.getName());
		assertThat(c.getName()).isEqualTo(testPatient.getName());
	}

	@Test
	@DirtiesContext
	public void checkC_update() throws Exception {
		getTestPatients();

		PatientDTO c = patientService.findByName(testPatient.getName());
		AddressDTO a = new AddressDTO(AddressType.BUSINESS, "99 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.addAddress(a);
		patientService.updatePatient(c);

		PatientDTO newC = patientService.findByName(testPatient.getName());
		assertThat(c.getName()).isEqualTo(testPatient.getName());

		assertThat(newC.hasAddress(a));
	}

	@Test
	@DirtiesContext
	public void checkD_delete() throws Exception {
		getTestPatients();
		long count = patientService.getNumberOfPatients();

		patientService.deletePatient(testPatient);
		assertTrue(patientService.getNumberOfPatients() == count - 1);
	}

}
