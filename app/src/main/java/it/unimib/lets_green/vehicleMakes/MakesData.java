package it.unimib.lets_green.vehicleMakes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MakesData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private MakesAttributes makesAttributes;

    /**
     * No args constructor for use in serialization
     *
     */
    public MakesData() {
    }

    /**
     *
     * @param makesAttributes
     * @param id
     * @param type
     */
    public MakesData(String id, String type, MakesAttributes makesAttributes) {
        super();
        this.id = id;
        this.type = type;
        this.makesAttributes = makesAttributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MakesAttributes getMakesAttributes() {
        return makesAttributes;
    }

    public void setMakesAttributes(MakesAttributes makesAttributes) {
        this.makesAttributes = makesAttributes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(it.unimib.lets_green.vehicleMakes.MakesData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("makesAttributes");
        sb.append('=');
        sb.append(((this.makesAttributes == null)?"<null>":this.makesAttributes));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
//public class MakesData implements Parcelable {
//
//    private String id;
//    private String type;
//    private MakesAttributes makesAttributes;
//
//    public MakesData(String id, String type, MakesAttributes makesAttributes) {
//        this.id = id;
//        this.type = type;
//        this.makesAttributes = makesAttributes;
//    }
//
//    public MakesData() {
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public MakesAttributes getMakesAttributes() {
//        return makesAttributes;
//    }
//
//    public void setMakesAttributes(MakesAttributes makesAttributes) {
//        this.makesAttributes = makesAttributes;
//    }
//
//    @Override
//    public String toString() {
//        return "MakesData{" +
//                "id='" + id + '\'' +
//                ", type='" + type + '\'' +
//                ", makesAttributes=" + makesAttributes +
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
//        dest.writeString(this.id);
//        dest.writeString(this.type);
//        dest.writeParcelable(this.makesAttributes, flags);
//    }
//
//    public void readFromParcel(Parcel source) {
//        this.id = source.readString();
//        this.type = source.readString();
//        this.makesAttributes = source.readParcelable(MakesAttributes.class.getClassLoader());
//    }
//
//    protected MakesData(Parcel in) {
//        this.id = in.readString();
//        this.type = in.readString();
//        this.makesAttributes = in.readParcelable(MakesAttributes.class.getClassLoader());
//    }
//
//    public static final Parcelable.Creator<MakesData> CREATOR = new Parcelable.Creator<MakesData>() {
//        @Override
//        public MakesData createFromParcel(Parcel source) {
//            return new MakesData(source);
//        }
//
//        @Override
//        public MakesData[] newArray(int size) { return new MakesData[size];
//        }
//    };
//}
