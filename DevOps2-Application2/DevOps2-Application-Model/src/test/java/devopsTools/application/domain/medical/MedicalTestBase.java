package devopsTools.application.domain.medical;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public abstract class MedicalTestBase {
	/*
	 * Used to hold a test patient that can be used in tests
	 */
	protected PatientDTO testPatient;
	protected int testCount;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	
	/*
	 * Used to get from a json file some test patients
	 */
	protected List<PatientDTO> getTestPatients() {
		log.debug("Getting test patients from JSON FIle");
		TypeReference<List<PatientDTO>> typeRef = new TypeReference<List<PatientDTO>>() {
		};
		@SuppressWarnings("unchecked")
		List<PatientDTO> patients = (List<PatientDTO>) (Object) JsonTestTools.getObjectsFromJsonFile(typeRef);

		testPatient = patients.get(0);
		testCount = patients.size();

		return patients;
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
