package devopsTools.application.serviceJSONImpl;

/*
 * An implementation of a doctorService.
 * This uses an in-memory list to supply the functions
 * automatically loads doctors from the JSON File
 */

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import devopsTools.application.domain.NameDTO;
import devopsTools.application.domain.medical.DoctorDTO;
import devopsTools.application.domain.medical.JsonTestTools;
import devopsTools.application.domain.medical.PatientDTO;
import devopsTools.application.service.DoctorService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DoctorServiceJsonImpl implements DoctorService {

	private List<DoctorDTO> allObjs;

	protected List<DoctorDTO> getAll() {
		if (allObjs == null) {
			TypeReference<List<DoctorDTO>> typeReference = new TypeReference<List<DoctorDTO>>() {
			};
			@SuppressWarnings("unchecked")
			List<DoctorDTO> all = (List<DoctorDTO>) (Object) JsonTestTools.getObjectsFromJsonFile(typeReference);
			allObjs = all;
		}
		return allObjs;
	}

	public boolean createDoctor(DoctorDTO doctor) {
		getAll();
		return allObjs.add(doctor);
	}

	public void updateDoctor(DoctorDTO doctor) {
		getAll();
		allObjs.remove(doctor);
		allObjs.add(doctor);
	}

	public void deleteDoctor(DoctorDTO doctor) {
		getAll();
		allObjs.remove(doctor);
	}

	public List<DoctorDTO> getDoctors() {
		getAll();
		return allObjs;
	}

	public void createDoctors(List<DoctorDTO> doctors) {
		getAll();
		for (DoctorDTO c : doctors)
			createDoctor(c);

	}

	public DoctorDTO findByName(NameDTO name) {
		getAll();
		for (DoctorDTO c : allObjs) {
			if (c.getName().equals(name))
				return c;
		}
		return null;
	}

	public int getNumberOfDoctors() {
		getAll();
		return allObjs.size();
	}

	public void deleteAllDoctors() {
		allObjs = new ArrayList<DoctorDTO>();
	}

	@Override
	public void addPatients(DoctorDTO doctor, List<PatientDTO> patients) {
		for(PatientDTO patient: patients)
			doctor.addPatient(patient);
	}

	@Override
	public void addPatient(DoctorDTO doctor, PatientDTO patient) {
		doctor.addPatient(patient);
	}

	@Override
	public void removePatient(DoctorDTO doctor, PatientDTO patient) {
		doctor.removePatient(patient);
	}

}
