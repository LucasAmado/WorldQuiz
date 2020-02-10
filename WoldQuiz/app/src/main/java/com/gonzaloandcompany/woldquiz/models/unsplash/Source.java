package com.gonzaloandcompany.woldquiz.models.unsplash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Source {

    private Ancestry ancestry;
    private String title;
    private String subtitle;
    private String description;
    private String metaTitle;
    private String metaDescription;
    private CoverPhoto coverPhoto;
}
