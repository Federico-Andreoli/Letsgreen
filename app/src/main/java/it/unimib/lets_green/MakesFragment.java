package it.unimib.lets_green;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.util.Base64;
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
    private RecyclerView recyclerView;
    List<VehicleMakes> vehicleMakesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_makes, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.pathFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        recyclerView = view.findViewById(R.id.recyclerView);
        textViewResult = view.findViewById(R.id.nametxt);
        vehicleMakesList = new ArrayList<>();
        editTextSearchMakes = view.findViewById(R.id.searchMakes);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//        traccia le chiamate all'api
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.carboninterface.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getVeicle();
//        metodo che permette di cercare gli elementi nella RecyclerView tramite una EditTextView
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
        call.enqueue(new Callback<List<VehicleMakes>>() {
            @Override
            public void onResponse(Call<List<VehicleMakes>> call, Response<List<VehicleMakes>> response) {

                if (response.code() != 200){
                    return;
                }

                List<VehicleMakes> makes = response.body();

                for (VehicleMakes vehicleMakes : makes) {
                    vehicleMakesList.add(vehicleMakes);
                }

                PutDataIntoRecyclerView(vehicleMakesList);

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