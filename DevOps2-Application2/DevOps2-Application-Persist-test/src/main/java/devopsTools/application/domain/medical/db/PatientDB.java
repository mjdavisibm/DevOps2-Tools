package devopsTools.application.domain.medical.db;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.db.AddressDB;
import devopsTools.application.domain.db.NameDB;
import devopsTools.application.domain.db.PersonDB;
import devopsTools.application.domain.medical.PatientDTO;
import devopsTools.application.domain.medical.PatientDTO.Gender;
import devopsTools.application.domain.util.LocalDateDeserializer;
import devopsTools.application.domain.util.LocalDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
// <data>
@Entity
// </data>
@ToString(callSuper = true, includeFieldNames = true)
public class PatientDB extends PersonDB {

	// Ensure a constructor includes this value (Lombok)
	@NonNull
	// <data>
	@Enumerated(EnumType.STRING)
	// </data>
	private Gender gender;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	// need to fix dates
	@Past(message = "Date of Birth cannot be in the future")
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	private LocalDate dob;

	// <data>
	@ManyToOne
	// </data>
	@ToString.Exclude
	private DoctorDB doctor;

	public PatientDB(@NonNull String ssn, @NonNull NameDB name, @NonNull List<AddressDB> addresses,
			@NonNull Gender gender, @NonNull LocalDate dob) {
		super(ssn, name, addresses);
		this.gender = gender;
		this.dob = dob;
	}

	protected PatientDB() {
		super();
	}

	@JsonIgnore
	public int getAge() {
		LocalDate today = LocalDate.now();
		return Period.between(this.dob, today).getYears();
	}

	// DB and DTO conversion functions

	static public PatientDB DTOtoDB(PatientDTO aPatient) {
		List<AddressDB> addresses = aPatient.getAddresses().stream().map(addr -> AddressDB.DTOtoDB(addr))
				.collect(Collectors.toList());
		PatientDB patientDB = new PatientDB(aPatient.getSsn(), NameDB.DTOtoDB(aPatient.getName()), addresses,
				aPatient.getGender(), aPatient.getDob());
		return patientDB;
	}

	public PatientDTO DBtoDTO() {
		List<AddressDTO> addresses = getAddresses().stream().map(addr -> addr.DBtoDTO()).collect(Collectors.toList());

		PatientDTO patientDTO = new PatientDTO(getSsn(), getName().DBtoDTO(), addresses.get(0), getGender(), getDob());
		addresses.subList(1, addresses.size()).forEach(addr -> patientDTO.addAddress(addr));
		return patientDTO;
	}
}
