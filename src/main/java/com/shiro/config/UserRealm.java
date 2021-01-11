package com.shiro.config;

import com.shiro.pojo.User;
import com.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");

        //获取当前登录对象，通过下面的认证doGetAuthenticationInfo下的new SimpleAuthenticationInfo()的第一个参数获取
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User)subject.getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission(currentUser.getPerms());
        return info;
    }

    /**
     * 认证
     * @return  token是前台的用户信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userService.queryUserByName(userToken.getUsername());
        if(user == null){
            return null; //返回null，自动抛异常IndexController中login下的catch异常
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
