package com.example.asyncmethod;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    private String name;
    private String blog;
}
