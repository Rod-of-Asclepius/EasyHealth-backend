package com.roa.easyhealth.security;

import com.roa.easyhealth.util.RedisUtil;
import generator.entity.Permission;
import generator.entity.Role;
import generator.entity.User;
import generator.service.PermissionService;
import generator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Resource
    private PermissionService permissionService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        ArrayList<String> userPermission = new ArrayList<>();
        Set<String> userPermissionSet = new HashSet<>();
        final List<Role> userRoles = permissionService.getUserRoles(user.getId());
        for (Role role:userRoles){
            userPermissionSet.add("ROLE_"+role.getName());
            final List<Permission> permissionsFromRole = permissionService.getPermissionsFromRole(role.getId());
            for (Permission permission:permissionsFromRole){
                userPermissionSet.add(permission.getName());
            }
        }
        final List<Permission> permissionsFromUser = permissionService.getPermissionsFromUser(user.getId());
        for (Permission permission:permissionsFromUser){
            userPermissionSet.add(permission.getName());
        }
        userPermission.addAll(userPermissionSet);
        user.setPermissions(userPermission);

        SecurityUser securityUser = new SecurityUser(user);
        securityUser.setPermissionValueList(userPermission);
        return securityUser;
    }
}
