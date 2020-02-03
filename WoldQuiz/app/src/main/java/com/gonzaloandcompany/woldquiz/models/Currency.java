package com.gonzaloandcompany.woldquiz.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Currency {
    public String code;
    public String name;
    public String symbol;
}