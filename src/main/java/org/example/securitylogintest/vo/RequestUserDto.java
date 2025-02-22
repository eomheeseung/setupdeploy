package org.example.securitylogintest.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestUserDto {
    private String email;
    private String password;
    private String name;
}
