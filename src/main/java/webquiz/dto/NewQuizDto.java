package webquiz.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewQuizDto(
        @NotBlank
        String title,
        @NotBlank
        String text,
        @NotNull
        @Size(min=2)
        String[] options,
        Integer[] answer) {
}
