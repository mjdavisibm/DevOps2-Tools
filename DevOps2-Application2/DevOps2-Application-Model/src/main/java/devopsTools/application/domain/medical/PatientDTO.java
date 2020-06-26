package devopsTools.application.domain.medical;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.NameDTO;
import devopsTools.application.domain.PersonDTO;
import devopsTools.application.domain.util.LocalDateDeserializer;
import devopsTools.application.domain.util.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true, includeFieldNames = true)
public class PatientDTO extends PersonDTO {

	// Ensure a constructor includes this value (Lombok)
	@NonNull
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
	
	@ToString.Exclude
	private DoctorDTO doctor;
	
	public PatientDTO(@NonNull String ssn, @NonNull NameDTO name, @NonNull AddressDTO address, @NonNull Gender gender, @NonNull LocalDate dob) {
		super(ssn,name,address);
		this.gender = gender;
		this.dob = dob;
	}
	
	protected PatientDTO() {
		super();
	}

	@AllArgsConstructor
	public enum Gender {
		MALE("Male"), FEMALE("Female");

		@JsonValue
		private String name;

		@Override
		public String toString() {
			return name;
		}
	}
	
	@JsonIgnore
	public int getAge() {
		LocalDate today = LocalDate.now();
		return Period.between(this.dob, today).getYears();
	}
}
