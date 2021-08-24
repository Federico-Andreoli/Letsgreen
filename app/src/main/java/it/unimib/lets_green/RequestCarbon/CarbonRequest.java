package it.unimib.lets_green.RequestCarbon;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class CarbonRequest {
    @SerializedName("data")
    @Expose
    private VehicleData data;

    /**
     * No args constructor for use in serialization
     *
     */
//    public CarbonRequest() {
//    }

    /**
     *
     * @param data
     */
    public CarbonRequest(VehicleData data) {
        super();
        this.data = data;
    }

    public VehicleData getData() {
        return data;
    }

    public void setData(VehicleData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CarbonRequest{" +
                "data=" + data +
                '}';
    }
}
