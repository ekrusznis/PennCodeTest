package uw.ek.penncodetest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uw.ek.penncodetest.R;
import uw.ek.penncodetest.adapters.IssuesRVAdapter;
import uw.ek.penncodetest.adapters.RepoRVAdapter;
import uw.ek.penncodetest.api.GithubService;
import uw.ek.penncodetest.api.RetrofitInstance;
import uw.ek.penncodetest.models.issues.IssueResponse;
import uw.ek.penncodetest.models.repos.RepoResponse;

public class IssuesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    IssuesRVAdapter adapter;
    TextView repoIntentName, repoIntentDesc;
    private String TAG = "IssuesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        initViews();

    }
    private void initViews() {
        Log.i(TAG, "initViews");
        repoIntentName = findViewById(R.id.repoIntentName);
        repoIntentDesc = findViewById(R.id.repoIntentDesc);
        recyclerView = (RecyclerView) findViewById(R.id.issueRV);

        Intent intent = getIntent();
        if (intent != null){
            String desc = intent.getStringExtra("desc");
            String name = intent.getStringExtra("name");
            repoIntentDesc.setText(desc);
            repoIntentName.setText(name);
        }
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON(repoIntentName.getText().toString());
    }
    private void loadJSON(final String name){
        Log.i(TAG, "loadJSON, " + name);
        GithubService service = RetrofitInstance.getRetrofitInstance().create(GithubService.class);
        Call<List<IssueResponse>> call = service.getIssuesFromRepo(name);
        call.enqueue(new Callback<List<IssueResponse>>() {
            @Override
            public void onResponse(Call<List<IssueResponse>> call, Response<List<IssueResponse>> response) {

                List<IssueResponse> jsonResponse = response.body();
//                data.add(jsonResponse);
                adapter = new IssuesRVAdapter(jsonResponse, name);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<IssueResponse>> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });


    }
}
