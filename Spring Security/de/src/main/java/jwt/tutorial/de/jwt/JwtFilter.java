package jwt.tutorial.de.jwt;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.io.IOException;


// JWT를 위한 커스텀 필터를 만들기 위해 JWTFilter 클래스 생성
// GenericFilterBean 을 extends해서 doFilter Override, 실제 필터링 로직은 doFilter 내부에 작성
// doFilter는 토큰의 인증정보를 SecurityContext에 저장하는 역할을 수행
public class JwtFilter extends GenericFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;
    public JwtFilter(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        logger.info("URI의 주소는 : "+ requestURI);

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}'인증정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        }else {
            logger.debug("유효한 JWT토큰이 없습니다, uri: {}", requestURI);
        }
        chain.doFilter(request, response);
        // resolveToken을 통해 토큰을 받아와서 유효성 검증을 하고 정상 토큰이면 SecurityContext에 저장한다
    }




    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
    // Request Header에서 토큰정보를 꺼내오기 위한 resolveToken메서드 추가
}


