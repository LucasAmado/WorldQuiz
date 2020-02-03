package com.gonzaloandcompany.woldquiz.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String nombre, urlFoto, email;
    private int puntos, partidas;
}
