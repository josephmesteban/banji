package com.josephmesteban.banji.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.josephmesteban.banji.databinding.RowLayoutBinding;
import com.josephmesteban.banji.utils.RecipeModelClass;

public class MyRecipePageListAdapter extends PagedListAdapter<RecipeModelClass, MyRecipePageListAdapter.MyViewHolder> {

    public MyRecipePageListAdapter() {
        super(RecipeModelClass.DIFF_CALLBACK);
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowLayoutBinding binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),  parent, false);
        return new MyViewHolder(binding);
        }

@Override
public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.binding.setModel(getItem(position));
        }

@Override
public long getItemId(int position) {
        return super.getItemId(position);
        }

class MyViewHolder extends RecyclerView.ViewHolder {

    RowLayoutBinding binding;

    MyViewHolder(RowLayoutBinding itemView) {
        super(itemView.getRoot());
        binding = itemView;
    }

}
}
