package generator.mapper;

import generator.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import generator.entity.Role;

import java.util.List;

/**
 * @Entity generator.entity.Permission
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> getPermissionsFromUser(Integer uid);
    List<Permission> getPermissionsFromRole(Integer rid);
    List<Role> getUserRoles(Integer uid);
}




