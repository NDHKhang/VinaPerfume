package com.example.webapp.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Category implements Serializable {

    private int id;

    private String name;

    private String description;

    private boolean status;

}
