package com.josephmesteban.banji.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class RecipeModelClass {
    private String recipeId, recipeTitle, recipeImageId, recipeImage;

    public String getRecipeId() {
        return recipeId;
    }

    public String getRecipeImageId() {
        return recipeImageId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeImage() {  return recipeImage; }
    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }
    public RecipeModelClass(String recipeId, String recipeTitle, String recipeImageId) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.recipeImageId = recipeImageId;
    }

    public static DiffUtil.ItemCallback<RecipeModelClass> DIFF_CALLBACK = new DiffUtil.ItemCallback<RecipeModelClass>() {
        @Override
        public boolean areItemsTheSame(@NonNull RecipeModelClass oldItem, @NonNull RecipeModelClass newItem) {
            return oldItem.recipeId.equals(newItem.recipeId);
        }

        @Override
        public boolean areContentsTheSame(@NonNull RecipeModelClass oldItem, @NonNull RecipeModelClass newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        RecipeModelClass article = (RecipeModelClass) obj;
        return article.recipeId.equals(this.recipeId);
    }

}
