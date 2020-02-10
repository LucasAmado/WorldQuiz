package com.gonzaloandcompany.woldquiz.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    private String nombre, urlFoto, email;
    private int puntos, partidas;
    //TODO preguntar miguel Double efectividad

    public UserEntity(String nombre, String urlFoto, int puntos, int partidas) {
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.puntos = puntos;
        this.partidas = partidas;
    }
}
