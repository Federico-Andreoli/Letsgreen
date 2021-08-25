package it.unimib.lets_green.ui.dashboard;

import java.util.ArrayList;

public class Plant {

    private String name;
    private String commonName;
    private String species;

    public Plant(String name, String commonName, String species) {
        this.name = name;
        this.commonName = commonName;
        this.species = species;
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

    public ArrayList<Plant> createPlantList(int numPlants) {
        ArrayList<Plant> plants = new ArrayList<Plant>();

        for(int i = 1; i <= numPlants; i++) {
            plants.add(new Plant(getName(), getCommonName(), getSpecies()));
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
