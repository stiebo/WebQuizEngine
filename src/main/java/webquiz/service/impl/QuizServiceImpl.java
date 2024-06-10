package webquiz.service.impl;


import webquiz.domain.Quiz;
import webquiz.domain.QuizCompletion;
import webquiz.domain.User;
import webquiz.dto.AnswerDto;
import webquiz.dto.ResultDto;
import webquiz.exception.QuizNotFoundException;
import webquiz.exception.UserForbiddenException;
import webquiz.repository.QuizCompletionRepository;
import webquiz.repository.QuizRepository;
import webquiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizCompletionRepository quizCompletionRepository;


    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, QuizCompletionRepository quizCompletionRepository) {
        this.quizRepository = quizRepository;
        this.quizCompletionRepository = quizCompletionRepository;
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuiz(Long id) throws QuizNotFoundException {
        return quizRepository.findById(id)
                .orElseThrow(QuizNotFoundException::new);
    }

    @Override
    public Page<Quiz> findAll(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    @Override
    public ResultDto solveQuiz(Long id, AnswerDto answerDto, User user) throws QuizNotFoundException {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(QuizNotFoundException::new);
        if (Arrays.equals(quiz.getAnswer(), answerDto.answer())) {
            QuizCompletion quizCompletion = new QuizCompletion()
                    .setQuiz(quiz)
                    .setUser(user)
                    .setCompletionTime(LocalDateTime.now());
            quizCompletionRepository.save(quizCompletion);
            return new ResultDto(true, "Congratulations, you're right!");
        }
        else {
            return new ResultDto(false, "Wrong answer! Please, try again.");
        }
    }

    @Override
    public void deleteQuiz(Long id, User requester) throws QuizNotFoundException, UserForbiddenException {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(QuizNotFoundException::new);
        if (!quiz.getCreator().getUsername().equals(requester.getUsername())) {
            throw new UserForbiddenException();
        }
        quizRepository.delete(quiz);
    }

    @Override
    public Page<QuizCompletion> findAllCompleted(Pageable pageable, User user) {
        return quizCompletionRepository.findAllByUserOrderByCompletionTimeDesc(pageable, user);
    }
}
