package boosters.fundboost.global.converter;

import boosters.fundboost.global.exception.FileException;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Converter
public class FilesConverter implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new FileException(ErrorStatus.FILE_CONVERT_ERROR);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new FileException(ErrorStatus.FILE_CONVERT_ERROR);
        }
    }
}
