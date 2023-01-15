package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autoWiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestAutowired.class);
    }

    static class TestAutowired{
        @Autowired(required = false)
        public void setNoBean1(Member member){
            System.out.println("setNoBean1 " + member);
        }
        @Autowired
        public void setNoBean2(@Nullable Member member){
            System.out.println("setNoBean2 " + member);
        }
        @Autowired
        public void setNoBean3(Optional<Member> member){
            System.out.println("setNoBean3 " + member);
        }
    }
}
