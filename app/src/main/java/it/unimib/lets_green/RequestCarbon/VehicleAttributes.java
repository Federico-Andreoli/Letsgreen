package it.unimib.lets_green.RequestCarbon;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class VehicleAttributes {
    @SerializedName("distance_value")
    @Expose
    private Double distanceValue;
    @SerializedName("vehicle_make")
    @Expose
    private String vehicleMake;
    @SerializedName("vehicle_model")
    @Expose
    private String vehicleModel;
    @SerializedName("vehicle_year")
    @Expose
    private Integer vehicleYear;
    @SerializedName("vehicle_model_id")
    @Expose
    private String vehicleModelId;
    @SerializedName("distance_unit")
    @Expose
    private String distanceUnit;
    @SerializedName("estimated_at")
    @Expose
    private String estimatedAt;
    @SerializedName("carbon_g")
    @Expose
    private Integer carbonG;
    @SerializedName("carbon_lb")
    @Expose
    private Double carbonLb;
    @SerializedName("carbon_kg")
    @Expose
    private Double carbonKg;
    @SerializedName("carbon_mt")
    @Expose
    private Double carbonMt;

    /**
     * No args constructor for use in serialization
     *
     */
    public VehicleAttributes() {
    }

    /**
     *
     * @param distanceUnit
     * @param carbonLb
     * @param vehicleYear
     * @param vehicleModelId
     * @param distanceValue
     * @param carbonMt
     * @param vehicleModel
     * @param carbonG
     * @param estimatedAt
     * @param carbonKg
     * @param vehicleMake
     */
    public VehicleAttributes(Double distanceValue, String vehicleMake, String vehicleModel, Integer vehicleYear, String vehicleModelId, String distanceUnit, String estimatedAt, Integer carbonG, Double carbonLb, Double carbonKg, Double carbonMt) {
        super();
        this.distanceValue = distanceValue;
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.vehicleModelId = vehicleModelId;
        this.distanceUnit = distanceUnit;
        this.estimatedAt = estimatedAt;
        this.carbonG = carbonG;
        this.carbonLb = carbonLb;
        this.carbonKg = carbonKg;
        this.carbonMt = carbonMt;
    }

    public Double getDistanceValue() {
        return distanceValue;
    }

    public void setDistanceValue(Double distanceValue) {
        this.distanceValue = distanceValue;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public Integer getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(String vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getEstimatedAt() {
        return estimatedAt;
    }

    public void setEstimatedAt(String estimatedAt) {
        this.estimatedAt = estimatedAt;
    }

    public Integer getCarbonG() {
        return (int) 3.67 * carbonG;
    }

    public void setCarbonG(Integer carbonG) {
        this.carbonG = carbonG;
    }

    public Double getCarbonLb() {
        return carbonLb;
    }

    public void setCarbonLb(Double carbonLb) {
        this.carbonLb = carbonLb;
    }

    public Double getCarbonKg() {
        return carbonKg;
    }

    public void setCarbonKg(Double carbonKg) {
        this.carbonKg = carbonKg;
    }

    public Double getCarbonMt() {
        return carbonMt;
    }

    public void setCarbonMt(Double carbonMt) {
        this.carbonMt = carbonMt;
    }

    @Override
    public String toString() {
        return "VehicleAttributes{" +
                "distanceValue=" + distanceValue +
                ", vehicleMake='" + vehicleMake + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleYear=" + vehicleYear +
                ", vehicleModelId='" + vehicleModelId + '\'' +
                ", distanceUnit='" + distanceUnit + '\'' +
                ", estimatedAt='" + estimatedAt + '\'' +
                ", carbonG=" + carbonG +
                ", carbonLb=" + carbonLb +
                ", carbonKg=" + carbonKg +
                ", carbonMt=" + carbonMt +
                '}';
    }
}
