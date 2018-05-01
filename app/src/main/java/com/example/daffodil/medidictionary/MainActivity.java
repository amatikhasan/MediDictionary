package com.example.daffodil.medidictionary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,NavigationView.OnNavigationItemSelectedListener {

    //this class shows the word list and search view

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    ArrayList<WordData> arraylist = new ArrayList<>();
    DBExternal dbExternal;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar initialized
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LSU Medical Dictionary");

        //drawer initialized
        drawerLayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //drawer navigation iitialized
        NavigationView navigationView = findViewById(R.id.navigation_viw);
        navigationView.setNavigationItemSelectedListener(this);

        // Locate ListView from listview_main.xml
        list = (ListView) findViewById(R.id.listview);

        // Generate DBExternal class instance
        dbExternal=new DBExternal(getApplicationContext());

        //calling the showWord method from DbExternal class and push the returned data in arraylist
        arraylist=dbExternal.showWord();

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        //initialize searchView and add Text Listener
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        //method to handle list item click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //start activity WordDetails
                //get id,word name from the arraylist
                // pass id,word name to WordDetails using Intent putExtra
                Intent intent = new Intent(getApplicationContext(), WordDetails.class);
                intent.putExtra("id", arraylist.get(position).getId());
                intent.putExtra("name", arraylist.get(position).getWord());
                startActivity(intent);

                Log.d("Word", "onItemClick: "+arraylist.get(position).getId()+" "+arraylist.get(position).getWord());
            }
        });
    }

    //override method for search view
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    //method for showing results on new word on search view
    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        //calling filter method from ListViewAdapter class
        adapter.filter(text);
        return false;
    }

    //override method for drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //method to handle drawer menu click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            startActivity(new Intent(this,about.class));
        }



        return false;
    }
}