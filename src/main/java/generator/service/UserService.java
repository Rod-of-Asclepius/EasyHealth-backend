package generator.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roa.easyhealth.exception.MyException;
import com.roa.easyhealth.util.AlgoUtil;
import com.roa.easyhealth.util.RedisUtil;
import generator.entity.User;
import generator.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 *
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User>{
    public static final String USER_PREFIX = "User:";

    @Resource
    RedisUtil redisUtil;

    public User getUserById(long userId){
        return getById(userId);
    }
    public User getUserByUserName(String userName){
        User user = new User();
        user.setName(userName);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user).last("LIMIT 1");
        final User one = getOne(queryWrapper);
        return one;
    }
    public void addUser(String userName, String password) throws MyException {
        System.out.println();
        if(getUserByUserName(userName)!=null){
            throw new MyException("用户名已存在！");
        }
        baseMapper.addUser(userName, password);
    }
    public String genToken(User user){
        return user.getName()+":"+AlgoUtil.getSHA256Str(UUID.randomUUID().toString());
    }
    public void setToken(String token, User user){

        redisUtil.set(USER_PREFIX+token,user,2*60*60L);
    }
    public void delToken(String token){
        redisUtil.del(USER_PREFIX+token);
    }
    public User getUserByToken(String token){
        final Object o = redisUtil.get(USER_PREFIX + token);
        if(o==null){
            return null;
        }else{
            return (User) o;
        }
    }
}




