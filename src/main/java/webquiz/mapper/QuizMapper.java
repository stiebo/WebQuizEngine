package webquiz.mapper;

import webquiz.domain.Quiz;
import webquiz.domain.QuizCompletion;
import webquiz.domain.User;
import webquiz.dto.NewQuizDto;
import webquiz.dto.QuizCompletionDto;
import webquiz.dto.QuizDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QuizMapper {
    public Quiz toQuiz (NewQuizDto newQuizDto, User requester) {
        String[] options = Arrays.copyOf(newQuizDto.options(), newQuizDto.options().length);
        Integer[] answer = newQuizDto.answer() != null ?
                Arrays.copyOf(newQuizDto.answer(), newQuizDto.answer().length) : new Integer[0];
        return new Quiz()
                .setTitle(newQuizDto.title())
                .setText(newQuizDto.text())
                .setOptions(options)
                .setAnswer(answer)
                .setCreator(requester);
    }

    public QuizDto toDto (Quiz quiz) {
        String[] options = quiz.getOptions() != null ?
                Arrays.copyOf(quiz.getOptions(), quiz.getOptions().length) : null;
        return new QuizDto(
                quiz.getId(),
                quiz.getTitle(),
                quiz.getText(),
                options);
    }

    public QuizCompletionDto toCompletionDto (QuizCompletion quizCompletion) {
        return new QuizCompletionDto(quizCompletion.getQuiz().getId(), quizCompletion.getCompletionTime());
    }
}
