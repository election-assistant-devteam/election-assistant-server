package com.runningmate.server.domain.test;

import com.runningmate.server.global.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("test")
    public BaseResponse<Object> test(){
        return new BaseResponse<>("hello");
    }
}
