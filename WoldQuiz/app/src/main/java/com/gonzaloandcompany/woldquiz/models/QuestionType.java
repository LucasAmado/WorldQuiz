package com.gonzaloandcompany.woldquiz.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum QuestionType {
    CAPITAL("¿Cuál es la capital de x ?"),
    CURRENCY("¿De qué país es la moneda x ?"),
    BORDERS("¿Cuáles de los siguientes países son limítrofes de x ?"),
    FLAG("¿A qué país pertenece la siguiente bandera?"),
    LANGUAGE("¿Cuál de los siguientes idiomas es el idioma oficial de x ?");
    private String description;



}
