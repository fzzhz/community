package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    @Insert("insert into question " +
            "(title,description,gmt_create,gmt_modified,creator,tag) " +
            "values " +
            "(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{creator}")
    Integer countByCreator(@Param("creator") Long creator);

    @Select("select * from question where creator=#{creator} limit #{offset},#{size}")
    List<Question> listByCreator(@Param("offset") Integer offset,@Param("size") Integer size,@Param("creator") Long creator);

    @Select("select * from question where id = #{questionId}")
    Question getQuestionByQuestionId(@Param("questionId") Integer questionId);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmt_modified},tag=#{tag} where id=#{id}")
    void update(Question question);
}
