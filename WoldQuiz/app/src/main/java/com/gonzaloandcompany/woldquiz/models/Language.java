package com.gonzaloandcompany.woldquiz.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Language {
    public String iso6391;
    public String iso6392;
    public String name;
    public String nativeName;
}
