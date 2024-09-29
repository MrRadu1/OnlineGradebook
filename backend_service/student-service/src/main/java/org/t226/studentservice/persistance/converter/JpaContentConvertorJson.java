package org.t226.studentservice.persistance.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.t226.studentservice.exception.AppException;
import org.t226.studentservice.exception.ExceptionType;
import org.t226.studentservice.persistance.model.domain.StudentProperties;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
@RequiredArgsConstructor
public class JpaContentConvertorJson implements AttributeConverter<StudentProperties, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(StudentProperties studentProperties) {
        try {
            return objectMapper.writeValueAsString(studentProperties);
        } catch (JsonProcessingException e) {
            throw new AppException(e.getMessage(), ExceptionType.GENERIC);
        }
    }

    @Override
    public StudentProperties convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, StudentProperties.class);
        } catch (JsonProcessingException e) {
            throw new AppException(e.getMessage(), ExceptionType.GENERIC);
        }
    }
}
