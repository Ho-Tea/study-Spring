package dev.be.modulecommon.service;



import dev.be.modulecommon.domain.Member;
import dev.be.modulecommon.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class CommonDemoService {

    public String commonService(){

        return "commonService";
    }
}
