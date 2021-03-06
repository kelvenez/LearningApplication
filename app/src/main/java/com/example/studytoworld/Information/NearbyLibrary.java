package com.example.studytoworld.Information;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.studytoworld.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class NearbyLibrary extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap googleMapRef;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_library);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(NearbyLibrary.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(NearbyLibrary.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        /*String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=" + currentLat + "," + currentLong +
                "&radius=5000" +
                "&types=" + "library" + //place type
                "&sensor=true" +
                "&key=" + getResources().getString(R.string.google_map_key);

        new PlaceTask().execute(url);*/


    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            googleMapRef = googleMap;
                            googleMapRef.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLat,currentLong),12
                            ));
                            LatLng place1 = new LatLng(22.36420349186002, 114.13179379408817);
                            LatLng place2 = new LatLng(22.370885018967122, 114.13914592047627);
                            LatLng place3 = new LatLng(22.375040356024865, 114.11582910183797);
                            LatLng place4 = new LatLng(22.35459183350226, 114.10610684328195);
                            LatLng place5 = new LatLng(22.375642133763584, 114.12410367299361);
                            googleMapRef.addMarker(new MarkerOptions().position(place1).title("South Kwai Chung Public Library"));
                            googleMapRef.addMarker(new MarkerOptions().position(place2).title("North Kwai Chung Public Library"));
                            googleMapRef.addMarker(new MarkerOptions().position(place3).title("Tsuen Wan Public Library"));
                            googleMapRef.addMarker(new MarkerOptions().position(place4).title("Tsing Yi Public Library"));
                            googleMapRef.addMarker(new MarkerOptions().position(place5).title("Shek Wai Kok Public Library"));
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("testMap",data);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {

        URL url = new URL(string);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line = "";
        while((line = reader.readLine()) != null){
            builder.append(line);
        }
        String data = builder.toString();
        reader.close();
        //Log.d("testMap",data);
        return data;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>>{
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();

            List<HashMap<String,String>> mapList = null;
            JSONObject object =null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            googleMapRef.clear();
            for(int i=0;i<hashMaps.size();++i){
                HashMap<String,String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");

                LatLng latLng = new LatLng(lat,lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                googleMapRef.addMarker(options);
            }
        }
    }
}