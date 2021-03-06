package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{questionId}")
    public String question(@PathVariable("questionId") Integer questionId,
                           Model model){
        QuestionDTO questionDTO = questionService.getQuestionDTOByQuestionId(questionId);
        if (questionDTO == null){
            return "redirect:/";
        }
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
