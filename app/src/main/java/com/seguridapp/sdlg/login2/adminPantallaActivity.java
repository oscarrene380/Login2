package com.seguridapp.sdlg.login2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class adminPantallaActivity extends AppCompatActivity {

    String user;
    private sesion session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pantalla);
        user = getIntent().getStringExtra("Username");
        session=new sesion(this);
    }

    public void irMapa(View v)
    {
        Intent i = new Intent(adminPantallaActivity.this, MapsActivity.class);
        i.putExtra("Username","admin");
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void irEditarPerfil(View v)
    {
        Intent i = new Intent(adminPantallaActivity.this, EditUserActivity.class);
        i.putExtra("Username",user);
        startActivity(i);
    }

    public void irMarcadores(View v)
    {
        Intent i = new Intent(adminPantallaActivity.this, listaMarcadoresActivity.class);
        startActivity(i);
    }

    public void irAgregarAdministrador(View v)
    {
        Intent i = new Intent(adminPantallaActivity.this, RegistrarActivity.class);
        i.putExtra("Nivel","2");
        startActivity(i);
    }

    public void irCerrarSesion(View v)
    {
        Intent i = new Intent(adminPantallaActivity.this, MainActivity.class);
        session.setLoggedin(false);
        session.setNivelActivo(0);
        session.setUsuarioActivo("");
        finish();
        startActivity(i);

    }
}
