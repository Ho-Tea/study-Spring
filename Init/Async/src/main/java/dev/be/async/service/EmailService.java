package dev.be.async.service;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Async("defaultTaskExecutor")  //사용할 쓰레드 풀 설정
    public void sendMail(){
        System.out.println("[sendMail] -> "+ Thread.currentThread().getName());
    }

    @Async("messagingTaskExecutor")
    public void sendMailWithCustomThreadPool(){
        System.out.println("[messagingTaskExecutor] -> "+ Thread.currentThread().getName());
    }
}
