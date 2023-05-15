package by.academy.pharmacy2.service.util;

import by.academy.pharmacy2.entity.Country;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public final class CountryConverter implements AttributeConverter<Country, String> {

    @Override
    public String convertToDatabaseColumn(final Country attribute) {
        return attribute.getCode();
    }

    @Override
    public Country convertToEntityAttribute(final String dbData) {
        return Country.valueOfCode(dbData);
    }
}
