package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size){
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if (totalCount == 0){
            totalPage = 1;
        }else {
            if (totalCount % size == 0){
                totalPage = totalCount / size;
            }else {
                totalPage = totalCount / size + 1;
            }
        }
        if (page < 1)
            page = 1;
        if (page > totalPage)
            page = totalPage;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user = userMapper.findByAccountId(question.getCreator());
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOS);

        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }

    public PaginationDTO list(Long account_id, Integer page, Integer size) {
        Integer totalPage;
        Integer totalCount = questionMapper.countByCreator(account_id);
        if (totalCount == 0){
            totalPage = 1;
        }else{
            if (totalCount % size == 0){
                totalPage = totalCount / size;
            }else {
                totalPage = totalCount / size + 1;
            }
        }
        if (page < 1)
            page = 1;
        if (page > totalPage)
            page = totalPage;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByCreator(offset,size,account_id);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user = userMapper.findByAccountId(question.getCreator());
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOS);

        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }

    public QuestionDTO getQuestionDTOByQuestionId(Integer questionId) {
        Question question = questionMapper.getQuestionByQuestionId(questionId);
        if (question == null){
            return null;
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findByAccountId(question.getCreator());
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            questionMapper.create(question);
        }else {
            questionMapper.update(question);
        }
    }
}
