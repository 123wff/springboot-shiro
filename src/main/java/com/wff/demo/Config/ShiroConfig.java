package com.wff.demo.Config;

import com.wff.demo.shiro.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager  securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置Shiro内置的过滤器
        Map<String,String> filterMap=new LinkedHashMap<String,String>();
        //认证过滤器

        //授权过滤器
       filterMap.put("/hello","anon");
       filterMap.put("/login","anon");
        filterMap.put("/add","perms[user:add]");
      // filterMap.put("/update","authc");
        filterMap.put("/*","authc");

        //修改调增的登录页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 创建DefaultWebSecurityManager
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("realm")Realm realm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean(name="realm")
    public Realm getRealm(){
        return new Realm();
    }

}
