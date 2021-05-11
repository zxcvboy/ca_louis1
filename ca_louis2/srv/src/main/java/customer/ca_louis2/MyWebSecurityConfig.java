package customer.ca_louis2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    // 指定密码的加密方式
    @SuppressWarnings("deprecation")
    @Bean
    PasswordEncoder passwordEncoder(){
        // 不对密码进行加密
        return NoOpPasswordEncoder.getInstance();
    }
    /*
    // 配置用户及其对应的角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root").password("123").roles("DBA")
                .and()
                .withUser("admin").password("123").roles("ADMIN")
                .and()
                .withUser("hangge").password("123").roles("USER");
    }
    */
    // 配置 URL 访问权限
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 开启 HttpSecurity 配置
            .anyRequest().permitAll()// 用户访问其它URL都必须认证后访问（登录后访问）
            //.and().formLogin().loginProcessingUrl("/login").permitAll() // 开启表单登录并配置登录接口
            .and().csrf().disable(); // 关闭csrf
    }
}