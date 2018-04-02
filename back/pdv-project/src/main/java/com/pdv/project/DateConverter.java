package com.pdv.project;

import java.time.Instant;
import java.util.Date;

import javax.persistence.AttributeConverter;

public class DateConverter implements AttributeConverter<Instant, Date> {

	@Override
	public Date convertToDatabaseColumn(Instant instant) {
		return Date.from(instant);
	}

	@Override
	public Instant convertToEntityAttribute(Date date) {
		return date.toInstant();
	}

}
