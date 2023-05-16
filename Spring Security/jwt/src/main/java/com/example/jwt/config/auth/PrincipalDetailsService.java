package com.example.jwt.config.auth;


import com.example.jwt.model.Users;
import com.example.jwt.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



// http://localhost:8080/login -> SecurityConfig에서 formLogin().disable() 해 놓았기 때문에 여기서 동작을 안한다
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsername(username);
        return new PrincipalDetails(users);
    }
}
