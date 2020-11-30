package com.lylbp.project.controller.bg;

import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.utils.TokenUtil;
import com.lylbp.core.constant.ProjectConstant;
import com.lylbp.core.entity.ResResult;
import com.lylbp.common.enums.ResResultEnum;
import com.lylbp.project.dto.AdminLoginDTO;
import com.lylbp.manger.security.SecurityProperties;
import com.lylbp.project.entity.SecurityUser;
import com.lylbp.project.service.AuthService;
import com.lylbp.project.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author weiwenbin
 * @date 2020/7/1 下午2:38
 */
@RestController
@RequestMapping("/bg/auth")
@Api(tags = "登录相关")
public class AuthController {
    @Resource
    private AuthService authService;

    @Resource
    SecurityProperties securityProperties;

    @PostMapping("/login")
    @ApiOperation("后台用户登录")
    public ResResult<String> login(@RequestBody @Validated AdminLoginDTO adminLoginDTO) {
        SecurityUser securityUser = authService.login(adminLoginDTO.getLoginName(), adminLoginDTO.getPwd());

        //不为超级管理员且权限验证已开启且当前权限为空
        if (!securityUser.getIsSupperAdmin()){
            if (securityProperties.getEnabled() && securityUser.getAuthorities().size() == 0) {
                return ResResultUtil.error(ResResultEnum.NO_ANY_AUTHENTICATION);
            }
        }

        String token = TokenUtil.createToken(securityUser, ProjectConstant.JWT_EXPIRE_TIME);
        return ResResultUtil.success(token);
    }
}
