package devopsTools.application.domain.medical;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.NameDTO;
import devopsTools.application.domain.PersonDTO;
import lombok.NonNull;

public abstract class MedicalPractitionerDTO extends PersonDTO {

	public MedicalPractitionerDTO(@NonNull String ssn,@NonNull NameDTO name,@NonNull AddressDTO address) {
		super(ssn,name, address);
		// TODO Auto-generated constructor stub
	}

	public MedicalPractitionerDTO() {
		// TODO Auto-generated constructor stub
	}

}
