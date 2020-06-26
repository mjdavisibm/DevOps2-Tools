package devopsTools.application.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.DoctorDTO;
import devopsTools.application.domain.medical.db.DoctorDB;

/* 
 * See Chapter 13 of SpringData in 28 Minutes
 * 
 */
@Repository
@Transactional
public interface DoctorRepository extends JpaRepository<DoctorDB, Long> {

	
	public Optional<DoctorDB> findByName(NameDB name);

	@Query("Select c From Doctor c where street like '%Avenue'")
	List<DoctorDB> doctorsOnAvenues();

}
