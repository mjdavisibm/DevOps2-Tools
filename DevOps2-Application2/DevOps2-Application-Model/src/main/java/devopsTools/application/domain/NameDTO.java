package devopsTools.application.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@EqualsAndHashCode(callSuper=false)
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class NameDTO extends DomainBaseDTO {

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Size(min = 3, message = "Name must be at least 3 characters long")
	private String firstName;

	private String middleName;

	@NotNull
	// Ensure a constructor includes this value (Lombok)
	@NonNull
	@Size(min = 5, message = "Name must be at least 5 characters long")
	private String lastName;

	// Optional
	private String preferredName;

	public NameDTO(@NonNull String firstName, String middleName, @NonNull String lastName) {
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
		NameDTO other = (NameDTO) obj;
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
		/*
		 * if (preferredName == null) { if (other.preferredName != null) return false; }
		 * else if (!preferredName.equals(other.preferredName)) return false;
		 */
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

}
