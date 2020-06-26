package devopsTools.application.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import devopsTools.application.DevOpsApplicationDataApplication;
import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import devopsTools.application.domain.PatientDataTestBase;
import devopsTools.application.domain.db.AddressDB;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.db.PatientDB;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DevOpsApplicationDataApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PatientJPATest extends PatientDataTestBase {

	@Autowired
	private PatientRepository repo;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void checkA_findById_PatientExists() throws Exception {
		getTestPatients();
		long id = testPatient.getId();
		Optional<PatientDB> c = repo.findById(id);
		assertTrue(c.isPresent());
	}

	@Test
	public void checkA_findById_PatientNotExist() throws Exception {
		Optional<PatientDB> c = repo.findById(77777L);
		assertFalse(c.isPresent());
	}

	@Test
	public void checkA_findAll() throws Exception {
		List<PatientDB> c = (List<PatientDB>) repo.findAll();
		assertTrue(c.size() > 0);
	}

	@Test
	public void checkA_pagination() throws Exception {
		PageRequest pageRequest = PageRequest.of(0, 3);
		Page<PatientDB> firstPage = repo.findAll(pageRequest);
		log.debug("First Page -> {}", firstPage.getContent());
		assertEquals(firstPage.getNumberOfElements(), 3);

		Pageable secondPageable = firstPage.nextPageable();
		Page<PatientDB> secondPage = repo.findAll(secondPageable);
		assertEquals(secondPage.getNumberOfElements(), 3);
		log.debug("Second Page -> {}", secondPage.getContent());

		Pageable thirdPageable = secondPage.nextPageable();
		Page<PatientDB> thirdPage = repo.findAll(thirdPageable);
		assertEquals(thirdPage.getNumberOfElements(), 2);
		log.debug("Third Page -> {}", thirdPage.getContent());

	}

	@Test
	public void checkA_findByName() throws Exception {
		getTestPatients();
		Optional<PatientDB> c = repo.findByName(testPatient.getName());
		assertTrue(c.isPresent());
		assertEquals(c.get(), testPatient);
	}

	@Test
	public void checkA_findPatientsOnAvenues() {
		List<PatientDB> c = repo.patientsOnAvenues();
		assertTrue(c.size() == 2);
	}

	/// Test below this line dirty the context

	@Test
	@DirtiesContext
	public void checkB_sortedPatients() throws Exception {
		Sort sort = Sort.by(Sort.Direction.DESC, "name.lastName");
		List<PatientDB> customers = repo.findAll(sort);
		PatientDB lastInAlphabet = customers.get(0);
		assertEquals(lastInAlphabet.getName().getLastName(), "Zulu");
		log.debug("Sorted Patients -> {}", customers);
	}

	@Test
	@DirtiesContext
	public void checkB_save() throws Exception {
		getTestPatients();
		long initialCount = repo.count();
		log.debug("Number of customers before save: {}", initialCount);

		// Ensure the Database creates a new entity

		AddressDB addr = testPatient.getAddresses().get(0);
		PatientDB newPatient = new PatientDB(testPatient.getName(), addr, testPatient.getGender(), testPatient.getDob());

		PatientDB c = repo.save(newPatient);
		log.debug("Saved Patient: {}", c);

		long newCount = repo.count();
		log.debug("Number of Patients after save: {}", newCount);
		assertTrue(newCount == initialCount + 1);
	}

	@Test
	@DirtiesContext
	public void checkC_update() throws Exception {
		getTestPatients();
		long initialCount = repo.count();
		log.debug("Number of customers before update: {}", initialCount);

		// Retrieve a Patient, change Address, update DB
		Optional<PatientDB> oc = repo.findById(testPatient.getId());
		assertTrue(oc.isPresent());
		PatientDB c = oc.get();
		AddressDB addr = new AddressDB(AddressType.BUSINESS, "100 I have been changed", "somewhere", State.CALIFORNIA, "00000");
		c.addAddress(addr);
		repo.save(c);

		long newCount = repo.count();
		log.debug("Number of Patients after update: {}", newCount);
		assertEquals(newCount, initialCount);

		// Now just update the Name
		NameDB name = new NameDB("New", "To", "Us", "Newbie");
		repo.updateName(c.getId(), name);

		newCount = repo.count();
		log.debug("Number of Patients after Second update: {}", newCount);
		assertEquals(newCount, initialCount);

	}

}
