package it.unimib.lets_green;

public class GreenHouseItem {
    private int mImageResource;
    private String namePlant;


    public GreenHouseItem(){

    }

    public GreenHouseItem(int ImageResource, String text1) {
//        mImageResource = ImageResource;
//        mText1 = text1;
        this.mImageResource=ImageResource;
        this.namePlant=text1;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getNamePlant() {
        return namePlant;
    }


}
