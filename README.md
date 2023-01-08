# ♻️ study-Spring

<br/>

### Introduction(중요 부분 발췌)
- JDBC
- JDBC Template
- JPA
- Spring Data JPA
- AOP

<br/>

## 정리
  - 프로젝트 시작(Initializer)
    - Gradle - Groovy -> Build Tool(라이브러리를 땡겨온다)
    - Dependencies : `Spring Web`, `Thymeleaf` <br> spring-boot-starter(공통) : 스프링 부트 + 스프링 코어 + 로깅(log)
      - `Spring Web` : 웹지향 통합기능을 제공(서블릿 리스너, 톰캣, 스프링 웹 MVC 등)
      - `Thymeleaf` : HTML을 만들어주는 템플릿 엔진<br> html태그에 속성을 추가해 페이지에 동적으로 값을 추가하거나 처리할 수 있다.
      - Spring Boot : Spring을 지원하기 위한 프레임워크(내장 Tomcat) Intializer로 Spring Boot 바로 생성


  - 스프링 웹 개발 기초
    - 정적 컨텐츠
    - MVC와 템플릿 엔진 -> Web
    - API -> `Android` or `React` or `Server`
      - `@ResponseBody` 를 사용하면 `viewResolver`를 사용하지 않음
      - 대신에 HTTP의 BODY에 문자 내용을 직접 반환
      - `viewResolver`대신에 `HttpMessageConverter`가 동작
        - 기본 문자 처리: `StringHttpMessageConverter`
        - 기본 객체 처리: `MappingJackson2HttpMessageConverter`

  - 스프링 빈과 의존관계
    - (1) 컴포넌트 스캔
      - `@Component`애노테이션이 있으면 스프링 빈으로 자동 등록된다.
      - 생성자에 `@Autowired`가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어준다
    <br> 이렇게 객체 의존관계를 외부에서 넣어주는 것을 DI(의존성 주입)이라 한다.
        - 스프링 빈은 싱글톤으로 설정하는게 일반적이다
      - 의존성주입 : 필드 주입, setter 주입, 생성자 주입 (생성자 주입이 가장 많이 쓰인다)
      ``` java
      @Autowired
      public MemberController(MemberService memberservice){
        this.memberservice = memberservice
      }
      ```

    - (2) 자바코드로 직접 스프링 빈 등록
      - `@Configuration`, `@Bean`

  - Test부분
    ```java
    @SpringBootTest //스프링 컨테이너와 테스트를 함께 실행
    @Transactional //테스트 시작전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다
    class MemberServiceIntegrationTest {
      @Autowired MemberService memberService;
      @Autowired MemberRepository memberRepository;   // Test에서는 Injection하고 끝이므로 필드 DI가 유용
    }
    ```


  - 스프링 DB 접근 기술
    - 순수 JDBC (`DataSource` 사용)
    - JDBC Template (`JdbcTemplate` 사용)
    - JPA(`EntityManager` 사용)
      - Table 대상으로 Query를 날리는 것이 아닌 객체를 대상으로 Query를 날린다(JPQL)
      - `@Entity, @Id, @GeneratedValue(strategy = GenerationType.IDENTITY), 서비스계층(@Transactional)`
    - Spring Data JPA
      - 기본 CRUD기능을 제공한다
      - `JPARepository`를 상속받는 인터페이스만으로 구현 가능
        - 스프링 데이터 JPA가 위 상속받는 인터페이스를 스프링 빈으로 자동 등록해준다
      - 복잡한 동적 쿼리는 Querydsl이라는 라이브러리를 이용하면 된다


  - AOP
    - 공통관심사항 vs 핵심관심사항을 분리  

      <img src = "image/AOP.jpeg" width = "300">

    - `@Aspect, @Around("execution(* com.example.introduction..*(..))")`  

      <img src = "image/AOP1.jpeg" width = "300">  

      <img src = "image/AOP2.jpeg" width = "300">
