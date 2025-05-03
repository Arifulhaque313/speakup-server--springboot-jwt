package com.practice.speakup.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/v3")
public class SwaggerController {
    @GetMapping("/api-docs.yml")
    public ResponseEntity<byte[]> getOpenApiSpec() throws Exception{
        ClassPathResource resource = new ClassPathResource("swagger.yml");
        InputStream inputStream = resource.getInputStream();

        byte[] content = StreamUtils.copyToByteArray(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=swagger.yml")
                .header(HttpHeaders.CONTENT_TYPE, "application/yaml")
                .body(content);
    }
}
