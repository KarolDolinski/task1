package com.kd.assignment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class Pair {
    @NotEmpty
    @Pattern(regexp = "[0-9A-Za-z]{1,10}")
    private String key;


    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]{1,10}")
    private String value;

}
