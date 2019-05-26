package com.josephmesteban.banji.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.josephmesteban.banji.utils.RecipeModelClass;
import com.josephmesteban.banji.utils.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class RecipesDataSourceFactory extends DataSource.Factory<Integer, RecipeModelClass> {

    private MutableLiveData<RecipesDataSourceClass> liveData;
    private Repository repository;
    private CompositeDisposable compositeDisposable;

    public RecipesDataSourceFactory(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<RecipesDataSourceClass> getMutableLiveData() {
        return liveData;
    }

    @Override
    public DataSource<Integer, RecipeModelClass> create() {
        RecipesDataSourceClass dataSourceClass = new RecipesDataSourceClass(repository, compositeDisposable);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}
