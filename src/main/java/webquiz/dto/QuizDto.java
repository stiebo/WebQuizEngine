package webquiz.dto;

public record QuizDto(
        Long id,
        String title,
        String text,
        String[] options) { }
