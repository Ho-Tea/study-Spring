package com.example.jwt.config;


import com.example.jwt.config.jwt.JwtAuthenticationFilter;
import com.example.jwt.config.jwt.JwtAuthorizationFilter;
import com.example.jwt.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
// @EnableGlobalMethodSecurity 는 deprecated 되어
// @EnableMethodSecurity 추가, @PreAuthorize 어노테이션을 메서드 단위로 추가하기 위해서 사용
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{

    private final CorsFilter corsFilter;
    // PasswordEncoder는 BCryptPasswordEncoder를 사용한다

    private final UsersRepository usersRepository;

    // HttpSecurity Configure
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class)  //spring filter chain에 내 filter를 등록하는 과정
                .csrf().disable()
                // 우리는 토큰을 사용하기 때문에 csrf설정은 disable
                // Authorization에 토큰을 넣어서 요청한다 -> Bearer 방식
                // Authorization에 ID, PW를 넣어서 요청한다 -> Http Basic 방식

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new MyCustomDsl()) // 커스텀 필터 등록 -> UsernamePasswordAuthenticationFilter를 커스텀 한 것
                // 세션을 사용하지 않기 때문에 세션 설정을 STATELESS로 설정한다
                .and()
                .formLogin().disable()  // 로 해놓아서 UsernamePasswordAuthenticationFilter가 동작을 하지 않는다 그래서 따로 필터 등록을 한것(/login은 된다)
                .httpBasic().disable()  // Http Header의 Authorization에 ID, PW를 넣어서 보낸다 (매 요청마다) -> ID, PW 암호화 안되어 보안에 취약(HTTPS를 쓰면 암호화가 되기는 한다)
                .authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/api/v1/user/**")).hasAnyRole("USER","ADMIN","MANAGER")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/manager/**")).hasAnyRole("MANAGER","ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/api/v1/admin/**")).hasAnyRole("ADMIN")
                .anyRequest().permitAll();


        return http.build();

    }
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(corsFilter)   // @CrossOrigin은 인증이 필요없는 경우만, 인증이 필요한 경우는 시큐리티 필터에 등록해야한다
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, usersRepository));
        }
    }

}