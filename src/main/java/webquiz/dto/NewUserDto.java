package webquiz.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewUserDto(
        @NotBlank
        @Pattern(regexp = "\\w+(\\.\\w+)?@\\w+\\.\\w{2,3}")
        String email,
        @NotBlank
        @Size(min = 5)
        String password) { }
