package com.lylbp.project.controller.bg;

import com.lylbp.core.constant.ProjectConstant;
import com.lylbp.core.entity.ResResult;
import com.lylbp.common.utils.ResResultUtil;
import com.lylbp.common.utils.TokenUtil;
import com.lylbp.project.entity.SecurityUser;
import com.lylbp.project.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author weiwenbin
 * @Date 2020/7/1 下午2:38
 */
@RestController
@RequestMapping("/bg/auth")
@Api(tags = "登录相关")
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation("后台用户登录")
    public ResResult<String> login() {
        SecurityUser securityUser = authService.login("admin", "admin");
        String token = TokenUtil.createToken(securityUser, ProjectConstant.JWT_EXPIRE_TIME);
        return ResResultUtil.success(token);
    }

    @PostMapping("/A")
    @ApiOperation("测试")
    public ResResult<String> a() {
        return ResResultUtil.success("测试！");
    }
}
