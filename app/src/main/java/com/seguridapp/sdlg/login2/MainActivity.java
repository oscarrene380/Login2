package com.seguridapp.sdlg.login2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Usuarios usuarios = new Usuarios();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText usr = (EditText)findViewById(R.id.etUsuario);
        EditText pss = findViewById(R.id.etPassword);
        usr.setText("");
        pss.setText("");
    }



    public void OnButtonClick(View v)
    {
        EditText usr = findViewById(R.id.etUsuario);
        EditText pss = findViewById(R.id.etPassword);
        String user = usr.getText().toString();
        String pass = pss.getText().toString();
        if (v.getId()==R.id.btIngresar)
        {

            if (user.isEmpty())
            {
                usr.setError("Campo vacío");
            }
            else if (pass.isEmpty())
            {
                pss.setError("Campo vacío");
            }
            else if(!usuarios.validarCorreo(user) || !usuarios.getPassword(user).equals(pass))
            {
                AlertDialog.Builder ale = new AlertDialog.Builder(this);
                ale.setMessage("Correo o contraseña inválidos");
                ale.setTitle("Error de credenciales");
                ale.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText usr = findViewById((R.id.etUsuario));
                        EditText pss = findViewById(R.id.etPassword);
                        dialog.cancel();
                        usr.setText("");
                        pss.setText("");
                    }
                });
                AlertDialog alerta = ale.create();
                alerta.show();
            }
            else
            {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("Username",user);
                startActivity(i);
            }
        }
    }

    public void Registrar(View v)
    {
        Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
        startActivity(i);
    }
}