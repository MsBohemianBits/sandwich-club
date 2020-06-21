package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView description_tv;
    TextView origin_tv;
    TextView also_known_tv;
    TextView ingredients_tv;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        description_tv = findViewById(R.id.description_tv);
        description_tv.setText(sandwich.getDescription());
        origin_tv = findViewById(R.id.origin_tv);
        if (sandwich.getPlaceOfOrigin().length() > 0) {
            origin_tv.setText(sandwich.getPlaceOfOrigin());
        } else {
            origin_tv.setText(R.string.not_available);
        }
        also_known_tv = findViewById(R.id.also_known_tv);

        StringBuilder alsoKnownSB = new StringBuilder();
        List<String> alsoKnown = sandwich.getAlsoKnownAs();
        if (alsoKnown != null && alsoKnown.size() > 0) {
            for (int i = 0; i < alsoKnown.size(); i++) {
                alsoKnownSB.append(alsoKnown.get(i));
                if (i < alsoKnown.size() - 1) {
                    alsoKnownSB.append(", ");
                }
            }
            also_known_tv.setText(alsoKnownSB.toString());
        } else {
            also_known_tv.setText(getString(R.string.no_other_names));
        }

        ingredients_tv = findViewById(R.id.ingredients_tv);

        StringBuilder ingredientsSB = new StringBuilder();
        List<String> ingredientsList = sandwich.getIngredients();

        if (ingredientsList != null && ingredientsList.size() > 0) {
            for (int i = 0; i < ingredientsList.size(); i++) {
                ingredientsSB.append(ingredientsList.get(i));
                if (i < ingredientsList.size() - 1) {
                    ingredientsSB.append("\n");
                }
            }
            ingredients_tv.setText(ingredientsSB.toString());
        }

    }
}
