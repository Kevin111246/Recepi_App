package com.kevin.myrecipes.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.kevin.myrecipes.R;

public class RecipeAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }
    @Override
    public boolean onSupportNavigateUp() {                     // for the toolbar back
        //This method is called when the up button is pressed. Just the pop back stack.
        finish();
        return super.onSupportNavigateUp();
    }

}
