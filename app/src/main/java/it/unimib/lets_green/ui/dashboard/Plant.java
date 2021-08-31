package it.unimib.lets_green.ui.dashboard;

import java.util.ArrayList;

public class Plant {

    // TODO: completare la classe con gli attributi mancanti (e pensare a quali inserire)

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

    public ArrayList<Plant> createPlantList(int numPlants) {
        ArrayList<Plant> plants = new ArrayList<Plant>();

        for(int i = 1; i <= numPlants; i++) {
            plants.add(new Plant(getName(), getCommonName(), getSpecies(), getDescription(), getCo2Absorption()));
        }

        return plants;
    }

    @Override
    public String toString() {
        return "Plant: " +
                "name = " + name +
                ", commonName = " + commonName +
                ", species = " + species;
    }
}
