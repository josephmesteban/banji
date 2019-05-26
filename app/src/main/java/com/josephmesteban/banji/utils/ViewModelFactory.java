package com.josephmesteban.banji.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.josephmesteban.banji.viewmodels.PagingRecipesViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PagingRecipesViewModel.class)) {
            return (T) new PagingRecipesViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}