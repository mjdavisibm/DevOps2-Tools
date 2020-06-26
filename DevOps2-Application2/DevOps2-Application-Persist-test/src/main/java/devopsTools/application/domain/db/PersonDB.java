package devopsTools.application.domain.db;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.PersonDTO;
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
// <data>
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// </data>
public class PersonDB extends DomainBaseDB {

	// <data>
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// </data>
	// Do not allow ID to be set manually leave it to the persistence layer
	@Setter(AccessLevel.NONE)
	private long id;

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$", message = "Please use a valid US SSN xxx-xx-xxx")
	private String ssn;

	// <data>
	@Embedded
	// </data>
	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	private NameDB name;

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Setter(AccessLevel.NONE)
	// <data>
	@OneToMany(mappedBy = "person")
	// </data>
	@JsonIgnore
	private List<AddressDB> addresses = new ArrayList<AddressDB>();

	public PersonDB(@NonNull String ssn, @NonNull NameDB name, @NonNull List<AddressDB> addresses) {
		this.name = name;
		this.addresses.addAll(addresses);
	}
	
	public void addAddress(AddressDB address) {
		this.addresses.add(address);
	}

	public void removeAddress(AddressDB address) {
		this.addresses.remove(address);
	}

	public boolean hasAddress(AddressDB address) {
		return this.addresses.contains(address);
	}

	// DB and DTO conversion functions

	static public PersonDB DTOtoDB(PersonDTO aPerson) {
		List<AddressDB> addresses = aPerson.getAddresses().stream().map(addr -> AddressDB.DTOtoDB(addr))
				.collect(Collectors.toList());
		PersonDB personDB = new PersonDB(aPerson.getSsn(), NameDB.DTOtoDB(aPerson.getName()), addresses);
		return personDB;
	}

	public PersonDTO DBtoDTO() {
		List<AddressDTO> addresses = getAddresses().stream().map(addr -> addr.DBtoDTO()).collect(Collectors.toList());

		PersonDTO personDTO = new PersonDTO(getSsn(), getName().DBtoDTO(), addresses.get(0));
		addresses.subList(1, addresses.size()).forEach(addr -> personDTO.addAddress(addr));
		return personDTO;
	}
}
