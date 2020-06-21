package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        // @todo remove hard-coded strings
        try {
            // Create new object to parse JSON data.
            JSONObject reader = new JSONObject(json);

            // Get basic objects and strings.
            JSONObject nameObject = reader.getJSONObject("name");
            sandwich.setMainName(nameObject.getString("mainName"));
            sandwich.setPlaceOfOrigin(reader.getString("placeOfOrigin"));
            sandwich.setDescription(reader.getString("description"));
            sandwich.setImage(reader.getString("image"));

            // Loop over items containing arrays.
            JSONArray ingredientItems = reader.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int ingredient = 0; ingredient < ingredientItems.length(); ingredient++) {
                ingredients.add(ingredientItems.getString(ingredient));
            }
            sandwich.setIngredients(ingredients);

            JSONArray akaItems = nameObject.getJSONArray("alsoKnownAs");
            List<String> aka = new ArrayList<>();
            for (int incrementer = 0; incrementer < akaItems.length(); incrementer++) {
                aka.add(ingredientItems.getString(incrementer));
            }
            sandwich.setAlsoKnownAs(aka);

        } catch (JSONException e) {
            Log.e("Sandwich", "Error: " + e.getMessage());
        }

        return sandwich;
    }
}
