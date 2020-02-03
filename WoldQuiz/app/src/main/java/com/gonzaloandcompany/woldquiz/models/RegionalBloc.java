package com.gonzaloandcompany.woldquiz.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegionalBloc {
    public String acronym;
    public String name;
    public List<Object> otherAcronyms = null;
    public List<Object> otherNames = null;
}
