package it.unimib.lets_green;

public class GreenHouseItem {
    private int mImageResource;
    private String namePlant;
    private String hp;


    public GreenHouseItem(){

    }

    public GreenHouseItem(int ImageResource, String text1, String hp) {
//        mImageResource = ImageResource;
//        mText1 = text1;
        this.mImageResource=ImageResource;
        this.namePlant=text1;
        this.hp = hp;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getNamePlant() {
        return namePlant;
    }

    public String getHp() { return String.valueOf((Float.parseFloat(hp) * 1000)/365); }

    public void setHp(String hp) { this.hp = hp; }
}
