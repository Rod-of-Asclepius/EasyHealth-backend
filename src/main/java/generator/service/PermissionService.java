package generator.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.entity.Permission;
import generator.entity.Role;
import generator.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission>{
    public Permission getPermissionsFromId(Integer id){
        return getById(id);
    }
    public List<Permission> getPermissionsFromUser(Integer uid){
        return baseMapper.getPermissionsFromUser(uid);
    }
    public List<Permission> getPermissionsFromRole(Integer rid){
        return baseMapper.getPermissionsFromRole(rid);
    }
    public List<Role> getUserRoles(Integer uid){
        return baseMapper.getUserRoles(uid);
    }
}




