package it.unimib.lets_green;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    @Expose
    @SerializedName("type")
    private String vehicle;

    @Expose
    @SerializedName("distance_unit")
    private String distance_unit;

    @Expose
    @SerializedName("distance_value")
    private String distance_value;

    @Expose
    @SerializedName("vehicle_model_id")
    private String vehicle_model_id;

    public Post(String vehicle, String distance_unit, String distance_value, String vehicle_model_id) {
        this.vehicle = vehicle;
        this.distance_unit = distance_unit;
        this.distance_value = distance_value;
        this.vehicle_model_id = vehicle_model_id;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getDistance_unit() {
        return distance_unit;
    }

    public void setDistance_unit(String distance_unit) {
        this.distance_unit = distance_unit;
    }

    public String getDistance_value() {
        return distance_value;
    }

    public void setDistance_value(String distance_value) {
        this.distance_value = distance_value;
    }

    public String getVehicle_model_id() {
        return vehicle_model_id;
    }

    public void setVehicle_model_id(String vehicle_model_id) {
        this.vehicle_model_id = vehicle_model_id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "vehicle='" + vehicle + '\'' +
                ", distance_unit='" + distance_unit + '\'' +
                ", distance_value=" + distance_value +
                ", vehicle_model_id='" + vehicle_model_id + '\'' +
                '}';
    }
}
