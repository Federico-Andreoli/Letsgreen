package it.unimib.lets_green.vehicleModel;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class VehicleModels {
    @SerializedName("data")
    @Expose
    private ModelData data;

    /**
     * No args constructor for use in serialization
     *
     */
    public VehicleModels() {
    }

    /**
     *
     * @param data
     */
    public VehicleModels(ModelData data) {
        super();
        this.data = data;
    }

    public ModelData getData() {
        return data;
    }

    public void setData(ModelData data) {
        this.data = data;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(it.unimib.lets_green.vehicleModel.VehicleModels.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("data");
        sb.append('=');
        sb.append(((this.data == null)?"<null>":this.data));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
