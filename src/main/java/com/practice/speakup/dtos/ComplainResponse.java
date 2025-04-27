package com.practice.speakup.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplainResponse {
    private Long id;
    private String title;
    private String description;
    private String name;
    private String username;
}
