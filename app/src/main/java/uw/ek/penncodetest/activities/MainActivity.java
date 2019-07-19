package uw.ek.penncodetest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uw.ek.penncodetest.R;
import uw.ek.penncodetest.adapters.RepoRVAdapter;
import uw.ek.penncodetest.api.GithubService;
import uw.ek.penncodetest.api.RetrofitInstance;
import uw.ek.penncodetest.models.repos.RepoResponse;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<RepoResponse> data = new ArrayList<>();
    private RepoRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.repoRV);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON(){
        GithubService service = RetrofitInstance.getRetrofitInstance().create(GithubService.class);
        Call<List<RepoResponse>> call = service.getRepos();
        call.enqueue(new Callback<List<RepoResponse>>() {
            @Override
            public void onResponse(Call<List<RepoResponse>> call, Response<List<RepoResponse>> response) {

                List<RepoResponse> jsonResponse = response.body();
//                data.add(jsonResponse);
                adapter = new RepoRVAdapter(jsonResponse);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RepoResponse>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

    }
}
