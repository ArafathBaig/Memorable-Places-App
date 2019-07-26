package arafath.myappcom.memorableplacesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static  ArrayList<String> places = new ArrayList<>();
    static  ArrayList<LatLng> location = new ArrayList<>();static ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("package arafath.myappcom.memorableplacesapp", Context.MODE_PRIVATE);

        ArrayList<String> latitudes = new ArrayList<>();
        ArrayList<String> longitudes = new ArrayList<>();

        places.clear();
        latitudes.clear();
        longitudes.clear();
        location.clear();

        try{
            places = (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.deserialize(new ArrayList<String>())));
            latitudes= (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("lats",ObjectSerializer.deserialize(new ArrayList<String>())));

    longitudes= (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("long",ObjectSerializer.deserialize(new ArrayList<String>())));

        }catch (Exception e){

        }

        if(places.size() > 0 && latitudes.size() > 0 && longitudes.size() > 0){
            if(places.size() == latitudes.size() && places.size() == longitudes.size()){
                for(int i = 0 ; i < latitudes.size() ; i++){
                    location.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))));
                }
            }
        } else{
            places.add("Add a new place...");
            location.add(new LatLng(0,0));
        }


        ListView listView = findViewById(R.id.listVIew);




        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, places);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("placeNumber",i);

                startActivity(intent);
            }
        });

    }
}
