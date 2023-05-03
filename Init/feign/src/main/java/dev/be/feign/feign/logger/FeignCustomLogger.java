package dev.be.feign.feign.logger;

import feign.Logger;
import feign.Request;
import feign.Response;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class FeignCustomLogger extends Logger {
    // ... 은 가변인자로 같은 형식의 여러개의 매개변수를 받을 수 있다
    @Override
    protected void log(String configKey, String format, Object... args) {
        //log를 어떤 형식으로 남길지 정해준다
        System.out.println(String.format(methodTag(configKey) + format, args));
    }

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        System.out.println(request);
    }


    // 요거 하나라 위의 메서드 2개를 컨트롤 할 수 있다
//    @Override
//    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
//        String protocolVersion = resolveProtocolVersion(response.protocolVersion());
//        String reason = response.reason() != null && logLevel.compareTo(Level.NONE) > 0? " " + response.reason() : "";
//        int status = response.status();
//        log(configKey, "<-- %s %s%s (%sns)", protocolVersion, status, reason, elapsedTime);
//    }
}
