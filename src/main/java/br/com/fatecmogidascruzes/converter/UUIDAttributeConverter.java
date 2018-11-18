package br.com.fatecmogidascruzes.converter;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UUIDAttributeConverter implements AttributeConverter<UUID, String> {

	@Override
	public String convertToDatabaseColumn(UUID uuid) {
		return (uuid == null ? null : uuid.toString());
	}

	@Override
	public UUID convertToEntityAttribute(String uuidString) {
		return (uuidString == null ? null : UUID.fromString(uuidString));
	}
}