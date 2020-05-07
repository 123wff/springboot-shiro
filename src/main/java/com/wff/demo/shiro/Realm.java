package com.wff.demo.shiro;

import com.wff.demo.entities.User;
import com.wff.demo.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class Realm extends AuthorizingRealm {

    /**
     * 执行授权；逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源授权
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //添加授权资源字符串
        //info.addStringPermission("user:add");
        //获取当前登录用户
        Subject subject= SecurityUtils.getSubject();
        User user= (User) subject.getPrincipal();
        User dbuser= (User) userMapper.selectOne(user.getId());
        info.addStringPermission(dbuser.getPerms());
        return info;
    }

    @Autowired
    private UserMapper userMapper;
    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        //设置数据库的用户名和密码

        //编写shiro判断
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        User user=new User();
        user.setUsername(token.getUsername());
        user=userMapper.selectOne(user);
        System.out.println(user);
        if(user==null){
            return null;
        }
        //判断密码
        return new SimpleAuthenticationInfo("user",user.getPassword()," ");

    }
}
