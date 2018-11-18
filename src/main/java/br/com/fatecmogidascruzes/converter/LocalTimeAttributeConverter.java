package br.com.fatecmogidascruzes.converter;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time> {

	@Override
	public Time convertToDatabaseColumn(LocalTime locDateTime) {
		return (locDateTime == null ? null : Time.valueOf(locDateTime));
	}

	@Override
	public LocalTime convertToEntityAttribute(Time sqlTimestamp) {
		return (sqlTimestamp == null ? null : sqlTimestamp.toLocalTime());
	}
}