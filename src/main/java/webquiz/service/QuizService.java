package webquiz.service;

import webquiz.domain.Quiz;
import webquiz.domain.QuizCompletion;
import webquiz.domain.User;
import webquiz.dto.AnswerDto;
import webquiz.dto.ResultDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizService {
    Quiz addQuiz(Quiz quiz);
    Quiz getQuiz(Long id);
    Page<Quiz> findAll(Pageable pageable);
    ResultDto solveQuiz(Long id, AnswerDto answerDto, User user);
    void deleteQuiz(Long id, User requester);
    Page<QuizCompletion> findAllCompleted(Pageable pageable, User user);
}
