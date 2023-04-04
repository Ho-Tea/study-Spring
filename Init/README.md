# 💡 헷갈려하는 정의나 개념을 정리하는 곳


## Servlet

  <img src = "image/3.png">

  <img src = "image/4.png">

  - 자바 서블릿은 웹 서버와 직접 데이터를 주고 받지 않고 전문 프로그램에의해 관리<br> 이를 서블릿 컨테이너 라고 한다
  - 서블릿 컨테이너 : 서블릿의 생성, 실행 그리고 소멸 등 LifeCycle을 관리하는 CGI프로그램
  - 서블릿 : **Servlet 인터페이스를 구현한 프로그램으로 서버측 프로그램이다**

- ### Servlet(자바로 구현된 CGI)
  - 클라이언트가 어떠한 요청을 하면 그에 대한 결과를 다시 전송해주어야 하는데, <br> 이러한 역할을 하는 자바 프로그램
  - 자바에서 웹 애플리케이션을 만드는 기술(서블릿은 여러개)
  - 자바에서 동적인 웹페이지를 구현하기 위한 표준
  - 클라이언트의 요청을 처리하고, 그 결과를 반환하는 Servlet클래스의 구현 규칙을 지킨 자바 웹 프로그래밍 기술이다

    <img src = "image/servlet3.png">

- ### CGI
  - 웹서버와 애플리케이션 사이에 데이터를 주고받는 규약
  - 프로그램 종류로는 컴파일방식(C, C++, java)과 인터프리터 방식(php, python)방식이 존재한다
  - <img src = "image/servlet.png">
  - <img src = "image/servlet2.png">

**우리는 cgi규칙을 몰라도 된다**  

**웹서버와 cgi규칙으로 데이터를 주고받는 스크립트 엔진, 서블릿 컨테이너**

## Servlet Container(서블릿 컨테이너)
  - 서블릿의 생성부터 소멸까지의 라이프 사이클을 관리하는 역할
  - 서블릿 컨테이너는 웹 서버와 소켓을 만들고 통신하는 과정을 대신 처리해준다
  - **개발자는 비즈니스 로직에만 집중하면 된다**
  - 서블릿 객체를 싱글톤으로 관리(인스턴스 하나만 생성하여 공유하는 방식)
  - 상태를 유지하게 설계하면 안됨
  - 쓰레드 safety(어떤 공유자원에 여러 쓰레드가 동시에 접근해도 프로그램 실행에 문제가 없는 상태를 의미한다)하지 않다 <br> -> **쓰레드끼리 서로 경쟁한다**

    <img src = "image/arch.png">

    <img src = "image/dispa.png">

    <img src = "image/handler.jpeg">

    <img src = "image/서블릿.png">

  - Spring MVC
    - Spring MVC는 spring에서 제공하는 웹 모듈로 Model, View, Controller 세가지 구성요소를 사용해 <br> 사용자의 다양한 HTTP Request를 처리하고 단순한 텍스트 형식의 응답부터 REST형식의 응답은 물론 <br> View를 표시하는 html을 return하는 응답까지 다양한 응답을 할 수 있도록 하는 프레임 워크

    <img src = "image/프론트.png">

    <img src = "image/cont.png">

    <img src = "image/vi.png">

    <img src = "image/2.jpeg">

    <img src = "image/1.jpeg">


### JSP
  - HTML내에 자바 코드를 삽입하여 웹 서버에서 동적으로 웹페이지를 생성하여 웹브라우저를 돌려주는 언어
  - JSP(Java Server Pages)는 서블릿 기술을 활용하여 동작하는 웹 프로그래밍 기술입니다.
  - JSP는 서블릿 코드를 자동으로 생성하고, 이를 서블릿 컨테이너에서 실행시킵니다.<br> 따라서 JSP는 사실상 서블릿으로 변환되어 실행되는 것이 맞습니다.
  - <img src = "image/jsp2.png">


### Was vs Servlet Container
  - **Was는 서블릿 컨테이너를 포함하는 개념**
  - Was는 매 요청마다 스레드 풀에서 기존스레드를 사용한다
  - Was의 주요 튜닝 포인트는 max thread수
  - 대표적인 Was는 톰캣이 있다
  - 프론트, 백엔드 모두` Was`에 들어간다
  - **스프링 부트는 내장톰캣을 가지고 있기 때문에 서블릿 같은 경우에는 톰캣을 따로 설치후 구동해야 한다**
  - (외장 Was) 개발 툴들이 특정한 구조로 개발이 용이하게 프로젝트를 설정해주고 그것을 빌드하면 `war` 나 `jar`파일이 만들어지며 그 파일들을 톰캣에다가(톰캣 내부 폴더에) 모든 파일들을 다 넣는것이 아닌 jar이나 war 파일들만 넣어서 실행시키면 된다
  - jar파일 내부를 확인해 보면 tomcat이 인식할 수 있는 구조로 변환되어 있는 것을 알 수 있다
  - 내장 톰캣이 있으므로 단순히 스프링부트에서 `war`파일을 만든다음 터미널에서 실행시키기만 하면 된다.

  <img src = "image/war.png">

  <img src = "image/was.png">


### JDBC
  - 자바에서 DB프로그래밍을 하기위해 사용되는 API
  - **DBCP** : 미리 일정량의 DB커넥션을 생성해서 풀에 저장해 두고있다가 HTTP 요청에 따라 필요할 때 **풀에서 커넥션을 가져다 사용하는 기법**
  - 참고로 스프링 부트 2.0부터는 디폴트 커넥션 풀로 **HikariC**P사용
    - 커넥션 풀 사용시 유의사항 : 커넥션의 사용 주체는 WAS 스레드이므로 커넥션 개수는 WAS 스레드 수와 함께 고려해야 한다
    - 커넥션 수를 크게 설정하면 메모리 소모가 큰 대신 동시 접속자 수가 많아지더라도 사용자 대기시간이 상대적으로 줄어들게 되고, <br>반대로 커넥션 개수를 작게 설정하면 메모리 소모는 적은 대신 그만큼 대기시간이 길어질 수 있다 따라서 **적정량의 커넥션 객체를 생성해 두어야 한다**
  - **DataSource** : 커넥션 흭득하기 위한 표준 인터페이스
  - **HikariCP의 DataSource 사용한다**

    
### Web.xml
  - `web.xml`에서 서블릿 맵핑 되는 방법, 인증이 필요한 URL등의 정보를 확인한다
  - `web.xml`은 WebApplication의 Deployment Descriptor(배포 설명자)로써 XML형식의 파일
  - 모든 Web application은 반드시 하나의 Web.xml파일을 가져야 하고 위치는 WEB-INF폴더 아래에 있다
  - web.xml 파일의 설정들은 Web application 시작시 메모리에 로딩된다
  ``` xml
  <web-app>
    <servlet>
      <servlet-name>welcome</servlet-name>
      <servlet-class>servlets.Servlet</servlet-class>
    </servlet>

    <servlet-mapping>
      <servlet-name>welcome</servlet-name>
      <url-pattern>/welcome</url-pattern>
    </servlet-mapping>
  </web-app>
  ```

## 빌드 도구 종류와 차이점
  - 앤트(Ant)
    - 자바 프로젝트 빌드 도구
    - 이클립스라는 IDE에 기본적으로 탑재되어있다(XML 스크립트를 기반) -> **build.xml**
    - 최근에 나온 빌드 도구들과 달리 자동으로 라이브러리를 업데이트 하는 기능이 <br>없기 때문에 현재는 주로 레거시 시스템에서만 사용
    - Ant는 핵심 Maven 플러그인으로써 사용 가능하다.
    - 수정된 Maven플러그인은 Ant에 구현될 수 있으며, <br>Maven프로젝트는 Maven프로젝트 생명주기 안에서 Ant스크립트를 실행하도록 설정하는것이 가능하다

  - 메이븐(Maven)
    - 메이븐은 Ant이후에 나온 자바 빌드 도구로 **자동**으로 라이브러리와 의존성을 관리하는 기능이 있다
    - Ant와 마찬가지로 **XML스크립트**를 기반으로 하며, pom.xml 파일로 의존성을 관리한다
  
  - 그래들(Gradle)
    - 그래들은 가장 최근에 나온 자바 빌드 도구로 그루비 문법을 사용한다
    - Build.gradle에 스크립트를 작성하며, 대규모 프로젝트에서 복잡해지는 경향이 있는 XML기반 스크립트에 비해 관리가 편하다
## ANT
  - 자바 기반의 빌드 툴
    - Ant는 자바기반으로 플랫폼에 독립적으로 실행(운영체제에 구애받지 않고 프로젝트 진행가능)
    - Ant의 빌드 파일은 xml문서의 구조이며, Ant가 작업을 수행할 프로젝트에 대한 정보를 담고 있다
      ```xml
      <project name="프로젝트이름" default="기본타겟이름" basedir="." > 
       <!--빌드파일의 루트태그.--> 
   
      <target name="타겟이름"> <!--실제 프로젝트가 수행할 작업(Task지정) -->
      <property name="프로퍼티이름1" value="프로퍼티값1"/> 
      <property name="프로퍼티이름2" value="프로퍼티값2"/>
      </target>
    
      <target name="타겟이름1">
      <태스크명/>
      <태스크명1 dir="${build}"/>
      <property name="프로퍼티이름3" value="프로퍼티값3"/>
      </target>
    
      <target name="타겟이름2" depends="타겟이름1">
        <태스크명2 속성1="값1" 속성2="값2"/> 
      <!--타겟 내에서 실제로 수행할 작업 -->
      </target>
      </project>
      ```
