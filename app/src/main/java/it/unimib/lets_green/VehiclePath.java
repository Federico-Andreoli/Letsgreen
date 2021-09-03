package it.unimib.lets_green;


import android.os.Parcel;
import android.os.Parcelable;

public class VehiclePath implements Parcelable {

    private String pathName;
    private String pathCarbon;

    public VehiclePath(String pathName, String pathCarbon) {
        this.pathName = pathName;
        this.pathCarbon = pathCarbon;
    }

    public VehiclePath() {

    }

    protected VehiclePath(Parcel in) {
        pathName = in.readString();
        pathCarbon = in.readString();
    }

    public static final Creator<VehiclePath> CREATOR = new Creator<VehiclePath>() {
        @Override
        public VehiclePath createFromParcel(Parcel in) {
            return new VehiclePath(in);
        }

        @Override
        public VehiclePath[] newArray(int size) {
            return new VehiclePath[size];
        }
    };

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getPathCarbon() {
        return pathCarbon;
    }

    public void setPathCarbon(String pathCarbon) {
        this.pathCarbon = pathCarbon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pathName);
        dest.writeString(pathCarbon);
    }
}
