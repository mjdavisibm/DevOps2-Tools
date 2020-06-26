package devopsTools.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import devopsTools.application.data.DoctorRepository;
import devopsTools.application.data.PatientRepository;
import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import devopsTools.application.domain.db.AddressDB;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.PatientDTO.Gender;
import devopsTools.application.domain.medical.db.DoctorDB;
import devopsTools.application.domain.medical.db.PatientDB;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DevOpsApplicationDataApplication implements CommandLineRunner {
/*
	@Autowired
	private PatientRepository patientRepository;
*/
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private EntityManager em;

	public static void main(String... args) {
		SpringApplication.run(DevOpsApplicationDataApplication.class, args);
	}

	public void createPerson(String... args) throws Exception {
		// Create Name and Address Objects
		NameDB name = new NameDB("Alfred", "B", "Johnson", "Alf");
		AddressDB addr = new AddressDB(AddressType.BUSINESS, "72 McCloud Street", "Denver", State.COLORADO, "80237");

		List<AddressDB> addresses = new ArrayList<AddressDB>();
		addresses.add(addr);
		// Create a Doctor Object with name and address
		DoctorDB p = new DoctorDB("000-11-2222",name, addresses);
		//addr.setPerson(p);

		em.persist(addr);
		//em.persist(p);

	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// Create Name and Address Objects
		NameDB name = new NameDB("Alfred", "B", "Johnson", "Alf");
		AddressDB addr = new AddressDB(AddressType.BUSINESS, "72 McCloud Street", "Denver", State.COLORADO, "80237");
		em.persist(addr);
		List<AddressDB> addresses = new ArrayList<AddressDB>();
		addresses.add(addr);
		DoctorDB doc = new DoctorDB("000-11-2222",name, addresses);
		em.persist(doc);
		
		doctorRepository.saveAndFlush(doc);
	}

	public void hold(String... args) throws Exception {

		NameDB name = new NameDB("Alfred", "B", "Johnson", "Alf");
		AddressDB addr = new AddressDB(AddressType.BUSINESS, "72 McCloud Street", "Denver", State.COLORADO, "80237");
		List<AddressDB> addresses = new ArrayList<AddressDB>();
		addresses.add(addr);
		DoctorDB doc = new DoctorDB("000-11-2222",name, addresses);

		/*
		 * Note: Still need to understand how entity manager, persistence and saving
		 * relationships should work.
		 * 
		 */

		name = new NameDB("Test", "A", "Patient", "T1");
		addr = new AddressDB(AddressType.HOME, "10B S 7th Street", "Denver", State.COLORADO, "80258");
		em.persist(addr);
		LocalDate dob = LocalDate.parse("2001-01-25");
		addresses = new ArrayList<AddressDB>();
		addresses.add(addr);
		PatientDB patient = new PatientDB("000-11-2222",name, addresses, Gender.MALE, dob);
		em.persist(patient);
		doc.addPatient(patient);

		doctorRepository.saveAndFlush(doc);
	}
}
