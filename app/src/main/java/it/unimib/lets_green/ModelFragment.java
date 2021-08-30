package it.unimib.lets_green;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.lets_green.adapter.VehicleMakesAdapter;
import it.unimib.lets_green.adapter.VehicleModelsAdapter;
import it.unimib.lets_green.vehicleMakes.VehicleMakes;
import it.unimib.lets_green.vehicleModel.VehicleModels;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ModelFragment extends Fragment {

    private EditText editTextSearchModels;
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    //    private AutoCompleteTextView autoCompleteTextView;
//    public List<VehicleMakes> Marks = getVeicle();
    private RecyclerView recyclerViewModels;
    List<VehicleModels> vehicleModlesList;
    public ModelFragment() {
        // Required empty public constructor
    }



//    public static ModelFragment newInstance(String param1, String param2) {
//        ModelFragment fragment = new ModelFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
////        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_model, container, false);
        editTextSearchModels = view.findViewById(R.id.searchModel);
        recyclerViewModels = view.findViewById(R.id.recyclerViewModel);
        textViewResult = view.findViewById(R.id.nametxt);
        vehicleModlesList = new ArrayList<>();
//        String makes = CarbonFragmentArgs.fromBundle(getArguments()).getIdModel();
        String makesId = ModelFragmentArgs.fromBundle(getArguments()).getIdMakes();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.carboninterface.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getModels(makesId);
        editTextSearchModels.addTextChangedListener(new TextWatcher() {
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

    private void getModels(String makesId){
        Call<List<VehicleModels>> call = jsonPlaceHolderApi.getModels(makesId);

        call.enqueue(new Callback<List<VehicleModels>>() {
            @Override
            public void onResponse(Call<List<VehicleModels>> call, Response<List<VehicleModels>> response) {
//                if (!response.isSuccessful()) {
//
//                    textViewResult.setText("Code: "+ response.code());
//                    return;
//                }
                if (response.code() != 200){
                    return;
                }

                List<VehicleModels> models = response.body();
                for (VehicleModels vehicleModels : models) {
//                    String content = "";

//                        content += vehicleMakes.getData().getMakesAttributes().getName();
//                    String name = vehicleMakes.getData().getMakesAttributes().getName();
                    vehicleModlesList.add(vehicleModels);
                }

                PutDataIntoRecyclerView(vehicleModlesList);
//                for(VehicleModels vehicleModels : models){
//                    String content ="";
//                    content += "ID: " + vehicleModels.getData().getId() + "\n";
//                    content += "name: " + vehicleModels.getData().getAttributes().getName()+ "\n";
//                    content += "year: " + vehicleModels.getData().getAttributes().getYear() + "\n\n";
//
//                    textViewResult.append(content);
//                }
            }

            @Override
            public void onFailure(Call<List<VehicleModels>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
    private void PutDataIntoRecyclerView(List<VehicleModels> listModels) {
        VehicleModelsAdapter vehicleModelsAdapter = new VehicleModelsAdapter(listModels, getContext(), new VehicleModelsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VehicleModels item) {
                moveToCarbonFragment(item);
            }
        });
        recyclerViewModels.setHasFixedSize(true);
        recyclerViewModels.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewModels.setAdapter(vehicleModelsAdapter);
    }

    private void filter(String text){
        ArrayList<VehicleModels> filteredList =  new ArrayList<>();

        for(VehicleModels item : vehicleModlesList){
            if((item.getData().getAttributes().getName() +" "+ item.getData().getAttributes().getYear().toString()).toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        PutDataIntoRecyclerView(filteredList);



    }

    public void moveToCarbonFragment(VehicleModels item){

//        Fragment fragment = new CarbonFragment();
//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.carbonFragment2, fragment);
//        fragmentTransaction.replace(R.id.modelFragment2, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        CarbonFragment action =
//                SpecifyAmountFragmentDirections.confirmationAction();
//        action.setAmount(amount);
//        Navigation.findNavController(view).navigate(R.id.action_modelFragment2_to_carbonFragment2);

        ModelFragmentDirections.ActionModelFragment2ToCarbonFragment2 action = ModelFragmentDirections.actionModelFragment2ToCarbonFragment2();
        action.setIdModel(item.getData().getId());
        Navigation.findNavController(getView()).navigate(action);

//        Intent intent = new Intent(this, CarbonFragment.class);
    }

}