package it.unimib.lets_green.vehicleModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//import javax.annotation.Generated;

//@Generated("jsonschema2pojo")
public class ModelAttributes {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("vehicle_make")
    @Expose
    private String vehicleMake;

    /**
     * No args constructor for use in serialization
     *
     */
    public ModelAttributes() {
    }

    /**
     *
     * @param year
     * @param name
     * @param vehicleMake
     */
    public ModelAttributes(String name, Integer year, String vehicleMake) {
        super();
        this.name = name;
        this.year = year;
        this.vehicleMake = vehicleMake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

}