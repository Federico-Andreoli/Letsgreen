package it.unimib.lets_green.vehicleMakes;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//import javax.annotation.Generated;

//@Generated("jsonschema2pojo")
public class VehicleMakes {

    @SerializedName("data")
    @Expose
    private MakesData makesData;

    /**
     * No args constructor for use in serialization
     *
     */
    public VehicleMakes() {
    }

    /**
     *
     * @param makesData
     */
    public VehicleMakes(MakesData makesData) {
        super();
        this.makesData = makesData;
    }

    public void displayMessage(Context context){
        Toast.makeText(context,"Clicked item " + this.getData().getMakesAttributes().getName(), Toast.LENGTH_SHORT).show();
    }

    public MakesData getData() {
        return makesData;
    }

    public void setData(MakesData data) {
        this.makesData = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(it.unimib.lets_green.vehicleMakes.VehicleMakes.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("data");
        sb.append('=');
        sb.append(((this.makesData == null)?"<null>":this.makesData));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}

//public class VehicleMakes implements Parcelable {
//
//
//    private MakesData makesData;
//
//    public VehicleMakes(MakesData makesData) {
//        this.makesData = makesData;
//
//    }
//
//    public VehicleMakes() {
//
//    }
//
//
//
//    public MakesData getVehicleData() { return makesData; }
//
//    public void setVehicleData(MakesData makesData) { this.makesData = makesData; }
//
//    @Override
//    public String toString() {
//        return "VehicleMakes{" +
//                "makesData=" + makesData +
//                '}';
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeParcelable(this.makesData, flags);
//
//    }
//
//    public void readFromParcel(Parcel source) {
//        this.makesData = source.readParcelable(MakesData.class.getClassLoader());
//
//    }
//
//    protected VehicleMakes(Parcel in) {
//        this.makesData = in.readParcelable(MakesData.class.getClassLoader());
//
//    }
//
//    public static final Parcelable.Creator<VehicleMakes> CREATOR = new Parcelable.Creator<VehicleMakes>() {
//        @Override
//        public VehicleMakes createFromParcel(Parcel source) {
//            return new VehicleMakes(source);
//        }
//
//        @Override
//        public VehicleMakes[] newArray(int size) {
//            return new VehicleMakes[size];
//        }
//    };
//}
