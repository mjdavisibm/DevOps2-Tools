package devopsTools.application.domain.medical;

/*
 * Just test that the load from JSON file is working and
 * uses this to check patients
 */
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Period;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import devopsTools.application.domain.NameDTO;
import devopsTools.application.domain.PersonDTO;
import devopsTools.application.domain.medical.PatientDTO.Gender;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mattdavis
 *
 */
@Slf4j
public class PatientTest extends MedicalTestBase {
	
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

	private PatientDTO generateTestPatient() {
		NameDTO name1 = new NameDTO("James", "M", "Test", "Jim");
		AddressDTO addr1 = new AddressDTO(AddressType.HOME, "123 My Street", "Denver", State.COLORADO, "80201");

		LocalDate dob = LocalDate.parse("1997-05-15");
		PatientDTO patient = new PatientDTO("550-55-8800",name1,addr1,Gender.MALE,dob);
		log.info("Patient --> {}", patient);
		return patient;
	}
	
	@Test
	public void patientAge_test() {
		PatientDTO patient = generateTestPatient();
		int age = LocalDate.now().getYear() - 1997;
		assertTrue(patient.getAge() == age);
	}
	@Test
	public void patient_test() {
		getTestPatients();
		System.out.println(testPatient.toPrettyPrintJson());
		NameDTO name = new NameDTO("Franklin", "Bill", "Tester", "Frank");
		assertTrue(testPatient.getName().equals(name));
		assertTrue(testPatient.getGender() == Gender.MALE);

		AddressDTO addr = new AddressDTO(AddressType.BUSINESS, "1 Test Street", "New York", State.NEW_YORK);
		assertTrue(testPatient.hasAddress(addr));
	}
	
	@Test
	public void patient_dobLocalDate() {
		getTestPatients();

		LocalDate dob = LocalDate.parse("1997-05-15");
		assertTrue(testPatient.getDob().equals(dob));
		int age = Period.between(dob, LocalDate.now()).getYears();
		assertTrue(testPatient.getAge() == age);

	}
	

}
