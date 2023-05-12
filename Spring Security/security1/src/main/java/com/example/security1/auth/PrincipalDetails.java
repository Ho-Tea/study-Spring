package com.example.security1.auth;

import com.example.security1.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


        // 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다
        // 로그인을 진행이 완료가 되면 자신만의 Security session을 만들어 준다
        // Security ContextHolder에다가 session 정보를 저장한다
        // 이때 session에 들어갈수 있는 정보는
        // 오브젝트는 무조건 -> Authentication 타입 객체
        // Authentication 안에는 User정보가 있어야 된다
        // User오브젝트 타입 => UserDetails 타입 객체여야 한다
        // 시큐리티 세션(Security Session)에 세션정보를 저장하는데 안에 내용은 Authentication객체여야 하고 User 정보는 UserDetails여야 한다




        @Getter
        public class PrincipalDetails implements UserDetails , OAuth2User {

            private User user;

            private Map<String, Object> attributes;

            // 일반 로그인
            public PrincipalDetails(User user) {
                this.user = user;
            }

            // OAuth 로그인
            public PrincipalDetails(User user, Map<String, Object> attributes) {
                this.user = user;
                this.attributes = attributes;
            }

            @Override
            public Map<String, Object> getAttributes() {
                return attributes;
            }

            // 해당 User의 권한을 리턴하는 곳
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> collect = new ArrayList<>();
                collect.add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return user.getRole();
                    }
                });
                return collect;
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public String getName() {
                return null;
            }
        }
