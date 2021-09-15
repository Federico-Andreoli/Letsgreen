package it.unimib.lets_green;

import static it.unimib.lets_green.ui.Login.Login.getIs_logged;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    private TextView grams;
    private EditText editTextName;
    private Button buttonAddPath;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        View view =  inflater.inflate(R.layout.fragment_carbon, container, false);
        String model = CarbonFragmentArgs.fromBundle(getArguments()).getIdModel();
        editTextKm = view.findViewById(R.id.km);
        buttonCalculateCO2 = view.findViewById(R.id.buttonCo2);
        labelCO2 = view.findViewById(R.id.labelCarbon);
        textViewCO2 = view.findViewById(R.id.co2);
        editTextName = view.findViewById(R.id.nomePercorso);
        buttonAddPath = view.findViewById(R.id.addPath);
        grams = view.findViewById(R.id.grams);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.pathFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

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

        InputFilter limitFilter = new MinMaxInputFilter(0, 999999);
        editTextKm.setFilters(new InputFilter[] { limitFilter });

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        editTextKm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String kmString = editTextKm.getText().toString().trim();
                buttonCalculateCO2.setEnabled(!kmString.isEmpty() && checkIsInRange(kmString));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonCalculateCO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCarbon(editTextKm.getText().toString(), model);

            }
        });

        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nameString = editTextName.getText().toString().trim();
                buttonAddPath.setEnabled(!nameString.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonAddPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getIs_logged()) {
                    DialogFragment dialogFragment = new DialogFragment();
                    dialogFragment.show(getActivity().getSupportFragmentManager(), "example");
                } else {
                    VehiclePath pathElement = new VehiclePath(editTextName.getText().toString(), textViewCO2.getText().toString());
                    CarbonFragmentDirections.ActionCarbonFragment2ToPathFragment action = CarbonFragmentDirections.actionCarbonFragment2ToPathFragment();
                    action.setPathObject(pathElement);
                    Navigation.findNavController(getView()).navigate(action);

                }
            }
        });

        return view;
    }
    private void createCarbon(String km, String id) {
        Post post1 = new Post("vehicle","km", km, id);
        Call<CarbonRequest> call = jsonPlaceHolderApi.carbon(post1);

        call.enqueue(new Callback<CarbonRequest>() {
            @Override
            public void onResponse(Call<CarbonRequest> call, Response<CarbonRequest> response) {

                if (!response.isSuccessful()) {
                    textViewCO2.setText("Code: "+ response.code());
                    return;
                }

                CarbonRequest carbonRequest = response.body();

                textViewCO2.setText(carbonRequest.getData().getAttributes().getCarbonG().toString());
                textViewCO2.setVisibility(View.VISIBLE);
                editTextName.setVisibility(View.VISIBLE);
                labelCO2.setVisibility(View.VISIBLE);
                grams.setVisibility(View.VISIBLE);
                buttonAddPath.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<CarbonRequest> call, Throwable t) {
                textViewCO2.setText(t.getMessage());
            }
        });
    }

    public boolean checkIsInRange(String s) {
        if(Double.parseDouble(s) > 999999) {
            return false;
        }
        else
            return true;
    }

    private class MinMaxInputFilter implements InputFilter {
        private double mMinValue;
        private double mMaxValue;

        private static final double MIN_VALUE_DEFAULT = Double.MIN_VALUE;
        private static final double MAX_VALUE_DEFAULT = Double.MAX_VALUE;

        public MinMaxInputFilter(Double min, Double max) {
            this.mMinValue = (min != null ? min : MIN_VALUE_DEFAULT);
            this.mMaxValue = (max != null ? max : MAX_VALUE_DEFAULT);
        }

        public MinMaxInputFilter(Integer min, Integer max) {
            this.mMinValue = (min != null ? min : MIN_VALUE_DEFAULT);
            this.mMaxValue = (max != null ? max : MAX_VALUE_DEFAULT);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                String replacement = source.subSequence(start, end).toString();
                String newVal = dest.subSequence(0, dstart).toString() + replacement
                        + dest.subSequence(dend, dest.length()).toString();

                // check if there are leading zeros
                if (newVal.matches("0\\d+.*"))
                    if (TextUtils.isEmpty(source))
                        return dest.subSequence(dstart, dend);
                    else
                        return "";

                // check range
                double input = Double.parseDouble(newVal);
                if (!isInRange(mMinValue, mMaxValue, input))
                    if (TextUtils.isEmpty(source))
                        return dest.subSequence(dstart, dend);
                    else
                        return "";

                return null;
            } catch (NumberFormatException nfe) {
                //LOG("inputfilter", "parse");
            }
            return "";
        }

        private boolean isInRange(double a, double b, double c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

}