package it.unimib.lets_green.ui.dashboard;

public class Plant {

    private String name;
    private String commonName;
    private String species;
    private String description;
    private String co2Absorption;

    public Plant(String name, String commonName, String species, String description, String co2Absorption) {
        this.name = name;
        this.commonName = commonName;
        this.species = species;
        this.description = description;
        this.co2Absorption = co2Absorption;
    }

    public String getName() {
        return name;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getSpecies() {
        return species;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCo2Absorption() {
        return co2Absorption;
    }

    public void setCo2Absorption(String co2Absorption) {
        this.co2Absorption = co2Absorption;
    }
}