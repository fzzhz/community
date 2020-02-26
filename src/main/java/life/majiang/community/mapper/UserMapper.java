package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modefied,avatar_url) " +
            "values (#{account_id},#{name},#{token},#{gmt_create},#{gmt_modefied},#{avatar_url})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{account_id}")
    User findByAccountId(@Param("account_id") Long account_id);

    @Update("update user set token = #{token}, name = #{name}, avatar_url = #{avatar_url}, gmt_modefied = #{gmt_modefied} where account_id = #{account_id}")
    void updateUser(User user);
}
