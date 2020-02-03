package com.gonzaloandcompany.woldquiz.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Quiz {
    private long id;
    private String question;
    private List<Answer> answers;

}
