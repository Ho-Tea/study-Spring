package com.example.security1.oauth;

import com.example.security1.auth.PrincipalDetails;
import com.example.security1.model.User;
import com.example.security1.provider.FacebookUserInfo;
import com.example.security1.provider.GoogleUserInfo;
import com.example.security1.provider.NaverUserInfo;
import com.example.security1.provider.OAuth2UserInfo;
import com.example.security1.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    //Config파일에서 말한 후처리가 이함수에서 진행이된다
    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("getAccessToken :" + userRequest.getAccessToken().getTokenValue());

        // 구글로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인을 완료
        // -> code를 리턴(OAuth-Client 라이브러리가 받는다)
        // -> Access Token을 요청한다(라이브러리가)
        // 여기까지가 userRequest 정보
        // userRequest로 회원 프로필을 받는다(이때 사용되는 함수가 loadUser함수)
        // 즉, userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원 프로필을 받아준다
        log.info("getAttributes :" + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }

        // 회원가입을 강제로 진행할 예정
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId; // google_1293814782
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_ADMIN";

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            userEntity = User.builder()
                    .username(username)
                    .provider(provider)
                    .providerId(providerId)
                    .email(email)
                    .role(role)
                    .build();
            userRepository.save(userEntity);
        }
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());

    }
}
