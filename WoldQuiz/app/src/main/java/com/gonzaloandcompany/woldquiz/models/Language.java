package com.gonzaloandcompany.woldquiz.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Language implements Serializable {
    public String iso6391;
    public String iso6392;
    public String name;
    public String nativeName;
}
