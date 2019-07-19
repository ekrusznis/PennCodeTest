package uw.ek.penncodetest.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uw.ek.penncodetest.models.issues.IssueDetails.IssueDetailResp;
import uw.ek.penncodetest.models.issues.IssueResponse;
import uw.ek.penncodetest.models.repos.RepoResponse;

public interface GithubService {

    @GET("users/google/repos")
    Call<List<RepoResponse>> getRepos();
//    void repos(Callback<List<RepoResponse>> cb);

    @GET("repos/google/{repo}/issues")
    Call<List<IssueResponse>> getIssuesFromRepo(@Path("repo") String repo);

    @GET("repos/google/{repo}/issues/{num}")
    Call<IssueDetailResp> getIssueDetail(@Path("repo") String repo, @Path("num") String num);

}
