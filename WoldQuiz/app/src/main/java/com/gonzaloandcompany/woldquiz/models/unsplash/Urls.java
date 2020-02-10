package com.gonzaloandcompany.woldquiz.models.unsplash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Urls {

    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;
}
