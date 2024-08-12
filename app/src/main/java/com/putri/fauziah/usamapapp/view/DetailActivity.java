package com.putri.fauziah.usamapapp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.putri.fauziah.usamapapp.NumberUtil;
import com.putri.fauziah.usamapapp.R;
import com.putri.fauziah.usamapapp.model.GeocodeResponse;
import com.putri.fauziah.usamapapp.model.State;
import com.putri.fauziah.usamapapp.network.ApiClient;
import com.putri.fauziah.usamapapp.network.ApiInterface;
import com.putri.fauziah.usamapapp.network.MapsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private MapView mapView;
    private Button btnShowBottomSheet;
    private TextView textMedianAge;
    private final String KEY = "524e4859030d4a078df6e6630e6f318e";
    private LatLng locationToFocus;
    private String stateName;
    private State state;

    private ApiInterface apiInterface;

    private List<JsonObject> medianAgeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        initializeComponent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        request();
        requestMedian();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (locationToFocus != null) {
            updateMap(locationToFocus);
        }
    }

    private void initializeComponent() {

        Bundle data = getIntent().getExtras();
        if (data != null) {
            stateName = data.getString("stateName");
            state = data.getParcelable("state");
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail");
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        TextView textStateName = (TextView) findViewById(R.id.textStateName);
        textStateName.setText(state.getState());

        TextView textYear = (TextView) findViewById(R.id.textYear);
        textYear.setText(state.getYear());

        TextView textCount = (TextView) findViewById(R.id.textCountPopu);
        textCount.setText(NumberUtil.toCurrDigitGrouping(String.valueOf(state.getPopulation())));

        textMedianAge = (TextView) findViewById(R.id.textMedianAge);

        Button btnOpenMaps = (Button) findViewById(R.id.btnOpenMaps);
        btnOpenMaps.setOnClickListener(this);

    }

    private void requestMedian() {
        Call<JsonObject> call = apiInterface.getMedAgeList(
                "State", "Median Age", state.getId().toString() , state.getYear()
        );

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonResponse = response.body();
                    JsonArray data = jsonResponse.getAsJsonArray("data");
                    for (JsonElement element : data) {
                        JsonObject item = element.getAsJsonObject();
                        double age = item.get("Median Age").getAsDouble();

                        textMedianAge.setText(String.valueOf(age));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("TAG", "API call failed: " + t.getMessage());
            }
        });
    }

    private void request() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.opencagedata.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MapsService service = retrofit.create(MapsService.class);

        Call<GeocodeResponse> call = service.getGeocode(stateName, KEY, "en", 1);
        call.enqueue(new Callback<GeocodeResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeocodeResponse> call, @NonNull Response<GeocodeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GeocodeResponse geocodeResponse = response.body();
                    if (geocodeResponse.getResults() != null && geocodeResponse.getResults().length > 0) {
                        GeocodeResponse.Results result = geocodeResponse.getResults()[0];
                        if (result.getGeometry() != null) {
                            double lat = result.getGeometry().getLat();
                            double lng = result.getGeometry().getLng();
                            Log.d("DetailActivity", "Latitude: " + lat + ", Longitude: " + lng);
                            locationToFocus = new LatLng(lat, lng);
                            if (mMap != null) {
                                updateMap(locationToFocus);
                            }
                        } else {
                            Log.e("DetailActivity", "No geometry data available");
                        }
                    } else {
                        Log.e("DetailActivity", "No results available");
                    }
                } else {
                    Log.e("DetailActivity", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<GeocodeResponse> call, @NonNull Throwable t) {
                Log.e("TAG", "API call failed: " + t.getMessage());
            }
        });
    }

    private void updateMap(LatLng location) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(location).title(stateName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12.f));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOpenMaps){
            if (locationToFocus != null) {
                String uriString = "https://www.google.com/maps/search/?api=1&query=" + stateName;
                Uri gmmIntentUri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(mapIntent);
            } else {
                Toast.makeText(DetailActivity.this, "Tidak Bisa Membuka Maps", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
