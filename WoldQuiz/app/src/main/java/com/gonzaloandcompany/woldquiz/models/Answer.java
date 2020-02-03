package com.gonzaloandcompany.woldquiz.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Answer {
    private long id;
    private String answer;
    private boolean correct;

}
