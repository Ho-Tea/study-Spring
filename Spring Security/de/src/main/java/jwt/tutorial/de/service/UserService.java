package jwt.tutorial.de.service;


import jwt.tutorial.de.dto.UserDto;
import jwt.tutorial.de.entity.Authority;
import jwt.tutorial.de.entity.User;
import jwt.tutorial.de.repository.UserRepository;
import jwt.tutorial.de.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    //회원가입, 유저정보 조회 등의 메서드를 만들기 위해 UserService 클래스를 생성
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signup(UserDto userDto) {
        if(userRepository.findOneWithAuthoritiesByUserName(userDto.getUserName()).orElse(null) != null){
            throw new RuntimeException("이미 가입 되어있는 유저입니다");
        }
        // username이 DB에 존재하지 않으면 Authority와 User 정보를 생성해서 UserRepository의 save메서드를 통해 DB에 정보를 저장
        Authority authority = Authority.builder().authorityName("ROLE_USER").build();

        User user = User.builder()
                .userName(userDto.getUserName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();
        // signup 메서드를 통해 가입한 회원은 USER ROLE을 가지고 있다
        // data.sql에서 자동으로 생성되는 admin 계정은 USER, ADMIN ROLE을 가지고 있다
        // 권한차이가 존재

        return userRepository.save(user);
    }


    // 유저, 권한정보를 가져오는 메서드 2개
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username){  // username을 기준으로 정보를 가져온다
        return userRepository.findOneWithAuthoritiesByUserName(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {  // Security Context에 저장된 username 정보만 가져온다
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUserName);
    }
}
