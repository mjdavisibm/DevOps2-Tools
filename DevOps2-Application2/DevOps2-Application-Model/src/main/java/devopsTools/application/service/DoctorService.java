package devopsTools.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import devopsTools.application.domain.NameDTO;
import devopsTools.application.domain.medical.DoctorDTO;
import devopsTools.application.domain.medical.PatientDTO;

/*
 * Matt Note: Added a service Spring Service component to try it out. 
 * This might not be the right place to finally put this service
 * This is the way to access doctors, irrelevant of which persistence 
 * layer you want to use
 * 
 */
@Service
public interface DoctorService {
	public abstract boolean createDoctor(DoctorDTO doctor);

	public abstract void updateDoctor(DoctorDTO doctor);

	public abstract void deleteDoctor(DoctorDTO doctor);

	public abstract void deleteAllDoctors();

	public abstract List<DoctorDTO> getDoctors();

	public abstract void createDoctors(List<DoctorDTO> doctors);
	
	// Search services
	public abstract int getNumberOfDoctors();

	public abstract DoctorDTO findByName(NameDTO name);
	
	public abstract void addPatients(DoctorDTO doctor, List<PatientDTO> patients);
	
	public abstract void addPatient(DoctorDTO doctor, PatientDTO patient);
	
	public abstract void removePatient(DoctorDTO doctor, PatientDTO patient);
}
