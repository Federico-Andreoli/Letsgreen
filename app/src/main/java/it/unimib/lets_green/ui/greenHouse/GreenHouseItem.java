package it.unimib.lets_green.ui.greenHouse;

public class GreenHouseItem {
    private int mImageResource;
    private String namePlant;
    private String hp;

    public GreenHouseItem(){

    }

    public GreenHouseItem(int ImageResource, String text1, String hp) {
        this.mImageResource=ImageResource;
        this.namePlant=text1;
        this.hp = hp;
    }

    public String getNamePlant() {
        return namePlant;
    }

    public String getHp() { return hp; }

}
