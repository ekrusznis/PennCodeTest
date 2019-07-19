package uw.ek.penncodetest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uw.ek.penncodetest.R;
import uw.ek.penncodetest.adapters.IssuesRVAdapter;
import uw.ek.penncodetest.api.GithubService;
import uw.ek.penncodetest.api.RetrofitInstance;
import uw.ek.penncodetest.models.issues.IssueDetails.IssueDetailResp;
import uw.ek.penncodetest.models.issues.IssueResponse;

public class IssuesDetail extends AppCompatActivity {
    private String TAG = "IssuesDetail";
    private TextView textBody, textTitle, textClosedByName, textClosedBy;
    private ImageView closedByImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_detail);

        textBody = findViewById(R.id.textBody);
        textTitle = findViewById(R.id.textTitle);
        textClosedByName = findViewById(R.id.textClosedByName);
        closedByImage = findViewById(R.id.closedByImage);
        textClosedBy = findViewById(R.id.textClosedBy);

        Intent intent = getIntent();
        if (intent != null){
            String num = intent.getStringExtra("num");
            String name = intent.getStringExtra("name");
            getJson(num, name);
        }
    }

    private void getJson(String num, String name){
        Log.i(TAG, "loadJSON, " + num + " " + name);
        GithubService service = RetrofitInstance.getRetrofitInstance().create(GithubService.class);
        Call<IssueDetailResp> call = service.getIssueDetail(name, num);
        call.enqueue(new Callback<IssueDetailResp>() {
            @Override
            public void onResponse(Call<IssueDetailResp> call, Response<IssueDetailResp> response) {
                if (response.isSuccessful()){
                    textBody.setText(response.body().getBody());
                    textTitle.setText(response.body().getTitle());
                    if (response.body().getClosedBy()==null){
                        textClosedByName.setText("OPEN");
                        textClosedBy.setVisibility(View.GONE);

                    }else {textClosedByName.setText(response.body().getClosedBy().getLogin());
                        Picasso.get().load(response.body().getClosedBy().getAvatarUrl()).centerCrop().into(closedByImage);
                    }
                }

            }

            @Override
            public void onFailure(Call<IssueDetailResp> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

    }
}
