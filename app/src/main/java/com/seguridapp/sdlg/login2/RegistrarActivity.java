package com.seguridapp.sdlg.login2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Pattern;

public class RegistrarActivity extends AppCompatActivity
{
    int Nivel = 0;
    usuarios user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        user=new usuarios(this);
        Nivel = Integer.valueOf(getIntent().getStringExtra("Nivel").toString());
    }

    void Atras()
    {
        Intent i = new Intent(RegistrarActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void Cancelar(View v)
    {
       finish();

    }

    private boolean validarEmail(String email)
    {
        Pattern pattern= Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void Registrar(View v)
    {
        EditText em = findViewById(R.id.etEmail);
        EditText pss = findViewById(R.id.etPassword);
        EditText pss1 = findViewById((R.id.etConfirmar));
        String email = em.getText().toString();
        String pass = pss.getText().toString();
        String pass1 = pss1.getText().toString();
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        user.setEmail(email);
        user.setPassword(pass);
        user.setNivel(Nivel);
        if (email.isEmpty())
        {
            em.setError("Introduzca su email");
        }
        else if(!validarEmail(email))
        {
            em.setError("Email no valido!");
        }
        else if (pass.isEmpty())
        {
            pss.setError("Introduzca una contraseña");
        }
        else if (pass1.isEmpty())
        {
            pss1.setError("Introduzca una contraseña para validar");
        }
        else if (!pass.equals(pass1))
        {
            alerta.setMessage("Las contraseñas no coinciden");
            alerta.setTitle("Error de validación");
            alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText pss1 = findViewById((R.id.etConfirmar));
                    EditText pss = findViewById(R.id.etPassword);
                    dialog.cancel();
                    pss1.setText("");
                    pss.setText("");
                }
            });
            AlertDialog alerta1 = alerta.create();
            alerta1.show();
        }
        else if (user.validarCorreo())
        {
            alerta.setMessage("El correo eléctronico ya existe en el registro");
            alerta.setTitle("Error");
            alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText pss1 = findViewById((R.id.etConfirmar));
                    EditText pss = findViewById(R.id.etPassword);
                    dialog.cancel();
                    pss1.setText("");
                    pss.setText("");
                }
            });
            AlertDialog alerta1 = alerta.create();
            alerta1.show();
        }
        else
        {

            if(user.registrarUsuario())
            {
                alerta.setMessage("Registro agregado exitosamente");
                alerta.setTitle("Registro");
                alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(RegistrarActivity.this,MainActivity.class);
                        finish();
                        startActivity(i);
                    }
                });
                AlertDialog alerta1 = alerta.create();
                alerta1.show();
            }
            else
            {
                alerta.setMessage("No se pudo registrar!");
                alerta.setTitle("Registro");
                alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Atras();
                    }
                });
                AlertDialog alerta1 = alerta.create();
                alerta1.show();
            }

        }
    }
}
