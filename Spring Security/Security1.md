## Spring Security 1  - Session
  - **Setting**
    - `Spring Boot DevTools`
    - `Lombok`
    - `Spring Data JPA`
    - `MySql Driver`
    - `Spring Security`
      - Spring Security 기본설정으로 인해 처음 `localhost:8080`에 접근하면 `login` 페이지가 뜬다
    - `Mustache`
      - `.html`도 `Mustache`가 인식할 수 있게끔
        ``` java
        @Configuration
        public class WebMvcConfig implements WebMvcConfigurer {
          @Override
          public void configureViewResolvers(ViewResolverRegistry registry) {
              MustacheViewResolver resolver = new MustacheViewResolver();

              resolver.setCharset("UTF-8");
              resolver.setContentType("text/html;charset=UTF-8");
              resolver.setPrefix("classpath:/templates/");
              resolver.setSuffix(".html");
              // .html 파일로 만들어도 mustache가 인식을 할 수 있게끔 설정하는 것
              registry.viewResolver(resolver);
              }
        }
        ```
    - `Spring Web`


-------

  - **시큐리티 설정**
    - **스프링 시큐리티 필터가 스프링 필터 체인에 등록이 되기 위해 `SecurityConfig`를 정의**
    - `Login`
      ``` java
      // 스프링 시큐리티 해당 주소를 밑의 메서드가 낚아 챈다 - 
      // (SecurityConfig 파일 생성 후 스프링 시큐리티 기본 로그인 동작이 작동 안함)
      @GetMapping("/login")
      public @ResponseBody String login(){
        return "login";
      }
      ```
      - 따라서, `SecurityConfig`파일을 수정하자
        ``` java
        .and()
                .formLogin()
                .loginPage("/login");
        ```
        - 어느 페이지로 접근하든 `/login` URI로 매핑된다

    - `Password`
      ``` java
      @PostMapping("/join")
      public String join(User user){
        log.info(user.getUsername());
        user.setRole("USER");
        userRepository.save(user);
        return "join";
      }
      // 회원가입은 잘 되지만, 시큐리티로 로그인을 할 수 없다
      // 왜냐하면, 패스워드가 암호화가 안되었기 때문에

      @Bean
      public PasswordEncoder encoderPwd(){
        return new BCryptPasswordEncoder();
      }
      // Encoder를 빈으로 등록해놓아야 한다

      @PostMapping("/join")
      public String join(User user){
        log.info(user.getUsername());
        user.setRole("USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/login";
      }
      ```
     

  - **시큐리티 설정 2**
    - `Login` 수정(`SecurityConfig.java`)
      ``` java
        @EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true) 
        // secured 어노테이션 활성화, preAuthorize 어노테이션 활성화, postAuthorize 어노테이션 활성화
        ...

        .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")  
                // /loginProc 이라는 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해 준다
                // /loginProc 관련 컨트롤러 생성할 필요 없다
                .defaultSuccessUrl("/");
      ```

    - `PrincipalDetails` - **UserDetails**
      - **시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다**
      - `Security ContextHolder`(Session정보가 저장됨)
      - `Session`(Authentication객체가 저장됨)
      - `Authentication`(UserDetails객체가 저장됨)

      ``` java
      // 1. 로그인을 진행이 완료가 되면 자신만의 Security session을 만들어 준다
      // 2. Security ContextHolder에다가 session 정보를 저장한다
      // 3. 이때 session에 들어갈수 있는 정보 오브젝트는 무조건 -> Authentication 타입 객체
      // 4. Authentication 안에는 User정보가 있어야 된다
      // 5. User오브젝트 타입 => UserDetails 타입 객체여야 한다
      // 6. 시큐리티 세션(Security Session)에 세션정보를 저장하는데 안에 내용은 Authentication객체여야 하고 User 정보는 UserDetails여야 한다

      @RequiredArgsConstructor
      public class PrincipalDetails implements UserDetails {

        private final User user;


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
      }
      ```

    - `PrincipalDetailService` - **UserDetailService**
      ``` java
      // 시큐리티 설정에서 loginProcessingUrl("/login");
      // /login 요청이 오면 자동으로 UserDetailsService 타입으로 Ioc되어 있는 loadUserByUsername 함수가 실행
      @Service
      @RequiredArgsConstructor
      public class PrincipalDetailService implements UserDetailsService {

        private UserRepository userRepository;

        // 시큐리티 session = Authentication(내부 UserDetails)
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User userEntity = userRepository.findByUsername(username);
          if(userEntity != null){
            return new PrincipalDetails(userEntity);
          }
          return null;
        }
      }

      ```

  