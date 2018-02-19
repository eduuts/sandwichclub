package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.ArrayUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// ArrayUtil java file taken from https://stackoverflow.com/a/40983074,
// for creating and using a simple "convert" function, to change data type
// from Json Array to ArrayList with just a function call.

public class JsonUtils {

    public static Sandwich parseSandwichJson(String jsonData) throws JSONException {

        Sandwich sandwich = new Sandwich();

        JSONObject jsonSandwich = new JSONObject(jsonData);
        // Data of type Json is now stored in our object, jsonSandwich
        JSONObject name = jsonSandwich.getJSONObject("name");
        String mainName = name.getString("mainName");
        String placeOfOrigin = jsonSandwich.getString("placeOfOrigin");
        String description = jsonSandwich.getString("description");
        String imageUrl = jsonSandwich.getString("image");
        JSONArray jsonAlsoKnownAs = name.getJSONArray("alsoKnownAs");
        JSONArray jsonIngredients = jsonSandwich.getJSONArray("ingredients");

        sandwich.setMainName(mainName);
        sandwich.setDescription(description);
        sandwich.setImage(imageUrl);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        // Setting the name, description, imageUrl and place of origin to the sandwich object.

        // For setting the "alsoKnownAs" and "ingredients" lists, we need to save each element
        // as a string in a list of strings, for later use, by converting each JSONArray to
        // Arraylist, and then going through the list, element by element, finally saving them in
        // alsoKnownAsList and ingredientsList

        ArrayList<Object> alsoKnownAs = ArrayUtil.convert(jsonAlsoKnownAs);
        List<String> alsoKnownAsList = new ArrayList<>();

        for (int i = 0; i < alsoKnownAs.size(); i++) {

            String alsoKnownAsString = alsoKnownAs.get(i).toString();
            alsoKnownAsList.add(alsoKnownAsString);
        }

        sandwich.setAlsoKnownAs(alsoKnownAsList);

        ArrayList<Object> ingredients = ArrayUtil.convert(jsonIngredients);
        List<String> ingredientsList = new ArrayList<>();

        for (int i = 0; i < ingredients.size(); i++) {

            String ingredientsString = ingredients.get(i).toString();
            ingredientsList.add(ingredientsString);
        }

        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }
}
