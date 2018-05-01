package com.example.daffodil.medidictionary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordDetails extends AppCompatActivity {

    //this class showa the details for the word with image.

    //declare values
    TextView tvname, tvdefinition, tvresource;
    ImageView image;
    Bundle extras;
    Toolbar toolbar;
    ArrayList<WordData> arraylist = new ArrayList<>();
    DBExternal dbExternal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LSU Medical Dictionary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbExternal = new DBExternal(getApplicationContext());

        tvname = findViewById(R.id.showTvWord);
        tvdefinition = findViewById(R.id.showTvDefinition);
        tvresource = findViewById(R.id.showTvResource);
        image = findViewById(R.id.showImage);

        //instance of bundle that receive the intent extra data passed to this class
        extras = getIntent().getExtras();

        //get id and word name from intent extra
        int id = extras.getInt("id");
        String name = extras.getString("name");

        //String definition = extras.getString("definition");
        // String resource = extras.getString("resource");
        //String imageByte = extras.getString("imageByte");
       //Log.d("imageByte", "onCreate: " + imageByte);

        //call showWordDetail from DBExternal class to get the details of the word passing the word id
        //push the returned data in arraylist
        arraylist = dbExternal.showWordDetail(id);

        //set word details from the returned data
        tvname.setText(name);
        tvdefinition.setText(arraylist.get(0).getDefinition());
        tvresource.setText(arraylist.get(0).getResource());
        String imageByte = arraylist.get(0).getImageByte();

        Log.d("imageByte", "onCreate: " + imageByte);

        //convert the image byte string to bitmap
        if (imageByte.length() != 0) {
            byte[] photo = Base64.decode(imageByte, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            //set bitmap to the imageview as a image
            image.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //for toolbar back button
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }

        return true;
    }
}
