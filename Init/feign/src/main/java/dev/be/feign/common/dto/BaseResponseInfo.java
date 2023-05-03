package dev.be.feign.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  //json을 받는 입장에서는 null이면 없는 걸로 친다
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseInfo {
    private String name;
    private Long age;
    private String header;
}
