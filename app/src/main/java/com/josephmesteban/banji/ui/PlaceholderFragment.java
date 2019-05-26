package com.josephmesteban.banji.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.josephmesteban.banji.MyApplication;
import com.josephmesteban.banji.adapters.MyRecipePageListAdapter;
import com.josephmesteban.banji.databinding.FragmentMainBinding;
import com.josephmesteban.banji.utils.Constant;
import com.josephmesteban.banji.utils.ViewModelFactory;
import com.josephmesteban.banji.viewmodels.PagingRecipesViewModel;

import java.util.Objects;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    @Inject
    ViewModelFactory viewModelFactory;

    PagingRecipesViewModel viewModel;

    FragmentMainBinding binding;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication myApplication = (MyApplication) this.getActivity().getApplication();
        myApplication.getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PagingRecipesViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        viewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        init();

        return binding.getRoot();
    }

    private void init() {

        binding.list.setLayoutManager(new LinearLayoutManager(this.getContext()));
        MyRecipePageListAdapter adapter = new MyRecipePageListAdapter();
        binding.list.setAdapter(adapter);

        viewModel.getListLiveData().observe(this, adapter::submitList);

        viewModel.getProgressLoadStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(Constant.LOADING)) {
                binding.progressBar.setVisibility(View.VISIBLE);
            } else if (status.equalsIgnoreCase(Constant.LOADED)) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}