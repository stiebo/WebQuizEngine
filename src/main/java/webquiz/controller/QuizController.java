package webquiz.controller;

import jakarta.validation.Valid;
import webquiz.domain.Quiz;
import webquiz.domain.QuizCompletion;
import webquiz.domain.User;
import webquiz.dto.*;
import webquiz.mapper.QuizMapper;
import webquiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/quizzes")
public class QuizController {
    private final QuizService service;
    private final QuizMapper mapper;

    @Autowired
    public QuizController(QuizService service, QuizMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public QuizDto addQuiz(@Valid @RequestBody NewQuizDto newQuizDto,
                           @AuthenticationPrincipal User requester) {
        return mapper.toDto(service.addQuiz(mapper.toQuiz(newQuizDto, requester)));
    }


    @GetMapping("/{id}")
    public QuizDto getQuiz(@PathVariable Long id) {
        return mapper.toDto(service.getQuiz(id));
    }

    @GetMapping
    public Page<QuizDto> getAllQuizzes(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<Quiz> quizzes = service.findAll(pageable);
        return quizzes.map(mapper::toDto);
    }

    @PostMapping("/{id}/solve")
    public ResultDto solveQuiz(@PathVariable("id") Long id, @RequestBody AnswerDto answerDto,
                               @AuthenticationPrincipal User user) {
        return service.solveQuiz(id, answerDto, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable("id") Long id, @AuthenticationPrincipal User requester) {
        service.deleteQuiz(id, requester);
    }

    @GetMapping("/completed")
    public Page<QuizCompletionDto> getCompletedQuizzes (@PageableDefault(page = 0, size = 10) Pageable pageable,
                                                        @AuthenticationPrincipal User user) {
        Page<QuizCompletion> quizCompletions = service.findAllCompleted(pageable, user);
        return quizCompletions.map(mapper::toCompletionDto);
    }
}
