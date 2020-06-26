package devopsTools.application.data.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import devopsTools.application.data.CustomizedPatientRepository;
import devopsTools.application.data.PatientRepository;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.PatientDTO;
import devopsTools.application.domain.medical.db.PatientDB;

public class PatientRepositoryImpl implements CustomizedPatientRepository{

	@Autowired
	private PatientRepository patientRepository;
	
	public PatientRepositoryImpl() {
		// TODO Auto-generated constructor stub
	}

	public PatientDB updateName(long patientId, NameDB name) {
		Optional<PatientDB> oPatient = patientRepository.findById(patientId);
		if (!oPatient.isPresent())
			return null;
		else {
			PatientDB patient = oPatient.get();
			patient.setName(name);
			return patientRepository.save(patient);
		}
	}
}
