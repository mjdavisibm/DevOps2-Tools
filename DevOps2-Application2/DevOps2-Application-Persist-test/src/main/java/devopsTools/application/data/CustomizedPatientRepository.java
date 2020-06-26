package devopsTools.application.data;

import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.db.PatientDB;

public interface CustomizedPatientRepository {

	/*
	 * This allows updating just the name field of a customer
	 * It is used by the Rest PATCH service specifically
	 */
	public PatientDB updateName(long patientId, NameDB name);
	
}
