package com.example.deliciousandathome;

public class Recipe {
    public String id, Name, Ingredients, Cooking, Category;

    public Recipe(){

    }

    public Recipe(String id, String Name, String Ingredients, String Cooking, String Category){
        this.id = id;
        this.Name = Name;
        this.Ingredients = Ingredients;
        this.Cooking = Cooking;
        this.Category = Category;
    }

}
