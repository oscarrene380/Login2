package com.seguridapp.sdlg.login2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class RegistrarActivity extends AppCompatActivity
{


    private String email;
    private String password;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    BDSistema bdSistema;
    String Consulta;
    SQLiteDatabase bd;
    Cursor c;

    public boolean registrarUsuario()
    {
        String consulgta=null;
        boolean resultado=false;
        try
        {
            Consulta = "insert into tblUsuarios (Email,Password) ";
            Consulta += "VALUES ('"+getEmail()+"' , '"+getPassword()+"')";
            bd.execSQL(Consulta);
            resultado=true;
        }
        catch (Exception ex)
        {
            resultado=false;
        }
        return resultado;
    }

    public boolean validarCorreo()
    {
        String consulta="SELECT * FROM tblUsuarios WHERE Email='"+getEmail()+"' ";
        boolean resultado=false;
        try
        {
            c=bd.rawQuery(consulta,null);
            if(c.moveToFirst())
            {
                resultado=true;
            }
        }
        catch (Exception ex)
        {
            resultado=true;
        }
        return resultado;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        bdSistema = new BDSistema(this,"BDSistema",null,1);
        bd = bdSistema.getWritableDatabase();
        bd=bdSistema.getReadableDatabase();
    }

    void Atras()
    {
        Intent i = new Intent(RegistrarActivity.this, MainActivity.class);
        startActivity(i);
    }

    public void Cancelar(View v)
    {
        Atras();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Atras();
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


        if (email.isEmpty())
        {
            em.setError("Introduzca su email");
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
        else if (validarCorreo())
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
            setPassword(pass);
            setEmail(email);
            if(registrarUsuario())
            {
                alerta.setMessage("Registro agregado exitosamente");
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
