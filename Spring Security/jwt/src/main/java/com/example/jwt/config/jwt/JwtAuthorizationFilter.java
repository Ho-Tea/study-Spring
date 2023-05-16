package com.example.jwt.config.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.model.Users;
import com.example.jwt.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 시큐리티가 filter가지고 있는데 그 필터중에 BasicAuthenticationFilter라는 것이 존재
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있다
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 타지 않는다

// 인가
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UsersRepository usersRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UsersRepository usersRepository) {
        super(authenticationManager);
        this.usersRepository = usersRepository;
    }

    // 인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 타게 된다
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        // header가 있는지 확인
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        // JWT 토큰을 검증을 해서 정상적인 사용자인지 확인한다
        System.out.println("header : " + header);
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
        // loadByUsername이 호출됨.
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        // 서명이 정상적으로 됨
        if (username != null) {
            Users user = usersRepository.findByUsername(username);

            // Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 준다
            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                    null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                    principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);




    }}
