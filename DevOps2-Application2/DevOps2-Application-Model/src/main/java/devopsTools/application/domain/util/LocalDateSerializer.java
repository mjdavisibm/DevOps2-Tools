package devopsTools.application.domain.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateSerializer extends StdSerializer<LocalDate> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3817465480505597714L;

	public LocalDateSerializer() {
		super(LocalDate.class);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_DATE));
	}
}
