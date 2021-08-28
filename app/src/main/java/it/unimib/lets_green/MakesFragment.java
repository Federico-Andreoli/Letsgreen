package it.unimib.lets_green;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import it.unimib.lets_green.RequestCarbon.CarbonRequest;
import it.unimib.lets_green.adapter.VehicleMakesAdapter;
import it.unimib.lets_green.vehicleMakes.VehicleMakes;
import it.unimib.lets_green.vehicleModel.VehicleModels;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MakesFragment extends Fragment {
    private EditText editTextSearchMakes;
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    //    private AutoCompleteTextView autoCompleteTextView;
//    public List<VehicleMakes> Marks = getVeicle();
    private RecyclerView recyclerView;
    List<VehicleMakes> vehicleMakesList;


    public MakesFragment() {
        // Required empty public constructor
    }




//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_makes, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        textViewResult = view.findViewById(R.id.nametxt);
//        autoCompleteTextView = findViewById(R.id.actv);
        vehicleMakesList = new ArrayList<>();
        editTextSearchMakes = view.findViewById(R.id.searchMakes);



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
        getVeicle();
        editTextSearchMakes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }

    private void getVeicle() {
        Call<List<VehicleMakes>> call = jsonPlaceHolderApi.getVeicle();
//      List<String> vehicles = new ArrayList<String>();
        call.enqueue(new Callback<List<VehicleMakes>>() {
            @Override
            public void onResponse(Call<List<VehicleMakes>> call, Response<List<VehicleMakes>> response) {
//                if (response.isSuccessful()) {
//                    List<VehicleMakes> vehicleMakes = response.body();
//                    makesAdapter.setData(vehicleMakes);
//
//                    recyclerView.setAdapter(makesAdapter);
////                    textViewResult.setText("Code: "+ response.code());
//
//                }
                if (response.code() != 200){
                    return;
                }

                List<VehicleMakes> makes = response.body();


//                vehicle = response.body();
                // data dal JSON
//                returnedList.addAll(response.body());
//                List<VehicleMakes> vehicle = response.body();
////                List<String> makes = null;
                for (VehicleMakes vehicleMakes : makes) {
//                    String content = "";

//                        content += vehicleMakes.getData().getMakesAttributes().getName();
//                    String name = vehicleMakes.getData().getMakesAttributes().getName();
                    vehicleMakesList.add(vehicleMakes);
                }

                PutDataIntoRecyclerView(vehicleMakesList);

//                    vehicles.add(vehicleMakes.getData().getMakesAttributes().getName());
//                    makes.add(vehicleMakes.getData().getMakesAttributes().getName());

//                    String content ="";
//                    content += "ID: " + vehicleMakes.getData().getId() + "\n";
//                    content += vehicleMakes.getData().getMakesAttributes().getName() + "\n";
//                    content += "number of vehicleMakes: " + vehicleMakes.getData().getMakesAttributes().getNumberOfModels() + "\n\n";

//                    autoCompleteTextView.append(content);
//                }
//                return makes;
            }

            @Override
            public void onFailure(Call<List<VehicleMakes>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void PutDataIntoRecyclerView(List<VehicleMakes> listMakes) {
        VehicleMakesAdapter vehicleMakesAdapter = new VehicleMakesAdapter(listMakes, getContext(), new VehicleMakesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VehicleMakes item) {
                moveToModelFragment(item);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(vehicleMakesAdapter);
//        VehicleMakesAdapter vehicleMakesAdapter = new VehicleMakesAdapter(getActivity(), vehicleMakesList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(vehicleMakesAdapter);
//
//        vehicleMakesAdapter.setOnItemClickListener(new VehicleMakesAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                vehicleMakesList.get(position).displayMessage(getActivity());
////                AppCompatActivity activity =(AppCompatActivity)getView().getContext();
////                ModelFragment modelFragment = new ModelFragment();
////                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcarbon,modelFragment).addToBackStack(null).commit();
//
//            }
//        });
    }

    private void getModels(){
        Call<List<VehicleModels>> call = jsonPlaceHolderApi.getModels("5f266411-5bb1-4b91-b044-9707426df630");

        call.enqueue(new Callback<List<VehicleModels>>() {
            @Override
            public void onResponse(Call<List<VehicleModels>> call, Response<List<VehicleModels>> response) {
                if (!response.isSuccessful()) {

                    textViewResult.setText("Code: "+ response.code());
                    return;
                }
                List<VehicleModels> models = response.body();

                for(VehicleModels vehicleModels : models){
                    String content ="";
                    content += "ID: " + vehicleModels.getData().getId() + "\n";
                    content += "name: " + vehicleModels.getData().getAttributes().getName()+ "\n";
                    content += "year: " + vehicleModels.getData().getAttributes().getYear() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<VehicleModels>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createCarbon() {
//        Map<String, String> fields = new HashMap<>();
//        fields.put("type", "vehicle");
//        fields.put("distance_unit", "mi");
//        fields.put("distance_value", "100");
//        fields.put("vehicle_model_id", "7268a9b7-17e8-4c8d-acca-57059252afe9");
//        final String auth = "Bearer " + getBase64String("XBrDDBBf0Z7TGn4AsFLquA");
//        jsonPlaceHolderApi.getCarbon(auth, "application/json", "vehicle", "mi", 100, "7268a9b7-17e8-4c8d-acca-57059252afe9");
        Post post1 = new Post("vehicle","km", "100", "7268a9b7-17e8-4c8d-acca-57059252afe9");
//        Call<CarbonRequest> call = jsonPlaceHolderApi.getCarbon("Bearer XBrDDBBf0Z7TGn4AsFLquA", "application/json", "vehicle", "mi", 100.0, "7268a9b7-17e8-4c8d-acca-57059252afe9");
        Call<CarbonRequest> call = jsonPlaceHolderApi.carbon(post1);

        call.enqueue(new Callback<CarbonRequest>() {
            @Override
            public void onResponse(Call<CarbonRequest> call, Response<CarbonRequest> response) {

                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: "+ response.code());
                    return;
                }
//                Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                CarbonRequest carbonRequest = response.body();


                String content ="";
                content += "code: " + response.code() + "\n";
                content += "ID: " + carbonRequest.getData().getAttributes().getVehicleModelId() + "\n";
                content += "unit: " + carbonRequest.getData().getAttributes().getDistanceUnit()+ "\n";
                content += "distance value: " + carbonRequest.getData().getAttributes().getDistanceValue()+ "\n";
                content += "vehicle make: " + carbonRequest.getData().getAttributes().getVehicleMake()+ "\n";
                content += "vehicle model: " + carbonRequest.getData().getAttributes().getVehicleModel()+ "\n";
                content += "carbon(g): " + carbonRequest.getData().getAttributes().getCarbonG()+ "\n\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<CarbonRequest> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    public static String getBase64String(String value) throws UnsupportedEncodingException {
        return Base64.encodeToString(value.getBytes("UTF-8"), Base64.NO_WRAP);
    }

    private void filter(String text){
        ArrayList<VehicleMakes> filteredList =  new ArrayList<>();

        for(VehicleMakes item : vehicleMakesList){
            if(item.getData().getMakesAttributes().getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        PutDataIntoRecyclerView(filteredList);



    }
    public void moveToModelFragment(VehicleMakes item){
        MakesFragmentDirections.ActionCarbonFragmentToModelFragment2 action = MakesFragmentDirections.actionCarbonFragmentToModelFragment2();
        action.setIdMakes(item.getData().getId());
        Navigation.findNavController(getView()).navigate(action);

    }
}