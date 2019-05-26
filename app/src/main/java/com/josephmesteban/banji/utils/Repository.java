package com.josephmesteban.banji.utils;

import com.google.gson.JsonElement;

import io.reactivex.Observable;

public class Repository {
    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call news api
     * */
    public Observable<JsonElement> executeRecipesApi() {
        return apiCallInterface.fetchRecipes();
    }

    public Observable<JsonElement> executeRecipeImageApi(String recipeId) {
        return apiCallInterface.fetchImage(recipeId);
    }
}
