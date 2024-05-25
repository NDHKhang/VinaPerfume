package com.example.webapp.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Account implements Serializable {

    private String id;
    private String username;
    private String email;

}
