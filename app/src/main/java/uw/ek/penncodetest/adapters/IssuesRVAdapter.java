package uw.ek.penncodetest.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uw.ek.penncodetest.R;
import uw.ek.penncodetest.activities.IssuesActivity;
import uw.ek.penncodetest.activities.IssuesDetail;
import uw.ek.penncodetest.models.issues.IssueResponse;
import uw.ek.penncodetest.models.repos.RepoResponse;

public class IssuesRVAdapter extends RecyclerView.Adapter<IssuesRVAdapter.IssueViewHolder> {

    private List<IssueResponse> dataList;
    private String repoName;

    public IssuesRVAdapter(List<IssueResponse> dataList, String repoName) {
        this.dataList = dataList;
        this.repoName = repoName;
    }

    @Override
    public IssuesRVAdapter.IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_issue, parent, false);
        return new IssuesRVAdapter.IssueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IssuesRVAdapter.IssueViewHolder holder, int position) {
        holder.issueName.setText(dataList.get(position).getTitle());
        holder.issueUser.setText(dataList.get(position).getUser().getLogin());
        holder.state.setText(dataList.get(position).getState());
        holder.issueNum.setText(String.valueOf(dataList.get(position).getNumber()));
        Picasso.get()
                .load(dataList.get(position).getUser().getAvatarUrl())
                .fit().centerCrop()
                .into(holder.issueImage);
        holder.issueCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), IssuesDetail.class);
                i.putExtra("num", holder.issueNum.getText().toString());
                i.putExtra("name", repoName);
                v.getContext().startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    class IssueViewHolder extends RecyclerView.ViewHolder {
        TextView issueName, issueUser, state, issueNum;
        ImageView issueImage;
        CardView issueCard;

        IssueViewHolder(View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.state);
            issueUser = itemView.findViewById(R.id.issueUser);
            issueName = itemView.findViewById(R.id.issueName);
            issueImage = itemView.findViewById(R.id.issueImage);
            issueCard = itemView.findViewById(R.id.issueCard);
            issueNum = itemView.findViewById(R.id.issueNum);
        }
    }
}
