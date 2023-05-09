package jwt.tutorial.de.controller;


import jakarta.validation.Valid;
import jwt.tutorial.de.dto.UserDto;
import jwt.tutorial.de.entity.User;
import jwt.tutorial.de.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api")
    public class UserController {
        private final UserService userService;

        public UserController(UserService userService){
            this.userService = userService;
        }

        @PostMapping("/signup")
        public ResponseEntity<User> signup(@Valid @RequestBody UserDto userDto){
            return ResponseEntity.ok(userService.signup(userDto));
        }

        @GetMapping("/user")
        @PreAuthorize("hasAnyRole('USER', 'ADMIN')")    //@PreAuthorize를 통해서 USER, ADMIN 두가지 권한 모두 허용
        public ResponseEntity<User> getMyUserInfo() {
            return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
        }

        @GetMapping("/user/{username}")
        @PreAuthorize("hasAnyRole('ADMIN')")
        public ResponseEntity<User> getUserInfo(@PathVariable String username) {    // getUserInfo 메서드는 ADMIN 권한만 호출할 수 있도록 설정
            return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());   // getUserInfo메서드는 UserService에서 만들었던
            // username파라미터를 기준으로 유저정보와 권한정보를 리턴하는 API
        }


    }
