package com.josephmesteban.banji.datasource;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.josephmesteban.banji.utils.Constant;
import com.josephmesteban.banji.utils.RecipeModelClass;
import com.josephmesteban.banji.utils.Repository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class RecipesDataSourceClass extends PageKeyedDataSource<Integer, RecipeModelClass> {

    private Repository repository;
    private Gson gson;
    private int sourceIndex;
    private MutableLiveData<String> progressLiveStatus;
    private CompositeDisposable compositeDisposable;

    RecipesDataSourceClass(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        progressLiveStatus = new MutableLiveData<>();
        GsonBuilder builder =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = builder.setLenient().create();
    }


    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, RecipeModelClass> callback) {

        ArrayList<RecipeModelClass> arrayList = new ArrayList<>();

        repository.executeRecipesApi()
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            JSONObject object = new JSONObject(gson.toJson(result));
                            JSONArray array = object.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                String id = array.getJSONObject(i).optString("id");
                                String title = array.getJSONObject(i).optJSONObject("attributes").optString("title");
                                String imageId = array.getJSONObject(i).optJSONObject("relationships").optJSONObject("image").optJSONObject("data").optString("id");
                                arrayList.add(new RecipeModelClass(id, title, imageId));
                            }

                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)

                );

        for (RecipeModelClass recipe: arrayList) {
            repository.executeRecipeImageApi(recipe.getRecipeImageId()).subscribe(
                    (JsonElement imageResult) ->
                    {
                        JSONObject imageObject = new JSONObject(gson.toJson(imageResult));
                        String imageUrl = imageObject.optJSONObject("data").optJSONObject("attributes").optString("url");
                        recipe.setRecipeImage(imageUrl);
                    },
                    throwable ->
                            progressLiveStatus.postValue(Constant.LOADED)
            );
        }

        sourceIndex++;
        callback.onResult(arrayList, null, sourceIndex);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, RecipeModelClass> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, RecipeModelClass> callback) {

        ArrayList<RecipeModelClass> arrayList = new ArrayList<>();

        repository.executeRecipesApi()
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JsonElement result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            JSONObject object = new JSONObject(gson.toJson(result));
                            JSONArray array = object.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                String id = array.getJSONObject(i).optString("id");
                                String title = array.getJSONObject(i).optJSONObject("attributes").optString("title");
                                String imageId = array.getJSONObject(i).optJSONObject("relationships").optJSONObject("image").optJSONObject("data").optString("id");
                                arrayList.add(new RecipeModelClass(id, title, imageId));
                            }

                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)
                );

        for (RecipeModelClass recipe: arrayList) {
            repository.executeRecipeImageApi(recipe.getRecipeImageId()).subscribe(
                    (JsonElement imageResult) ->
                    {
                        JSONObject imageObject = new JSONObject(gson.toJson(imageResult));
                        String imageUrl = imageObject.optJSONObject("data").optJSONObject("attributes").optString("url");
                        recipe.setRecipeImage(imageUrl);
                    },
                    throwable ->
                            progressLiveStatus.postValue(Constant.LOADED)
            );
        }

        callback.onResult(arrayList, params.key == 3 ? null : params.key + 1);

    }
}
