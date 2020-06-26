package devopsTools.application.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * High level concrete class to hold person information
 */
@Data
@RequiredArgsConstructor
// Access need to be protected or private
// Allows Spring to create an empty Person Object
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(includeFieldNames = true)
@EqualsAndHashCode(callSuper = false)
public class PersonDTO extends DomainBaseDTO {

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$", 
	message = "Please use a valid US SSN xxx-xx-xxx")
	private String ssn;
	
	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	private NameDTO name;

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Setter(AccessLevel.NONE)
	private List<AddressDTO> addresses = new ArrayList<AddressDTO>();

	static final String ILLEGAL_SSN = "Illegal";
	
	public PersonDTO(@NonNull String ssn, @NonNull NameDTO name, @NonNull AddressDTO address) {
		if (!isValidSSN(ssn)) ssn = ILLEGAL_SSN;
		this.ssn = ssn;
		this.name = name;
		this.addAddress(address);
	}

	public boolean isValidSSN(String anSSN) {
		String regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
		return anSSN.matches(regexp);
	}
	
	public void setSsn(String ssn) {
		if (!isValidSSN(ssn)) ssn = ILLEGAL_SSN;
		this.ssn = ssn;
	}
	
	public void addAddress(AddressDTO address) {
		this.addresses.add(address);
	}

	public void removeAddress(AddressDTO address) {
		this.addresses.remove(address);
	}

	public boolean hasAddress(AddressDTO address) {
		return this.addresses.contains(address);
	}
	
	public String getLastFourOfSocial() {
		return ssn.substring(ssn.length() - 4, ssn.length());
	}
	
	
}
