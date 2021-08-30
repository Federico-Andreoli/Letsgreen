package it.unimib.lets_green.vehicleModel;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class ModelData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("attributes")
    @Expose
    private ModelAttributes attributes;

    /**
     * No args constructor for use in serialization
     *
     */
    public ModelData() {
    }

    /**
     *
     * @param attributes
     * @param id
     * @param type
     */
    public ModelData(String id, String type, ModelAttributes attributes) {
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

    public ModelAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ModelAttributes attributes) {
        this.attributes = attributes;
    }
}
