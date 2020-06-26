package devopsTools.application.domain;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public abstract class DomainBaseDTO {

	/*
	 * Uses a pretty printer mechanism to send back JSON for this object
	 */
	public String toPrettyPrintJson() {
		String prettyJson = this.toString();
		try {
			ObjectMapper mapper = new ObjectMapper();
			prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prettyJson;
	}

}
