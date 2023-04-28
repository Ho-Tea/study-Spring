package dev.be.async.service;


import dev.be.async.controller.AsyncController;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AsyncService {


    private final EmailService emailService;
    public void asyncCall_1(){//빈주입을 받아서 사용하는 것
        System.out.println("[asyncCall_1 : -> " + Thread.currentThread().getName());
        emailService.sendMail();
        emailService.sendMailWithCustomThreadPool();
    }
    public void asyncCall_2(){  //여기서도 비동기로 동작을 하는 지 확인
        System.out.println("[asyncCall_2 : -> " + Thread.currentThread().getName());
        EmailService emailService = new EmailService();
        emailService.sendMail();    //다 다른 쓰레드가 처리하길 희망했지만 그렇지 않았다
        emailService.sendMailWithCustomThreadPool();
    }
    public void asyncCall_3(){
        System.out.println("[asyncCall_3 : -> " + Thread.currentThread().getName());
        sendMail();
    }

    @Async
    public void sendMail(){
        System.out.println("[sendMail : -> " + Thread.currentThread().getName());
    }

}
