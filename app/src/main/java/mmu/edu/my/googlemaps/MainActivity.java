package mmu.edu.my.googlemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Geocoder geocoder;
    TextView showaddress;
    TextView putIn;
    Button findBtn;
    SupportMapFragment supportMapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showaddress  =findViewById(R.id.textView);
        findBtn =findViewById(R.id.find);
        putIn =findViewById(R.id.search);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result=null;
                geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    Double lat,lng;
                    List<Address> addresses = geocoder.getFromLocationName(putIn.getText().toString(),1);
                    result = addresses.get(0).getAddressLine(0);
                    lng = addresses.get(0).getLongitude();
                    lat = addresses.get(0).getLatitude();
                    showaddress.setText(result);
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng point= new LatLng(lat,lng);
                            map=googleMap;
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(point,15));
                            map.addMarker(new MarkerOptions().position(point).title("your Location"));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}