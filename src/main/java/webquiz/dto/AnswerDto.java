package webquiz.dto;


import jakarta.validation.constraints.NotNull;

public record AnswerDto(
        @NotNull
        Integer[] answer) {
}
