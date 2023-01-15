package hello.core.autowired;

import hello.core.AppConfig;
import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean(){
        ApplicationContext ac =  new AnnotationConfigApplicationContext(AutoAppConfig.class ,DiscountService.class);
    }

    static class DiscountService{
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policyList;

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policyList) {
            this.policyMap = policyMap;
            this.policyList = policyList;
            System.out.println("policyMap : " + policyMap);
            System.out.println("policyList : " + policyList);
        }
    }
}
