package com.josephmesteban.banji.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.josephmesteban.banji.datasource.RecipesDataSourceClass;
import com.josephmesteban.banji.datasource.RecipesDataSourceFactory;
import com.josephmesteban.banji.utils.RecipeModelClass;
import com.josephmesteban.banji.utils.Repository;

import io.reactivex.disposables.CompositeDisposable;

public class PagingRecipesViewModel extends PageViewModel {

    private RecipesDataSourceFactory recipesDataSourceFactory;
    private LiveData<PagedList<RecipeModelClass>> listLiveData;

    private LiveData<String> progressLoadStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public PagingRecipesViewModel(Repository repository) {
        recipesDataSourceFactory = new RecipesDataSourceFactory(repository, compositeDisposable);
        initializePaging();
    }


    private void initializePaging() {

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();

        listLiveData = new LivePagedListBuilder<>(recipesDataSourceFactory, pagedListConfig)
                .build();

        progressLoadStatus = Transformations.switchMap(recipesDataSourceFactory.getMutableLiveData(), RecipesDataSourceClass::getProgressLiveStatus);

    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<RecipeModelClass>> getListLiveData() {
        return listLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

}
