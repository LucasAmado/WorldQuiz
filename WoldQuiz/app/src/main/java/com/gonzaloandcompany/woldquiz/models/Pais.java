package com.gonzaloandcompany.woldquiz.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pais {
    public String name;

    public List<String> topLevelDomain = null;

    public String alpha2Code;

    public String alpha3Code;

    public List<String> callingCodes = null;

    public String capital;

    public List<String> altSpellings = null;

    public String region;

    public String subregion;

    public Integer population;

    public List<Double> latlng = null;

    public String demonym;

    public Double area;

    public Double gini;

    public List<String> timezones = null;

    public List<String> borders = null;

    public String nativeName;

    public String numericCode;

    public List<Currency> currencies = null;

    public List<Language> languages = null;

    public Translations translations;

    public String flag;

    public List<RegionalBloc> regionalBlocs = null;

    public String cioc;
}
