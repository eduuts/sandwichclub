package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Save each textview from the detail xml for later updating or hiding the text

        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownAsTv = findViewById(R.id.also_known_tv);

        TextView originTitle = findViewById(R.id.place_of_origin_title);
        TextView descriptionTitle = findViewById(R.id.description_title);
        TextView ingredientsTitle = findViewById(R.id.ingredients_title);
        TextView alsoKnownAsTitle = findViewById(R.id.also_known_as_title);

        //Either update the text from the database, or hide the TextViews if they are empty

        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTv.setText(android.text.TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        } else{
            alsoKnownAsTitle.setVisibility(View.GONE);
            alsoKnownAsTv.setVisibility(View.GONE);
        }

        if (!sandwich.getDescription().isEmpty()) {
            descriptionTv.setText(sandwich.getDescription());
        } else{
            descriptionTitle.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        }

        if (!sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setText(android.text.TextUtils.join(", ", sandwich.getIngredients()));
        } else{
            ingredientsTitle.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        }

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTitle.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        }
    }
}
