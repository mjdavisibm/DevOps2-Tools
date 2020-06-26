package devopsTools.application.domain.medical.db;

import java.util.List;

import devopsTools.application.domain.db.AddressDB;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.db.PersonDB;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper=true)
public abstract class MedicalPractitionerDB extends PersonDB {

	public MedicalPractitionerDB(@NonNull String ssn,@NonNull NameDB name,@NonNull List<AddressDB> addresses) {
		super(ssn, name, addresses);
		// TODO Auto-generated constructor stub
	}

	public MedicalPractitionerDB() {
		// TODO Auto-generated constructor stub
	}

}
