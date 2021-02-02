package com.example.restapi.snippets;


import com.example.restapi.exception.response.ResponseStatus;
import com.example.restapi.web.common.RestDocsConfiguration;
import com.example.restapi.web.common.TestDescription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Map;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class CommonSnippetsTest {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    @TestDescription("Error Response snippet 생성 테스트")
    public void error_response() throws Exception {

        this.mockMvc.perform(
                get("/403error")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is4xxClientError())
                .andDo(document("error-response",
                        responseFields(
                                fieldWithPath("resultCode").type(JsonFieldType.NUMBER).description("결과코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지"),
                                fieldWithPath("details").type(JsonFieldType.STRING).description("에러상세설명")
                        ))
                );
    }

    @Test
    @TestDescription("resultCode snippets 생성 테스트")
    public void commons() throws Exception {

        this.mockMvc.perform(
                get("/resultCode")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(document("common",
                        customResponseFields("custom-response", null,
                                Attributes.attributes(Attributes.key("title").value("ResponseState Code")),
                                enumConvertFieldDescriptor(ResponseStatus.values())
                        )
                ));
    }

    private FieldDescriptor[] enumConvertFieldDescriptor(ResponseStatus[] enumTypes) {
        return Arrays.stream(enumTypes)
                .map(enumType -> fieldWithPath(String.valueOf(enumType.getResultCode())).description(enumType.getResultMsg()))
                .toArray(FieldDescriptor[]::new);
    }

    public static CustomResponseFieldsSnippet customResponseFields(String type,
                                                                   PayloadSubsectionExtractor<?> subsectionExtractor,
                                                                   Map<String, Object> attributes, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), attributes
                , true);
    }
}
