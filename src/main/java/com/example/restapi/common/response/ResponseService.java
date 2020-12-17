package com.example.restapi.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 해당 Class가 Service임을 명시합니다.
public class ResponseService {

    @AllArgsConstructor
    @Getter
    public enum CommonResponse {
        SUCCESS(0, "성공하였습니디."),
        FAIL(-1, "실패하였습니다.");
        int resultCode;
        String resultMsg;
    }

    public <T> ResponseData<T> createSuccessResponse(T data){
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setResultCode(0);
        responseData.setResultMsg("success");
        responseData.setData(data);
        return responseData;
    }
}
