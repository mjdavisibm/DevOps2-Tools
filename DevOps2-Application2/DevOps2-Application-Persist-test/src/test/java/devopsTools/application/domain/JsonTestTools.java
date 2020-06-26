package devopsTools.application.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class JsonTestTools {

	public JsonTestTools() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * This function will read a any JSON File into a list of objects. The file
	 * structure must match the object
	 * 
	 * Example: To load a list of Patients from Patients.json use
	 * 
	 * TypeReference<List<Patient>> typeReference = new
	 * TypeReference<List<Patient>>() {}; List<Patient> =
	 * getObjectsFromJsonFile(typeReference)
	 */
	static public <T> List<Object> getObjectsFromJsonFile(TypeReference<List<T>> typeReference) {
		List<Object> allObjs = null;

		String className = typeReference.getType().getTypeName();
		String filename = className.substring(className.lastIndexOf('.') + 1, className.length() - 1);
		String jsonFilename = "/json/" + filename + "s.json";
		log.trace("Loading '" + typeReference.getType().getTypeName() + "' from Json file: " + jsonFilename);
		ObjectMapper mapper = new ObjectMapper();
		InputStream inputStream = TypeReference.class.getResourceAsStream(jsonFilename);
		try {
			allObjs = mapper.readValue(inputStream, typeReference);
			if (log.isTraceEnabled()) {
				for (Object obj : allObjs) {
					log.trace("Line Item: " + obj);
				}
			}
		} catch (IOException e) {
			log.warn("Unable to load JSON file: " + jsonFilename + "\n Error Message: " + e.getMessage());
		}
		return allObjs;
	}
}
