package com.seguridapp.sdlg.login2;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0;
    double lng = 0;

    EditText titulo;
    Spinner marcadores;
    Button guardar;
    LatLng punto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        titulo=(EditText)findViewById(R.id.txtTitulo);
        marcadores=(Spinner)findViewById(R.id.spinMarcadores);
        guardar=(Button)findViewById(R.id.btnGuardar);
    }


    void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) {
            marcador.remove();
        }
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Estás acá")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
        mMap.animateCamera(miUbicacion);
    }

    void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,locationListener);
    }





    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
           @Override
            public void onMapLongClick(LatLng latLng) {
               mMap.clear();
               punto=latLng;

                mMap.addMarker(new MarkerOptions()
                        .title("Nuevo Marcador")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icmarcadormapa))
                        .anchor(0.0f,1.0f)
                        .position(punto)
                );
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(punto,16));
                titulo.setEnabled(true);
                guardar.setEnabled(true);
            }

        });
        //mostrar();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(),"Zona marcada como peligrosa",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        miUbicacion();
    }
    /*public void guardar(View view)
    {
        Marcador marcador=new Marcador(titulo.getText().toString().trim(),punto.latitude,punto.longitude);
        marcador.ingresar(this);
        titulo.setText("");
        titulo.setEnabled(false);
        guardar.setEnabled(false);
        mostrar();
    }
    public void mostrar()
    {
        marcadores.setAdapter(new Marcador().obtenerMarcadores(this));
    }

    public void mostrarMarcador(View view)
    {
        mMap.clear();
        Marcador m=(Marcador)marcadores.getSelectedItem();
        punto=new LatLng(m.getLatitud(),m.getLongitud());
        mMap.addMarker(new MarkerOptions().position(punto).title(m.getTitulo()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(punto,174));

    }*/

    public void Atras(View v)
    {
        Intent i = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(i);
    }
}
