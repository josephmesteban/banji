package com.josephmesteban.banji.utils;

import com.google.gson.JsonElement;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ${Saquib} on 12-08-2018.
 */
public interface ApiCallInterface {

    @GET(Urls.FetchRecipeList)
    Observable<JsonElement> fetchRecipes();

    @GET(Urls.FetchRecipeImage)
    Observable<JsonElement> fetchImage(@Path("Id") String recipeId);

}
