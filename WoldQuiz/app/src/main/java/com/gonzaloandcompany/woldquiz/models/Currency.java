package com.gonzaloandcompany.woldquiz.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Currency implements Serializable {
    public String code;
    public String name;
    public String symbol;
}