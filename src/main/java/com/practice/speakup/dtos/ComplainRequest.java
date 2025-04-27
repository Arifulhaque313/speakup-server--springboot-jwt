package com.practice.speakup.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplainRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
}
