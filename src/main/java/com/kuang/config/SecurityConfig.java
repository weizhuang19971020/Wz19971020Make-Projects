package com.kuang.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    // 定制请求的授权规则
   // 首页所有人可以访问
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

     // 开启自动配置的登录功能,没有权限的时候，会跳转到登录的页面！
     // /login?error 重定向到这里表示登录失败
     //定制登录页
        http.formLogin().loginPage("/toLogin");

        //防止网站攻击
        http.csrf().disable();//关闭

        http.logout().logoutSuccessUrl("/");

        http.rememberMe().rememberMeParameter("remember");
  }
   //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                        .withUser("kuangshen").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2")
                        .and()
                        .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3");
    }
}