package devopsTools.application.domain.medical;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.NameDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(includeFieldNames = true)
@NoArgsConstructor
public class DoctorDTO extends MedicalPractitionerDTO {

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Setter(AccessLevel.NONE)
	@JsonIgnore
	private List<PatientDTO> patients = new ArrayList<PatientDTO>();

	
	public DoctorDTO(@NonNull String ssn, @NonNull NameDTO name, @NonNull AddressDTO address) {
		super(ssn,name,address);
	}
	
	public void addPatient(PatientDTO patient) {
		this.patients.add(patient);
	}

	public void removePatient(PatientDTO patient) {
		this.patients.remove(patient);
	}

	public boolean hasPatient(PatientDTO patient) {
		return this.patients.contains(patient);
	}

}
