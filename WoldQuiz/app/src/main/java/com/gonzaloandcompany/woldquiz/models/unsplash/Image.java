package com.gonzaloandcompany.woldquiz.models.unsplash;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Image {

    private Integer total;

    private Integer totalPages;

    private List<Result> results;
}