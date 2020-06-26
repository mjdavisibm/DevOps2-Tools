package devopsTools.application.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import devopsTools.application.DevOpsApplicationDataApplication;
import devopsTools.application.domain.PatientDataTestBase;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.PatientDTO;
import devopsTools.application.domain.medical.db.PatientDB;
import lombok.extern.slf4j.Slf4j;

/*
 * Test The CustomerRepository
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes= DevOpsApplicationDataApplication.class)
@Slf4j
public class PatientRepositoryTest extends PatientDataTestBase {
	
	@Autowired
	private PatientRepository patientRepository;

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	@Transactional
	public void findByName_test() {
		getTestPatients();
		NameDB name = testPatient.getName();
		Optional<PatientDB> patient = patientRepository.findByName(name);
		assertThat(patient.isPresent());
		log.info("Found Patient --> {}",patient.get());
	}
	
	@Test
	public void customersOnAvenues_test() {
		List<PatientDB> patients = patientRepository.patientsOnAvenues();
		for( PatientDB p : patients)
			log.info("{}",p.toString());
		assertThat(patients.size() == 2);
	}

	@Test
	// Test the Patch method
	public void updateName_test() {
		getTestPatients();
		NameDB name = new NameDB("Substituted","Complete","Name","sub");	
		patientRepository.updateName(testPatient.getId(), name);
		Optional<PatientDTO> patient = patientRepository.findById(testPatient.getId());
		assertThat(patient.isPresent());
		assertEquals(name,patient.get().getName());
		
		PatientDTO c = patientRepository.updateName(54635l, name);
		assertNull(c);
		
	}

}
