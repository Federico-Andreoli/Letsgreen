package it.unimib.lets_green;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import it.unimib.lets_green.RequestCarbon.CarbonRequest;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CarbonFragment extends Fragment {
    private EditText editTextKm;
    private Button buttonCalculateCO2;
    private TextView labelCO2;
    private TextView textViewCO2;
    private EditText editTextName;
    private Button buttonAddPath;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_carbon, container, false);
        String model = CarbonFragmentArgs.fromBundle(getArguments()).getIdModel();
        editTextKm = view.findViewById(R.id.km);
        buttonCalculateCO2 = view.findViewById(R.id.buttonCo2);
        labelCO2 = view.findViewById(R.id.labelCarbon);
        textViewCO2 = view.findViewById(R.id.co2);
        editTextName = view.findViewById(R.id.nomePercorso);
        buttonAddPath = view.findViewById(R.id.addPath);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        traccia le chiamate all'api
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

//        HttpLoggingInterceptor httpClient = OkHttpClient.Builder()


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.carboninterface.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();



        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        editTextKm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String kmString = editTextKm.getText().toString().trim();
                buttonCalculateCO2.setEnabled(!kmString.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonCalculateCO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewCO2.setVisibility(view.VISIBLE);
                editTextName.setVisibility(view.VISIBLE);
                labelCO2.setVisibility(view.VISIBLE);
                buttonAddPath.setVisibility(view.VISIBLE);
                createCarbon(editTextKm.getText().toString(), model);

            }
        });

        return view;
    }
    private void createCarbon(String km, String id) {
//        Map<String, String> fields = new HashMap<>();
//        fields.put("type", "vehicle");
//        fields.put("distance_unit", "mi");
//        fields.put("distance_value", "100");
//        fields.put("vehicle_model_id", "7268a9b7-17e8-4c8d-acca-57059252afe9");
//        final String auth = "Bearer " + getBase64String("XBrDDBBf0Z7TGn4AsFLquA");
//        jsonPlaceHolderApi.getCarbon(auth, "application/json", "vehicle", "mi", 100, "7268a9b7-17e8-4c8d-acca-57059252afe9");
        Post post1 = new Post("vehicle","km", km, id);
//        Call<CarbonRequest> call = jsonPlaceHolderApi.getCarbon("Bearer XBrDDBBf0Z7TGn4AsFLquA", "application/json", "vehicle", "mi", 100.0, "7268a9b7-17e8-4c8d-acca-57059252afe9");
        Call<CarbonRequest> call = jsonPlaceHolderApi.carbon(post1);

        call.enqueue(new Callback<CarbonRequest>() {
            @Override
            public void onResponse(Call<CarbonRequest> call, Response<CarbonRequest> response) {

                if (!response.isSuccessful()) {
                    textViewCO2.setText("Code: "+ response.code());
                    return;
                }
//                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                CarbonRequest carbonRequest = response.body();


//                String content ="";
//                content += "code: " + response.code() + "\n";
//                content += "ID: " + carbonRequest.getData().getAttributes().getVehicleModelId() + "\n";
//                content += "unit: " + carbonRequest.getData().getAttributes().getDistanceUnit()+ "\n";
//                content += "distance value: " + carbonRequest.getData().getAttributes().getDistanceValue()+ "\n";
//                content += "vehicle make: " + carbonRequest.getData().getAttributes().getVehicleMake()+ "\n";
//                content += "vehicle model: " + carbonRequest.getData().getAttributes().getVehicleModel()+ "\n";
//                content += "carbon(g): " + carbonRequest.getData().getAttributes().getCarbonG()+ "\n\n";

                textViewCO2.setText(carbonRequest.getData().getAttributes().getCarbonG().toString());
            }

            @Override
            public void onFailure(Call<CarbonRequest> call, Throwable t) {
                textViewCO2.setText(t.getMessage());
            }
        });
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        TextView carbon = view.findViewById(R.id.idmodel);
//
//        String model = CarbonFragmentArgs.fromBundle(getArguments()).getIdModel();
//
//        carbon.setText(model);
//
//
//    }
}