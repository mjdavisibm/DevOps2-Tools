package devopsTools.application.domain.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import devopsTools.application.domain.AddressDTO;
import devopsTools.application.domain.AddressDTO.AddressType;
import devopsTools.application.domain.AddressDTO.State;
import devopsTools.application.domain.PersonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
//<data>
@Entity
//</data>
public class AddressDB extends DomainBaseDB {

	// <data>
	@Id
	@GeneratedValue
	// </data>
	private long id;

	// <data>
	@ManyToOne
	// </data>
	@ToString.Exclude
	private PersonDB person;

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	// <data>
	@Enumerated(EnumType.STRING)
	@Column(name = "ATYPE", nullable = false)
	// </data>
	private AddressType type;

	@Size(min = 3, message = "Street must be at least 3 characters long")
	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	// <data>
	@Column(nullable = false)
	// </data>
	private String street;

	@Size(min = 3, message = "City must be at least 3 characters long")
	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	// <data>
	@Column(nullable = false)
	// </data>
	private String city;

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	// <data>
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	// </data>
	private State state;

	@Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Must be a valid zip code xxxxx or xxxxx-xxx")
	@NotNull
	private String zip;

	public AddressDB(@NonNull AddressType type, @NonNull String street, @NonNull String city, @NonNull State state,
			@NonNull String zip) {
		this.type = type;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressDB other = (AddressDB) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (state != other.state)
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;

		// At this point the address is true except for Zip codes
		// If either address is missing a zip code then the answer is true

		if (zip == null || other.zip == null)
			return true;
		else if ((zip != null && zip.isEmpty()) || (other.zip != null && other.zip.isEmpty())) {
			return true;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}
	
	// DB and DTO conversion functions
			
	static public AddressDB DTOtoDB(AddressDTO anAddr) {
		AddressDB addrDB = new AddressDB(anAddr.getType(), anAddr.getStreet(), anAddr.getCity(), anAddr.getState(),
				anAddr.getZip());
		
		return addrDB;
	}

	public AddressDTO DBtoDTO() {
		AddressDTO anAddrDTO = new AddressDTO(getType(), getStreet(), getCity(), getState(),getZip());
		return anAddrDTO;
	}
}
