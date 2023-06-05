package org.example.controller;

import com.mfa.framework.base.model.Response;
import com.mfa.framework.base.translate.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.example.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HealthController {

    private final HealthService healthService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response checkHealth(@Param("action") Long action) {
        healthService.checkHealth(action);
        return ResponseBuilder.success();
    }
}
