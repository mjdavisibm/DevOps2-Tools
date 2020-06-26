package devopsTools.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import devopsTools.application.domain.NameDTO;
import devopsTools.application.domain.medical.PatientDTO;

/*
 * Matt Note: Added a service Spring Service component to try it out. 
 * This might not be the right place to finally put this service
 * This is the way to access customers, irrelevant of which persistence 
 * layer you want to use
 * 
 */
@Service
public interface PatientService {
	public abstract boolean createPatient(PatientDTO patient);

	public abstract void updatePatient(PatientDTO patient);

	public abstract void deletePatient(PatientDTO patient);

	public abstract void deleteAllPatients();

	public abstract List<PatientDTO> getPatients();

	public abstract void createPatients(List<PatientDTO> patients);

	// Search services
	public abstract int getNumberOfPatients();

	public abstract PatientDTO findByName(NameDTO name);
}
