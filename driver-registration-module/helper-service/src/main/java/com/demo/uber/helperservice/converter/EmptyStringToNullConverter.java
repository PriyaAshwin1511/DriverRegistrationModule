package com.demo.uber.helperservice.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EmptyStringToNullConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String s) {
        return "".equals(s) ? null : s;
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return "".equals(s) ? null : s;
    }
}
