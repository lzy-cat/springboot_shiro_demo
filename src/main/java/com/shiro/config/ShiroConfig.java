package com.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //3、ShiroFilterFactoryBean 过滤器 从下往上写，逻辑更清晰 关联DefaultWebSecurityManager
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

        /**
         * anon 无需认证即可访问
         * authc 必须要认证后才能访问
         * user     必须拥有记住我功能才能访问
         * perms    根据用户的权限来访问资源
         * role     根据角色权限访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update");
        //过滤
        bean.setFilterChainDefinitionMap(filterMap);
        //没有认证，跳转登录页
        bean.setLoginUrl("/toLogin");
        //关联关联DefaultWebSecurityManager
        bean.setSecurityManager(securityManager);
        return bean;
    }

    //2、DefaultWebSecurityManager 关联realm对象通过@Bean给spring容器管理，通过spring传进来
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm对象
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    //1、创建realm对象
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    /**
     * 用于thymeleaf和shiro标签配合使用，需要配置这个bean，根据权限判断是否要显示该功能
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
