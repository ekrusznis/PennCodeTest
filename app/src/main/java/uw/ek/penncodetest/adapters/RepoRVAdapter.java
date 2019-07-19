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
import uw.ek.penncodetest.models.repos.RepoResponse;

public class RepoRVAdapter extends RecyclerView.Adapter<RepoRVAdapter.RepoViewHolder> {

    private List<RepoResponse> dataList;

    public RepoRVAdapter(List<RepoResponse> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_repo, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RepoViewHolder holder, int position) {
        holder.repoName.setText(dataList.get(position).getName());
        holder.repoDesc.setText(dataList.get(position).getDescription());
        holder.openIssuesNum.setText(String.valueOf(dataList.get(position).getOpenIssuesCount()));
        Picasso.get()
                .load(dataList.get(position).getOwner().getAvatarUrl())
                .fit().centerCrop()
                .into(holder.repoImage);
        holder.repoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), IssuesActivity.class);
                i.putExtra("name", holder.repoName.getText().toString());
                i.putExtra("desc", holder.repoDesc.getText().toString());
                v.getContext().startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView repoName, repoDesc, openIssuesNum;
        ImageView repoImage;
        CardView repoCard;

        RepoViewHolder(View itemView) {
            super(itemView);
            openIssuesNum = itemView.findViewById(R.id.openIssuesNum);
            repoDesc = itemView.findViewById(R.id.repoDesc);
            repoName = itemView.findViewById(R.id.repoName);
            repoImage = itemView.findViewById(R.id.repoImage);
            repoCard = itemView.findViewById(R.id.repoCard);
        }
    }
}
