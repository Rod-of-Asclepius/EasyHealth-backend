package generator.mapper;

import generator.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

/**
 * @Entity generator.entity.User
 */
public interface UserMapper extends BaseMapper<User> {
    @Insert("Insert into user (name,password) values ('${username}', '${password}')")
    void addUser(String username, String password);
}




