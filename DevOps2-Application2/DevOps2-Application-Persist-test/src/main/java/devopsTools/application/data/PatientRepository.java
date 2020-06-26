package devopsTools.application.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.db.PatientDB;

/* 
 * See Chapter 13 of SpringData in 28 Minutes
 * 
 */
@Repository
@Transactional
public interface PatientRepository extends JpaRepository<PatientDB, Long> , CustomizedPatientRepository {
	
	public Optional<PatientDB> findBySsn(String ssn);
	
	public Optional<PatientDB> findByName(NameDB name);

	/*
	 * SELECT * FROM person  P
	 * where P.id  in (select person_id from Address where street like '%Avenue')
	 */
	
	//@Query("Select p From Patient p where p.id in (select person_id from Address where street like '%Avenue')")
	
	@Query("Select p From Address a JOIN a.patient p where a.street like '%Avenue'")
	List<PatientDB> patientsOnAvenues();

}
