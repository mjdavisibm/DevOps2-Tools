package devopsTools.application.domain.medical.db;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.db.AddressDB;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.medical.DoctorDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
//<data>
@Entity
//</data>
@ToString(includeFieldNames = true)
public class DoctorDB extends MedicalPractitionerDB {

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Setter(AccessLevel.NONE)
	// <data>
	@OneToMany(mappedBy = "doctor")
	// </data>
	@JsonIgnore
	private List<PatientDB> patients = new ArrayList<PatientDB>();

	
	public DoctorDB(@NonNull String ssn, @NonNull NameDB name, @NonNull List<AddressDB> addresses) {
		super(ssn,name,addresses);
	}
	
	public void addPatient(PatientDB patient) {
		this.patients.add(patient);
	}

	public void removePatient(PatientDB patient) {
		this.patients.remove(patient);
	}

	public boolean hasPatient(PatientDB patient) {
		return this.patients.contains(patient);
	}
	
	// DB and DTO conversion functions
	
	static public DoctorDB DTOtoDB(DoctorDTO aDoctor) {
		List<AddressDB> addresses = aDoctor.getAddresses().stream()
				.map(addr -> AddressDB.DTOtoDB(addr)).collect(Collectors.toList());
		DoctorDB doctorDB = new DoctorDB(aDoctor.getSsn(),NameDB.DTOtoDB(aDoctor.getName()), addresses);
		aDoctor.getPatients().forEach(p -> doctorDB.addPatient(PatientDB.DTOtoDB(p)));
		return doctorDB;
	}

	public DoctorDTO DBtoDTO() {
		List<AddressDTO> addresses = getAddresses().stream()
				.map(addr -> addr.DBtoDTO()).collect(Collectors.toList());
	
		DoctorDTO doctorDTO = new DoctorDTO(getSsn(),getName().DBtoDTO(),addresses.get(0));
		addresses.subList(1,addresses.size()).forEach(addr -> doctorDTO.addAddress(addr));
		getPatients().forEach(patient -> doctorDTO.addPatient(patient.DBtoDTO()));
		return doctorDTO;
	}

}
