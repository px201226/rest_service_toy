package com.example.restapi.snippets;

import com.example.restapi.domain.response.ResponseStatus;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class CustomSnippetsUtill {

    public FieldDescriptor[] enumConvertFieldDescriptor(ResponseStatus[] enumTypes) {

        return Arrays.stream(enumTypes)
                .map(enumType -> fieldWithPath(Integer.toString(enumType.getResultCode())).description(enumType))
                .toArray(FieldDescriptor[]::new);
    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
                                                                   PayloadSubsectionExtractor<?> subsectionExtractor,
                                                                   Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }
}
