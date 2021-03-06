package com.lylbp.project.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lylbp.common.utils.TokenUtil;
import com.lylbp.common.constant.ProjectConstant;
import com.lylbp.manager.redis.service.RedisService;
import com.lylbp.manager.security.interfaces.MyUserDetailsService;
import com.lylbp.manager.security.entity.PermissionAuthority;
import com.lylbp.project.entity.Admin;
import com.lylbp.project.entity.SecurityUser;
import com.lylbp.project.enums.TrueOrFalseEnum;
import com.lylbp.project.service.AdminService;
import com.lylbp.project.service.AuthService;
import com.lylbp.project.service.PermissionService;
import com.lylbp.project.service.RoleService;
import com.lylbp.project.vo.PermissionVO;
import com.lylbp.project.vo.RoleVO;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * UserDetailsService
 *
 * @author weiwenbin
 * @date 2020/6/30 下午5:25
 */
@Service
public class UserDetailServiceImpl implements MyUserDetailsService {
    @Resource
    private AuthService authService;

    @Resource
    private RedisService redisService;

    @Resource
    private AdminService adminService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取用户信息
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getLoginAccount, username);
        Admin admin = adminService.getBaseMapper().selectOne(wrapper);
        //用户id
        String adminId = admin.getAdminId();

        //当前用户含有的角色对象集合
        List<RoleVO> roleVOList;
        //当前用户含有的权限对象集合
        List<PermissionVO> userHasPermission;
        //是否为超级管理员,若为超级管理员查全部
        HashMap<String, Object> params = new HashMap<>(1);
        Boolean supperAdmin = adminService.isSupperAdmin(adminId);
        if (supperAdmin) {
            roleVOList = roleService.getRoleVOListByParams(null, params);
            userHasPermission = permissionService.getPermissionVOListByParams(null, params);
        } else {
            roleVOList = adminService.getUserAssignRoleList(adminId, TrueOrFalseEnum.TRUE.getCode(), params);
            userHasPermission = adminService.getUserAssignPermissionVO(adminId, TrueOrFalseEnum.TRUE.getCode(), params);
        }

        List<PermissionAuthority> permissionAuthorities = new ArrayList<>();
        userHasPermission.forEach(permissionVO -> permissionAuthorities.add(new PermissionAuthority(permissionVO.getPermissionUrl())));
        return new SecurityUser(supperAdmin, admin, roleVOList, permissionAuthorities);
    }

    @Override
    public SecurityUser token2SecurityUser(HttpServletRequest request) {
        String auth = request.getHeader(ProjectConstant.AUTHENTICATION);
        //验证token
        String token = authService.getRedisToken(auth);
        if (StrUtil.isEmpty(token)) {
            return null;
        }

        if (!TokenUtil.verifyToken(token)) {
            return null;
        }
        SecurityUser securityUser = TokenUtil.getClazzByToken(token, SecurityUser.class);

        //token续命
        long expireTime = TokenUtil.getTokenExp(token).getTime();
        long currentTime = DateUtil.date().getTime();
        if (expireTime - currentTime < ProjectConstant.JWT_EXPIRE_TIME / 2) {
            token = TokenUtil.createToken(securityUser, ProjectConstant.JWT_EXPIRE_TIME);
            redisService.strSet(ProjectConstant.REDIS_USER_TOKEN_PRE + auth, token, ProjectConstant.JWT_EXPIRE_TIME);
        }

        return securityUser;
    }

    @Override
    public Collection<ConfigAttribute> getConfigAttributes(HttpServletRequest request) {
        //当前请求url
        String requestURI = request.getRequestURI();
        //获取所有权限集合
        List<PermissionVO> redisAllPermissionVO = permissionService.getRedisAllPermissionVO();

        //若当前路由存在于所有权限url中返回
        PathMatcher pathMatcher = new AntPathMatcher();
        for (PermissionVO permissionVO : redisAllPermissionVO) {
            String permissionUrl = permissionVO.getPermissionUrl();
            if (pathMatcher.match(permissionUrl, requestURI)) {
                List<ConfigAttribute> configAttributes = new ArrayList<>();
                configAttributes.add(new SecurityConfig(permissionUrl));
                return configAttributes;
            }
        }

        return null;
    }
}
