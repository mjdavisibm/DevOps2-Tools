package devopsTools.application.serviceJSONImpl;

/*
 * An implementation of a patientService.
 * This uses an in-memory list to supply the functions
 * automatically loads patients from the JSON File
 */

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import devopsTools.application.domain.NameDTO;
import devopsTools.application.domain.medical.JsonTestTools;
import devopsTools.application.domain.medical.PatientDTO;
import devopsTools.application.service.PatientService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PatientServiceJsonImpl implements PatientService {

	private List<PatientDTO> allObjs;

	protected List<PatientDTO> getAll() {
		if (allObjs == null) {
			TypeReference<List<PatientDTO>> typeReference = new TypeReference<List<PatientDTO>>() {
			};
			@SuppressWarnings("unchecked")
			List<PatientDTO> all = (List<PatientDTO>) (Object) JsonTestTools.getObjectsFromJsonFile(typeReference);
			allObjs = all;
		}
		return allObjs;
	}

	public boolean createPatient(PatientDTO patient) {
		getAll();
		return allObjs.add(patient);
	}

	public void updatePatient(PatientDTO patient) {
		getAll();
		allObjs.remove(patient);
		allObjs.add(patient);
	}

	public void deletePatient(PatientDTO patient) {
		getAll();
		allObjs.remove(patient);
	}

	public List<PatientDTO> getPatients() {
		getAll();
		return allObjs;
	}

	public void createPatients(List<PatientDTO> patients) {
		getAll();
		for (PatientDTO c : patients)
			createPatient(c);

	}

	public PatientDTO findByName(NameDTO name) {
		getAll();
		for (PatientDTO c : allObjs) {
			if (c.getName().equals(name))
				return c;
		}
		return null;
	}

	public int getNumberOfPatients() {
		getAll();
		return allObjs.size();
	}

	public void deleteAllPatients() {
		allObjs = new ArrayList<PatientDTO>();
	}

}
