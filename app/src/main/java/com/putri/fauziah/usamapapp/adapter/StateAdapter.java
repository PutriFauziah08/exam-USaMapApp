package com.putri.fauziah.usamapapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.putri.fauziah.usamapapp.NumberUtil;
import com.putri.fauziah.usamapapp.R;
import com.putri.fauziah.usamapapp.model.State;
import com.putri.fauziah.usamapapp.view.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {

    private List<State> stateList;
    private List<State> filteredStateList;

    public StateAdapter(List<State> stateList) {
        this.stateList = stateList;
        this.filteredStateList = new ArrayList<>(stateList);
    }

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, int position) {
        State state = filteredStateList.get(position);
        holder.itemTitle.setText(state.getState());
        holder.itemPupulation.setText(String.format("%s Population", NumberUtil.toCurrDigitGrouping(String.valueOf(state.getPopulation()))));

        Glide.with(holder.itemImage.getContext())
                .load(state.getImageUrl())
                .into(holder.itemImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("stateName", state.getState());
            intent.putExtra("state", state);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredStateList.size();
    }

    public void filter(String query) {
        filteredStateList.clear();

        if (query.isEmpty()) {
            filteredStateList.addAll(stateList);
        } else {
            for (State state : stateList) {
                if (state.getState().toLowerCase().contains(query.toLowerCase())) {
                    filteredStateList.add(state);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class StateViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTitle;
        public TextView itemPupulation;
        public ImageView itemImage;

        public StateViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemPupulation = itemView.findViewById(R.id.itemPupulation);
        }
    }
}

