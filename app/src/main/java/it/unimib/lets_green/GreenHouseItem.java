package it.unimib.lets_green;

public class GreenHouseItem {
    private int mImageResource;
    private String mText1;

    public GreenHouseItem(int ImageResource, String text1) {
        mImageResource = ImageResource;
        mText1 = text1;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }


}
