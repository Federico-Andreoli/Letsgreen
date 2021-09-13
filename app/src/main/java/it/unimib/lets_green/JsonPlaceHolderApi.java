package it.unimib.lets_green;

import it.unimib.lets_green.RequestCarbon.CarbonRequest;
import it.unimib.lets_green.vehicleMakes.VehicleMakes;
import it.unimib.lets_green.vehicleModel.VehicleModels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
//    chiamata per restituire tutti i tipi di auto (fiat, dodge,ecc)
    @Headers({
            "Authorization: Bearer uxw6Z5ZQKR0uRSXBXcUtwQ",
            "Content-Type: application/json"
    })
    @GET("api/v1/vehicle_makes")
    Call<List<VehicleMakes>> getVeicle();

//    chiamata per restituire tutti i modelli per una marca(fiat500, panda ecc)
    @Headers({
            "Authorization: Bearer uxw6Z5ZQKR0uRSXBXcUtwQ",
            "Content-Type: application/json"
    })
    @GET("api/v1/vehicle_makes/{id}/vehicle_models")
    Call<List<VehicleModels>> getModels(@Path("id") String vehicle_id);

    @Headers({
            "Authorization: Bearer uxw6Z5ZQKR0uRSXBXcUtwQ",
            "Content-Type: application/json"
    })
    @POST("api/v1/estimates")
    Call<CarbonRequest> carbon(@Body Post post);
//    Call<CarbonRequest>createCarbon(@Body Post post);
//    Call<CarbonRequest> createCarbon(@Body Post post);
//    Call<CarbonRequest> createCarbon(@FieldMap Map<String, String> fields);

//            @Field(value = "type") String type,
//            @Field(value = "distance_unit") String distanceUnit,
//            @Field(value ="distance_value") Double distanceValue,
//            @Field(value="vehicle_model_id") String idModel
//    );
//    @FormUrlEncoded
//    @POST("api/v1/estimates")
//    Call<CarbonRequest> getCarbon(@Header("Authorization") String authorization,
//                  @Header(("Content-Type")) String contentType,
//                  @Field(("type")) String type,
//                  @Field(("distance_unit")) String distanceUnit,
//                  @Field(("distance_value")) Double distanceValue,
//                  @Field(("vehicle_model_id")) String idModel
//                  );

}
