package customer.ca_louis1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//new-start
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import com.sap.cloud.security.xsuaa.tokenflows.XsuaaTokenFlows;
import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.autoconfiguration.XsuaaTokenFlowAutoConfiguration;
import com.sap.cloud.security.xsuaa.extractor.IasXsuaaExchangeBroker;
import org.springframework.beans.factory.annotation.Autowired;

//new-end

@Configuration
@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    //new-start
    @Autowired
	XsuaaServiceConfiguration xsuaaServiceConfiguration;

     @Autowired
	 XsuaaTokenFlows xsuaaTokenFlows;
    //new-end

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

        // old 
        // http.authorizeRequests() // 开启 HttpSecurity 配置
        //     .anyRequest().permitAll()// 用户访问其它URL都必须认证后访问（登录后访问）
        //     //.and().formLogin().loginProcessingUrl("/login").permitAll() // 开启表单登录并配置登录接口
        //     .and().csrf().disable(); // 关闭csrf

        //new-start 
         http.csrf().disable();
        http.authorizeRequests()
            .antMatchers("/rest/**").authenticated()
            .antMatchers("/odata/**").authenticated()
            .antMatchers("/").permitAll()
            .and()
                .oauth2ResourceServer()
				.bearerTokenResolver(new IasXsuaaExchangeBroker(xsuaaTokenFlows))
				.jwt()
                .jwtAuthenticationConverter(getJwtAuthenticationConverter());
                

        //new-end
    }

    //new-start
   /**
	 * Customizes how GrantedAuthority are derived from a Jwt
	 */
	Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
		TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
		converter.setLocalScopeAsAuthorities(true);
		return converter;
    }
    
    //new-end
}