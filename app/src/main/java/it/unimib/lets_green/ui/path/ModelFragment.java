package it.unimib.lets_green.ui.path;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.unimib.lets_green.R;
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
    private RecyclerView recyclerViewModels;
    List<VehicleModels> vehicleModlesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_model, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.pathFragment);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);
        editTextSearchModels = view.findViewById(R.id.searchModel);
        recyclerViewModels = view.findViewById(R.id.recyclerViewModel);
        textViewResult = view.findViewById(R.id.nametxt);
        vehicleModlesList = new ArrayList<>();
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

                if (response.code() != 200){
                    return;
                }

                List<VehicleModels> models = response.body();
                for (VehicleModels vehicleModels : models) {

                    vehicleModlesList.add(vehicleModels);
                }

                PutDataIntoRecyclerView(vehicleModlesList);

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

        ModelFragmentDirections.ActionModelFragment2ToCarbonFragment2 action = ModelFragmentDirections.actionModelFragment2ToCarbonFragment2();
        action.setIdModel(item.getData().getId());
        Navigation.findNavController(getView()).navigate(action);
    }

}