package com.projectEarthquake.earthquake_Project;


import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"continent",
"capital",
"languages",
"geonameId",
"south",
"isoAlpha3",
"north",
"fipsCode",
"population",
"east",
"isoNumeric",
"areaInSqKm",
"countryCode",
"west",
"countryName",
"postalCodeFormat",
"continentName",
"currencyCode"
})

public class Country {

@JsonProperty("continent")
private String continent;
@JsonProperty("capital")
private String capital;
@JsonProperty("languages")
private String languages;
@JsonProperty("geonameId")
private Integer geonameId;
@JsonProperty("south")
private Double south;
@JsonProperty("isoAlpha3")
private String isoAlpha3;
@JsonProperty("north")
private Double north;
@JsonProperty("fipsCode")
private String fipsCode;
@JsonProperty("population")
private Integer population;
@JsonProperty("east")
private Double east;
@JsonProperty("isoNumeric")
private Integer isoNumeric;
@JsonProperty("areaInSqKm")
private Integer areaInSqKm;
@JsonProperty("countryCode")
private String countryCode;
@JsonProperty("west")
private Double west;
@JsonProperty("countryName")
private String countryName;
@JsonProperty("postalCodeFormat")
private String postalCodeFormat;
@JsonProperty("continentName")
private String continentName;
@JsonProperty("currencyCode")
private String currencyCode;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("continent")
public String getContinent() {
return continent;
}

@JsonProperty("continent")
public void setContinent(String continent) {
this.continent = continent;
}

@JsonProperty("capital")
public String getCapital() {
return capital;
}

@JsonProperty("capital")
public void setCapital(String capital) {
this.capital = capital;
}

@JsonProperty("languages")
public String getLanguages() {
return languages;
}

@JsonProperty("languages")
public void setLanguages(String languages) {
this.languages = languages;
}

@JsonProperty("geonameId")
public Integer getGeonameId() {
return geonameId;
}

@JsonProperty("geonameId")
public void setGeonameId(Integer geonameId) {
this.geonameId = geonameId;
}

@JsonProperty("south")
public Double getSouth() {
return south;
}

@JsonProperty("south")
public void setSouth(Double south) {
this.south = south;
}

@JsonProperty("isoAlpha3")
public String getIsoAlpha3() {
return isoAlpha3;
}

@JsonProperty("isoAlpha3")
public void setIsoAlpha3(String isoAlpha3) {
this.isoAlpha3 = isoAlpha3;
}

@JsonProperty("north")
public Double getNorth() {
return north;
}

@JsonProperty("north")
public void setNorth(Double north) {
this.north = north;
}

@JsonProperty("fipsCode")
public String getFipsCode() {
return fipsCode;
}

@JsonProperty("fipsCode")
public void setFipsCode(String fipsCode) {
this.fipsCode = fipsCode;
}

@JsonProperty("population")
public Integer getPopulation() {
return population;
}

@JsonProperty("population")
public void setPopulation(Integer population) {
this.population = population;
}

@JsonProperty("east")
public Double getEast() {
return east;
}

@JsonProperty("east")
public void setEast(Double east) {
this.east = east;
}

@JsonProperty("isoNumeric")
public Integer getIsoNumeric() {
return isoNumeric;
}

@JsonProperty("isoNumeric")
public void setIsoNumeric(Integer isoNumeric) {
this.isoNumeric = isoNumeric;
}

@JsonProperty("areaInSqKm")
public Integer getAreaInSqKm() {
return areaInSqKm;
}

@JsonProperty("areaInSqKm")
public void setAreaInSqKm(Integer areaInSqKm) {
this.areaInSqKm = areaInSqKm;
}

@JsonProperty("countryCode")
public String getCountryCode() {
return countryCode;
}

@JsonProperty("countryCode")
public void setCountryCode(String countryCode) {
this.countryCode = countryCode;
}

@JsonProperty("west")
public Double getWest() {
return west;
}

@JsonProperty("west")
public void setWest(Double west) {
this.west = west;
}

@JsonProperty("countryName")
public String getCountryName() {
return countryName;
}

@JsonProperty("countryName")
public void setCountryName(String countryName) {
this.countryName = countryName;
}

@JsonProperty("postalCodeFormat")
public String getPostalCodeFormat() {
return postalCodeFormat;
}

@JsonProperty("postalCodeFormat")
public void setPostalCodeFormat(String postalCodeFormat) {
this.postalCodeFormat = postalCodeFormat;
}

@JsonProperty("continentName")
public String getContinentName() {
return continentName;
}

@JsonProperty("continentName")
public void setContinentName(String continentName) {
this.continentName = continentName;
}

@JsonProperty("currencyCode")
public String getCurrencyCode() {
return currencyCode;
}

@JsonProperty("currencyCode")
public void setCurrencyCode(String currencyCode) {
this.currencyCode = currencyCode;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}