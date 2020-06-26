package devopsTools.application.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.DevOpsApplicationDataApplication;
import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import devopsTools.application.domain.PatientDataTestBase;
import devopsTools.application.domain.db.AddressDB;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.PatientDTO;
import devopsTools.application.domain.medical.db.PatientDB;
import lombok.extern.slf4j.Slf4j;

/*
 * Test that the data persistence layer is working
 * 1) It uses the domain layer objects
 * 2) Tests the CRUD level
 * 		Creates - Creates a Patient
 * 		Read - Read all Patient and Individual Patient
 * 		Update - Test different forms of update in preparation for PUT/PATCH
 * 			PUT - Update entire Patient Data, therefore entire Patient object is needed. 
 * 			PATCH - Updates a piece of the Patient, e.g. just the Address or Name
 * 		Delete - Delete a Patient using just Id
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= DevOpsApplicationDataApplication.class)
//Used to force saves before gets, before update, before delete
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PatientCRUDTest extends PatientDataTestBase {
	
	@Autowired
	private PatientRepository patientRepository;

	@Before
	public void setUp() throws Exception {
	}

	/*
	 * Note that due to forced ordering of tests we do not indicate that each test
	 * dirties the context, i.e. we do not mark each test with @DirtiesContext, as
	 * we want to use the inserting, and updates in future test
	 */
	@Test
	@DirtiesContext
	// Tests Create of CRUD
	public void checkA_create() {
		/*
		 * We force the Patient table to be cleared of all data
		 */
		patientRepository.deleteAll();
		// Saves all Test patients and confirms count
		List<PatientDTO> patients = getTestPatients();
		
		List<PatientDB> patientsDB = patients.stream()
				.map(patient -> PatientDB.DTOtoDB(patient)).collect(Collectors.toList());
		
		patientRepository.saveAll(patientsDB);
		assertTrue(patientRepository.count() == testCount);
	}

	@Test
	// Tests Read of CRUD
	public void checkB_read() {
		getTestPatients();

		// Read all test Patients from repository and confirm they match the count
		List<PatientDB> patients = (List<PatientDB>) patientRepository.findAll();
		assertTrue(patients.size() > 0);

		// Get the Test Patient by Id from the repository
		Optional<PatientDB> c = patientRepository.findBySsn(testPatient.getSsn());
		assertThat(c.isPresent());
		log.debug("Test Patient: {}", testPatient.toPrettyPrintJson());
		log.trace("DB Patient: {}", c.get().toPrettyPrintJson());
		assertThat(c.get()).isEqualTo(testPatient);

		// Get the Test Patient by Name from the repository
		c = patientRepository.findByName(NameDB.DTOtoDB(testPatient.getName()));
		assertTrue(c.isPresent());
		assertThat(c.get()).isEqualTo(testPatient);
	}

	@Test
	@DirtiesContext
	// Tests Update of CRUD
	public void checkC_update() {
		getTestPatients();
		long initialCount = patientRepository.count();
		log.debug("Number of patients before update: {}", initialCount);
		assertTrue(initialCount > 0);

		// Reads the entire Patient, changes the address and Updates the entire
		// patient
		Optional<PatientDB> opatient = patientRepository.findBySsn(testPatient.getSsn());
		assertTrue(opatient.isPresent());
		PatientDB c = opatient.get();
		AddressDB a = new AddressDB(AddressType.BUSINESS,"100 Changed Address", "Change Me", State.CALIFORNIA, "99999");
		c.addAddress(a);
		c = patientRepository.save(c);

		long newCount = patientRepository.count();
		log.debug("Number of Patients after update: {}", newCount);
		assertThat(initialCount == newCount);

		Optional<PatientDB> opatient2 = patientRepository.findBySsn(testPatient.getSsn());
		assertTrue(opatient2.isPresent());
		c = opatient.get();
		log.debug("Changed Patient: {}", c);
		assertThat(c.hasAddress(a));

		// Using the same patient, update the Name and triggers that save
		NameDB name = new NameDB("Test", "My", "Name", "Tester");
		patientRepository.updateName(c.getId(), name);

		newCount = patientRepository.count();
		log.debug("Number of Patients after Name update: {}", newCount);
		assertThat(initialCount == newCount);

		Optional<PatientDB> updateC = patientRepository.findById(c.getId());
		assertThat(updateC.isPresent());
		c = updateC.get();
		assertThat(c.getName()).isEqualTo(name);
	}

	@Test
	@DirtiesContext
	// Test Delete of CRUD
	public void checkD_delete() {
		getTestPatients();
		long count = patientRepository.count();
		// Delete the testPatient and ensure count has gone down by one
		Optional<PatientDB> p = patientRepository.findBySsn(testPatient.getSsn());
		assertTrue(p.isPresent());
		patientRepository.delete(p.get());
		assertTrue(patientRepository.count() == count - 1);
	}
	
	@Test
	@DirtiesContext
	// Test Delete of CRUD
	public void check_delete() {

		
	}
	
	
	

}
