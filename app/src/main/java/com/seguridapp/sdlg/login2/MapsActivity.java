package com.seguridapp.sdlg.login2;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
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
    BDSistema bdSistema;
    SQLiteDatabase bd;
    Cursor c;
    String consulta;
    private sesion session;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bdSistema = new BDSistema(this, "BDSistema", null, 1);
        bd = bdSistema.getWritableDatabase();
        //mostrarMarcadores();
        session = new sesion(this);
        usuario = getIntent().getStringExtra("Username");
    }

    public void ubicarme(View v) {
        miUbicacion();
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
            Toast.makeText(getApplicationContext(), "Servicios activados exitosamente", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Por favor, active los servicios de GPS para el buen funcionamiento de la aplicación", Toast.LENGTH_LONG).show();
        }
    };

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //Método para mostrar formulario


    //Método para mostrar los marcadores guardados
    public void mostrarMarcadores(GoogleMap googleMap) {
        try {
            mMap = googleMap;
            double latitud;
            double longitud;
            /*c = bd.rawQuery("Select count(*) from tblPosiciones",null);
            int total = c.getInt(0);*/
            c = bd.rawQuery("Select latitud,longitud,estado from tblPosiciones", null);
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    if (c.getInt(2) == 1){
                        latitud = c.getDouble(0);
                        longitud = c.getDouble(1);
                        LatLng punto = new LatLng(latitud, longitud);
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                                .anchor(0.0f, 1.0f)
                                .position(punto)
                        );
                    }
                } while (c.moveToNext());


                Toast.makeText(getApplicationContext(), "Marcadores actualizados exitosamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "No hay marcadores en la base de Datos", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error inesperado", Toast.LENGTH_SHORT).show();
        }

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
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/


        mMap.setOnMapLongClickListener(new OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                double latitud = latLng.latitude;
                double longitud = latLng.longitude;
                String latitud1 = String.valueOf(latitud);
                String longitud1 = String.valueOf(longitud);
                Intent i = new Intent(MapsActivity.this,agregarMarcadorActivity.class);
                i.putExtra("Usuario",usuario);
                i.putExtra("latitud",latitud1);
                i.putExtra("longitud",longitud1);
                startActivity(i);
                finish();
                /*consulta = "insert into tblPosiciones(latitud,longitud)";
                consulta += "values (" + latitud + "," + longitud + ")";
                try {
                    bd.execSQL(consulta);
                    Toast.makeText(getApplicationContext(), "Marcador guardado en\nLatitud: " + latitud + "\nLongitud:  " + longitud, Toast.LENGTH_SHORT).show();
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                            .anchor(0.0f, 1.0f)
                            .position(latLng)
                    );

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No se pudieron guardar los datos", Toast.LENGTH_LONG).show();
                }*/

            }
        });
        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double latitud = marker.getPosition().latitude;
                double longitud = marker.getPosition().longitude;
                if (location.getLatitude() == latitud && location.getLongitude() == longitud){

                }
                else{
                    String usuario1,motivo,descripcion;
                    c = bd.rawQuery("Select idUsuario,motivo,descripcion from tblPosiciones where latitud = "+latitud+" and longitud = "+longitud, null);
                    if (c.moveToFirst()) {
                        //Recorremos el cursor hasta que no haya más registros

                        do {
                            usuario1 = c.getString(0);
                            motivo= c.getString(1);
                            descripcion = c.getString(2);
                        } while (c.moveToNext());
                        marker.setTitle(usuario1+": "+descripcion);
                        Toast.makeText(getApplicationContext(),"Zona marcada por "+motivo,Toast.LENGTH_SHORT).show();
                    }



                }
                return false;
            }
        });

        miUbicacion();
        mostrarMarcadores(googleMap);

    }

    public void Atras(View v)
    {
        Intent i = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(i);
        session.setLoggedin(false);
        session.setNivelActivo(0);
        session.setUsuarioActivo("");
        finish();
    }
    public void editar(View view)
    {
        Intent i = new Intent(MapsActivity.this, EditUserActivity.class);
        startActivity(i);
        finish();
    }
}
