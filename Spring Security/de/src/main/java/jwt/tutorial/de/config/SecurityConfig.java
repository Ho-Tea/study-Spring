package jwt.tutorial.de.config;


import jakarta.servlet.annotation.WebServlet;
import jwt.tutorial.de.jwt.JwtAccessDeniedHandler;
import jwt.tutorial.de.jwt.JwtAuthenticationEntryPoint;
import jwt.tutorial.de.jwt.JwtSecurityConfig;
import jwt.tutorial.de.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

    @EnableWebSecurity
    @Configuration
    // @EnableGlobalMethodSecurity 는 deprecated 되어
    // @EnableMethodSecurity 추가, @PreAuthorize 어노테이션을 메서드 단위로 추가하기 위해서 사용
    @EnableMethodSecurity(prePostEnabled = true)
    public class SecurityConfig{
        private final TokenProvider tokenProvider;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

        // SecurityConfig 는 TokenProvider, JwtAuthenticationEntryPoint, JwtAccessDeniedHandler 주입
        public SecurityConfig(
                TokenProvider tokenProvider,
                JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                JwtAccessDeniedHandler jwtAccessDeniedHandler
        ){
            this.tokenProvider = tokenProvider;
            this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
            this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;

        }
        // PasswordEncoder는 BCryptPasswordEncoder를 사용한다
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        // HttpSecurity Configure

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    // 우리는 토큰을 사용하기 때문에 csrf설정은 disable

                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    // Exception을 핸들링 할때 우리가 만들었던 클래스들을 추가해준다

                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()
                    // h2-console을 위한 설정
                    //.and()
                    //                .headers()
                    //                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    //                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)) 와 같은 역할

                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    // 세션을 사용하지 않기 때문에 세션 설정을 STATELESS로 설정한다


                    .and()
                    .authorizeHttpRequests()    //HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다는 의미
                    .requestMatchers(toH2Console(), new AntPathRequestMatcher("/api/hello"),
                            new AntPathRequestMatcher("/api/authenticate"),
                            new AntPathRequestMatcher("/api/signup"))//로그인 API와 회원가입 API는 토큰이 없는 상태에서 요청이 들어오기 때문에
                    .permitAll() // api/hello에 대한 요청은 인증없이 접근을 허용하겠다는 뜻
                    .anyRequest().authenticated()   //나머지 요청들은 모두 인증을 받아야 한다
                    .and()
                    .apply(new JwtSecurityConfig(tokenProvider));




            return http.build();

        }

    //WebSecurity Configure
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers( "/h2-console/**","/favicon.ico");
//        // h2-console 하위 모든 요청과 파비콘은 모두 무시하는 것으로 설정 해준다.
//    }


}
