package com.example.security1.config;


import com.example.security1.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity  //스프링 시큐리티 필터가 스프링 필터 체인에 등록이 된다
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public PasswordEncoder encoderPwd(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // 우리는 토큰을 사용하기 때문에 csrf설정은 disable

                .authorizeHttpRequests()    //HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/manager/**").access(AuthorityAuthorizationManager.hasRole("ADMIN"))
                .anyRequest()
                .permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")  // /login 이라는 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해 준다
                .successHandler((eq, resp, authentication) -> {
                    System.out.println("디버그 : 로그인이 완료되었습니다");
                    resp.sendRedirect("/");
                })
                .and()
                .oauth2Login()
                .loginPage("/login")   // 구글 로그인이 완료된 뒤의 후처리가 필요하다
                .userInfoEndpoint()
                .userService(principalOauth2UserService);


        return http.build();

    }

}
