package devopsTools.application.domain.db;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import devopsTools.application.domain.DomainBaseDTO;
import devopsTools.application.domain.NameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/*
 * Name Class.
 * Stores all the necessary name information for a person in the application
 * It is designed to be embedded into another person like object, i.e. it is not itself
 * a database table
 */
@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
//<data>
@Embeddable
//</data>
public class NameDB extends DomainBaseDTO {

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Size(min = 3, message = "Name must be at least 3 characters long")
	// <data>
	@Column(nullable = false)
	// </data>
	private String firstName;

	private String middleName;

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Size(min = 5, message = "Name must be at least 5 characters long")
	// <data>
	@Column(nullable = false)
	// </data>
	private String lastName;

	// Optional
	private String preferredName;

	public NameDB(@NonNull String firstName, String middleName, @NonNull String lastName) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NameDB other = (NameDB) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;

		/*
		 * At this point first and last names match if there is a middle or preferred
		 * name they must match if middle or preferred names are empty in either name
		 * they are ignored
		 * 
		 */
		if ((middleName != null && !middleName.isEmpty())
				&& (other.middleName != null && !other.middleName.isEmpty())) {
			if (!middleName.equals(other.middleName))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result + ((preferredName == null) ? 0 : preferredName.hashCode());
		return result;
	}

	// DB and DTO conversion functions
	
	static public NameDB DTOtoDB(NameDTO aName) {
		NameDB name = new NameDB(aName.getFirstName(), aName.getLastName(), aName.getMiddleName(),
				aName.getPreferredName());
		return name;

	}

	public NameDTO DBtoDTO() {
		NameDTO name = new NameDTO(this.getFirstName(), this.getLastName(), this.getMiddleName(),
				this.getPreferredName());
		return name;
	}

}
