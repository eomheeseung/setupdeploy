package org.example.securitylogintest.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestUserDto {
    private String email;
    private String password;
    private String name;

    @Builder
    public RequestUserDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public RequestUserDto() {
    }
}
