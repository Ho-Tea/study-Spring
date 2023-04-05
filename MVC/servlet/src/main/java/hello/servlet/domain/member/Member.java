package hello.servlet.domain.member;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private int age;

    public Member(String name, int age){
        this.name = name;
        this.age = age;

    }


}
