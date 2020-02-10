package com.gonzaloandcompany.woldquiz.models.unsplash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ancestry {

    private Type type;
    private Category category;
    private Subcategory subcategory;
}
