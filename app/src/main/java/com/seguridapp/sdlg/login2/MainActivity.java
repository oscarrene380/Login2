package com.seguridapp.sdlg.login2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    BDSistema bdSistema;
    String Consulta;
    SQLiteDatabase bd;
    Cursor c;
    private sesion session;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText usr = (EditText)findViewById(R.id.etUsuario);
        EditText pss = findViewById(R.id.etPassword);
        usr.setText("");
        pss.setText("");
        bdSistema = new BDSistema(this,"BDSistema",null,1);
        bd = bdSistema.getWritableDatabase();
        IniciarBD();
        session=new sesion(this);
        if(session.loggedin())
        {
            startActivity(new Intent(MainActivity.this,MapsActivity.class));
            finish();
        }

    }

    void IniciarBD()
    {
        //Comprobando si la BD Abre correctamente
        if (bd!=null)
        {
            //Generar valores
            //int idUsuario= i;

            String email = "oscar", password = "123";
            //Creando cadena SQL
            Consulta = "insert into tblUsuarios (Email,Password) ";
            Consulta += "VALUES ('"+email+"' , '"+password+"')";
            bd.execSQL(Consulta);

            email = "alex";
            //Creando cadena SQL
            Consulta = "insert into tblUsuarios (Email,Password) ";
            Consulta += "VALUES ('"+email+"' , '"+password+"')";
            bd.execSQL(Consulta);

            email = "fernando";
            //Creando cadena SQL
            Consulta = "insert into tblUsuarios (Email,Password) ";
            Consulta += "VALUES ('"+email+"' , '"+password+"')";
            bd.execSQL(Consulta);

            //bd.close();
        }
        else{
            AlertDialog.Builder ale = new AlertDialog.Builder(this);
            ale.setMessage("No se pudieron iniciar los datos");
            ale.setTitle("Error de Datos");
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
    }

    public void OnButtonClick(View v)
    {
        EditText usr = findViewById(R.id.etUsuario);
        EditText pss = findViewById(R.id.etPassword);
        String user = usr.getText().toString();
        String pass = pss.getText().toString();
        String consulta = "select Email,Password from tblUsuarios where Email='"+user.trim()+"' and Password='"+pass.trim()+"'";
        //else if(!c.moveToFirst())
        c = bd.rawQuery(consulta,null);
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
            //else if(!usuarios.validarCorreo(user) || !usuarios.getPassword(user).equals(pass))
            else if(!c.moveToFirst())
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
                session.setLoggedin(true);
                finish();
            }
        }
    }

    public void Registrar(View v)
    {
        Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
        startActivity(i);
        finish();
    }
}
