package com.putri.fauziah.usamapapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import com.putri.fauziah.usamapapp.NumberUtil;
import com.putri.fauziah.usamapapp.R;
import com.putri.fauziah.usamapapp.WelcomeActivity;
import com.putri.fauziah.usamapapp.adapter.StateAdapter;
import com.putri.fauziah.usamapapp.model.DataNationResponse;
import com.putri.fauziah.usamapapp.model.DataResponse;
import com.putri.fauziah.usamapapp.model.Images;
import com.putri.fauziah.usamapapp.model.ImagesResponse;
import com.putri.fauziah.usamapapp.model.Nation;
import com.putri.fauziah.usamapapp.model.State;
import com.putri.fauziah.usamapapp.network.ApiClient;
import com.putri.fauziah.usamapapp.network.ApiInterface;
import com.putri.fauziah.usamapapp.network.ImagesService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ApiInterface apiInterface;
    private Button btnLogout;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView countPopulation, labelPopulation;
    private List<State> stateList;
    private List<Nation> nationList;
    private StateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        initializeComponent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestState();
    }
    private void initializeComponent() {
        ImageView imgLogo = findViewById(R.id.imgLogo);
        TextView textLogo = findViewById(R.id.textLogo);
        btnLogout = findViewById(R.id.buttonLogout);
        btnLogout.setOnClickListener(this);
        labelPopulation = findViewById(R.id.labelPopulation);
        countPopulation = findViewById(R.id.textCountPop);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });
    }

    private void requestNation() {
        Call<DataNationResponse> call = apiInterface.getNationList(
                "Nation", "Population", stateList.get(0).getYear()
        );

        call.enqueue(new Callback<DataNationResponse>() {
            @Override
            public void onResponse(Call<DataNationResponse> call, Response<DataNationResponse> response) {
                if (response.isSuccessful()) {
                    DataNationResponse res = response.body();

                    if (res != null) {
                        nationList = res.getData();
                        if (nationList != null) {
                            Nation nation = nationList.get(0);
                            String nationName = nation.getNation();
                            String year = nation.getYear();
                            int countPop = nation.getPopulation();

                            labelPopulation.setText("Population " + nationName + " " + year);
                            countPopulation.setText(NumberUtil.toCurrDigitGrouping(String.valueOf(countPop)));
                        }
                    }
                } else {
                    Log.e("TAG", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DataNationResponse> call, Throwable t) {
                Log.e("TAG", "API call failed: " + t.getMessage());
            }
        });
    }

    private void requestState() {
        Call<DataResponse> call = apiInterface.getStateList(
                "State", "Population", "latest"
        );

        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                if (response.isSuccessful()) {
                    DataResponse res = response.body();

                    if (res != null) {
                        stateList = res.getData();

                        if (stateList != null) {
                            adapter = new StateAdapter(stateList);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));

                            requestNation();
                            for (State state : stateList) {
                                fetchUnsplashImages(state.getState());
                            }
                        } else {
                            Log.e("TAG", "stateList is null");
                        }
                    }
                } else {
                    Log.e("TAG", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                Log.e("TAG", "API call failed: " + t.getMessage());
            }
        });
    }

    private void fetchUnsplashImages(String query) {
        Retrofit ret = ApiClient.getImage();
        ImagesService service = ret.create(ImagesService.class);

        Call<ImagesResponse> call = service.searchPhotos(
                "8NH2T2UM3-Bpj6aad-JdVBIf9-VDuy8hpfItn1pajeM",
                query,
                1
        );

        call.enqueue(new Callback<ImagesResponse>() {
            @Override
            public void onResponse(Call<ImagesResponse> call, Response<ImagesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ImagesResponse unsplashResponse = response.body();
                    if (unsplashResponse.getResults() != null && !unsplashResponse.getResults().isEmpty()) {
                        Images firstPhoto = unsplashResponse.getResults().get(0);
                        String imageUrl = firstPhoto.getUrls().getRegular();

                        for (State state : stateList) {
                            if (state.getState().equalsIgnoreCase(query)) {
                                state.setImageUrl(imageUrl);
                                break;
                            }
                        }

                        adapter.notifyDataSetChanged();  // Notify adapter about the data change
                    }
                }
            }

            @Override
            public void onFailure(Call<ImagesResponse> call, Throwable t) {
                Log.e("Unsplash", "Error: " + t.getMessage());
            }
        });
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear previous activities
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null) // Simply dismiss the dialog
                .show();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonLogout) {
            showLogoutDialog();
        }
    }
}
