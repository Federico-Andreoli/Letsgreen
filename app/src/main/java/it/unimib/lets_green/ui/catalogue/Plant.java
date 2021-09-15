package it.unimib.lets_green.ui.catalogue;

public class Plant {

    // classe per definire l'elemento pianta

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

    public String getDescription() {
        return description;
    }

    public String getCo2Absorption() {
        return co2Absorption;
    }

}