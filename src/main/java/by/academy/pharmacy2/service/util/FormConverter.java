package by.academy.pharmacy2.service.util;

import by.academy.pharmacy2.entity.Form;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public final class FormConverter implements AttributeConverter<Form, String> {

    @Override
    public String convertToDatabaseColumn(final Form attribute) {
        return attribute.toString().toLowerCase();
    }

    @Override
    public Form convertToEntityAttribute(final String dbData) {
        return Form.valueOf(dbData.toUpperCase());
    }
}
