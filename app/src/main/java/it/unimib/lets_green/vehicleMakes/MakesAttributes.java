package it.unimib.lets_green.vehicleMakes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MakesAttributes {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number_of_models")
    @Expose
    private Integer numberOfModels;

    /**
     * No args constructor for use in serialization
     *
     */
    public MakesAttributes() {
    }

    /**
     *
     * @param numberOfModels
     * @param name
     */
    public MakesAttributes(String name, Integer numberOfModels) {
        super();
        this.name = name;
        this.numberOfModels = numberOfModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfModels() {
        return numberOfModels;
    }

    public void setNumberOfModels(Integer numberOfModels) {
        this.numberOfModels = numberOfModels;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(it.unimib.lets_green.vehicleMakes.MakesAttributes.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("numberOfModels");
        sb.append('=');
        sb.append(((this.numberOfModels == null)?"<null>":this.numberOfModels));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
//    private String name;
//    private int numbersOfModels;
//
//    public MakesAttributes(String name, int numbersOfModels) {
//        this.name = name;
//        this.numbersOfModels = numbersOfModels;
//    }
//
//    public MakesAttributes() {
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getNumbersOfModels() {
//        return numbersOfModels;
//    }
//
//    public void setNumbersOfModels(int numbersOfModels) {
//        this.numbersOfModels = numbersOfModels;
//    }
//
//    @Override
//    public String toString() {
//        return "MakesAttributes{" +
//                "name='" + name + '\'' +
//                ", numbersOfModels=" + numbersOfModels +
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
//        dest.writeString(this.name);
//        dest.writeInt(this.numbersOfModels);
//    }
//
//    public void readFromParcel(Parcel source) {
//        this.name = source.readString();
//        this.numbersOfModels = source.readInt();
//    }
//
//    protected MakesAttributes(Parcel in) {
//        this.name = in.readString();
//        this.numbersOfModels = in.readInt();
//    }
//
//    public static final Parcelable.Creator<MakesAttributes> CREATOR = new Parcelable.Creator<MakesAttributes>() {
//        @Override
//        public MakesAttributes createFromParcel(Parcel source) {
//            return new MakesAttributes(source);
//        }
//
//        @Override
//        public MakesAttributes[] newArray(int size) { return new MakesAttributes[size]; }
//    };

