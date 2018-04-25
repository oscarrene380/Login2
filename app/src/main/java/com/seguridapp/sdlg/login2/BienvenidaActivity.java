package com.seguridapp.sdlg.login2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BienvenidaActivity extends AppCompatActivity {

    private GoogleMap Mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        String username = getIntent().getStringExtra("Username");
        TextView tv = (TextView)findViewById(R.id.tvUsuario);
        tv.setText(username);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa);
        //mapFragment.getMapAsync(this);

    }

    void Atras()
    {
        Intent i = new Intent(BienvenidaActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void CerrarSesion(View v)
    {
        Atras();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Atras();
    }


    public void onMapReady(GoogleMap googleMap)
    {
        Mapa = googleMap;

        //Add a marker in Sydney and move the camera
        LatLng Sydney = new LatLng(-34,151);
        Mapa.addMarker(new MarkerOptions().position(Sydney).title("Estás en Sydney"));
        Mapa.moveCamera(CameraUpdateFactory.newLatLng(Sydney));
    }
}
