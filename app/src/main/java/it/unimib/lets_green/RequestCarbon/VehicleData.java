package it.unimib.lets_green.RequestCarbon;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class VehicleData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private VehicleAttributes attributes;

    /**
     * No args constructor for use in serialization
     *
     */
    public VehicleData() {
    }

    /**
     *
     * @param attributes
     * @param id
     * @param type
     */
    public VehicleData(String id, String type, VehicleAttributes attributes) {
        super();
        this.id = id;
        this.type = type;
        this.attributes = attributes;
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

    public VehicleAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(VehicleAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "VehicleData{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
