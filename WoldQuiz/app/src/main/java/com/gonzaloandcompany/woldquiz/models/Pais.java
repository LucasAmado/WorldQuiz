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

    public List<String> topLevelDomain;

    public String alpha2Code;

    public String alpha3Code;

    public List<String> callingCodes;

    public String capital;

    public List<String> altSpellings;

    public String region;

    public String subregion;

    public Integer population;

    public List<Double> latlng;

    public String demonym;

    public Double area;

    public Double gini;

    public List<String> timezones;

    public List<String> borders;

    public String nativeName;

    public String numericCode;

    public List<Currency> currencies;

    public List<Language> languages;

    public Translations translations;

    public String flag;

    public List<RegionalBloc> regionalBlocs;

    public String cioc;
}
