package com.gonzaloandcompany.woldquiz.models.unsplash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tag {

    private String type;
    private String title;
    private Source source;
}
