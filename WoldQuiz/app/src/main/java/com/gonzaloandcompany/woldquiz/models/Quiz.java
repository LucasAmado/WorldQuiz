package com.gonzaloandcompany.woldquiz.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Quiz {
    private Question question;
    private List<Answer> answers;
    private Answer selected;
    private Answer correct;
    private int points;
    private QuestionType type;


}
