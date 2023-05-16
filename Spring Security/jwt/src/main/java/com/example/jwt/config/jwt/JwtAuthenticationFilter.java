package com.example.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.dto.LoginDto;
import com.example.jwt.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;


// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있다
// /login 요청해서 username, password 전송하면 (POST)
// UsernamePasswordAuthenticationFilter 동작을 한다
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    // /login요청을 하면 로그인 시도를 위해서 실행되는 함수 formLogin하면 자동으로 해주는 것들
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 1. username, password 받아서
        // 2. 정상인지 로그인 시도를 해보는 것 authenticationManager로 로그인 시도를 하면, PrincipalDetailService가 호출된다
        // PrincipalDetailService가 호출, loadUserByUsername() 함수 실행된다
        // 3. PrincipalDetails 를 세션에 담고 (권한 관리를 위해 세션에 담는것이다 권한관리가 필요하지 않으면 세션에 담지 않아도 된다)
        // 4. JWT 토큰을 만들어서 응답

        ObjectMapper om = new ObjectMapper();
        LoginDto loginDto = null;
        try{

            loginDto = om.readValue(request.getInputStream(), LoginDto.class);

            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴된다
            // DB에 있는 username과 password가 일치한다

            // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
            // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
            // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
            // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
            // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

            // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
            // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
            // 결론은 인증 프로바이더에게 알려줄 필요가 없음.

            // 인증이 완료되면 authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return 해주는 것
            // 리턴의 이유는 권한관리를 security가 대신 해주기 때문에 편해지려고 하는거임
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음, 근데 단지 권한 처리 때문에 session에 넣어준다
        }catch (IOException e){
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        System.out.println("토큰 생성 완료");

        Authentication authentication = authenticationManager.authenticate(authenticationToken);


        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return authentication;

    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행된다
    // JWT 토큰을 만들어서 request요청한 사용자에게 JWT토큰을 response해주면 된다
    // JWT Token 생성해서 response에 담아주기

    // RSA 방식은 아니고 Hash 암호 방식
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUsers().getId())
                .withClaim("username", principalDetails.getUsers().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
        System.out.println(jwtToken);
    }
}
