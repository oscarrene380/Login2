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
import android.widget.Toast;

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
            if (session.nivelActivo() == 1){
                Intent i = new Intent(MainActivity.this,MapsActivity.class);
                i.putExtra("Username",session.usuarioActivo());
                Toast.makeText(this, "Bienvenid@ de nuevo, "+session.usuarioActivo(), Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }
            else if(session.nivelActivo() == 2){
                Intent i = new Intent(MainActivity.this,adminPantallaActivity.class);
                i.putExtra("Username",session.usuarioActivo());
                Toast.makeText(this, "Bienvenid@ de nuevo, "+session.usuarioActivo(), Toast.LENGTH_LONG).show();
                startActivity(i);
                finish();
            }
        }

    }

    void IniciarBD()
    {
        //Comprobando si la BD Abre correctamente
        if (bd!=null)
        {
            //Generar valores
            //int idUsuario= i;
            bd.execSQL("insert into tblMotivos(Motivo) values ('Robo')");
            bd.execSQL("insert into tblMotivos(Motivo) values ('Asalto')");
            bd.execSQL("insert into tblMotivos(Motivo) values ('Acto delictivo')");
            bd.execSQL("insert into tblMotivos(Motivo) values ('Escena de violencia')");
            String email = "oscar", password = "123";
            //Creando cadena SQL
            Consulta = "insert into tblUsuarios (Email,Password,Estado,Nivel) ";
            Consulta += "VALUES ('"+email+"' , '"+password+"',0,1)";
            bd.execSQL(Consulta);

            email = "alex";
            //Creando cadena SQL
            Consulta = "insert into tblUsuarios (Email,Password,Estado,Nivel) ";
            Consulta += "VALUES ('"+email+"' , '"+password+"',1,1)";
            bd.execSQL(Consulta);

            email = "fernando";
            //Creando cadena SQL
            Consulta = "insert into tblUsuarios (Email,Password,Estado,Nivel) ";
            Consulta += "VALUES ('"+email+"' , '"+password+"',1,1)";
            bd.execSQL(Consulta);


            email = "admin";
            //Creando cadena SQL
            Consulta = "insert into tblUsuarios (Email,Password,Estado,Nivel) ";
            Consulta += "VALUES ('"+email+"' , '"+password+"',1,2)";
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
            else if(!c.moveToFirst() && c.getInt(3) == 1)
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
                int nivel = 0;
                int Estadousr = 0;
                boolean estado = false;

                c = bd.rawQuery("Select Nivel,Estado from tblUsuarios where Email = '"+user+"'", null);
                if (c.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        nivel = c.getInt(0);
                        Estadousr = c.getInt(1);

                    } while (c.moveToNext());
                }
                if (Estadousr != 0){
                    if (nivel == 1){
                        Intent i = new Intent(MainActivity.this, MapsActivity.class);
                        i.putExtra("Username",user);
                        startActivity(i);
                        session.setLoggedin(true);
                        session.setNivelActivo(1);
                        session.setUsuarioActivo(user);
                        finish();
                    }
                    else if(nivel == 2){
                        Intent i = new Intent(MainActivity.this, adminPantallaActivity.class);
                        i.putExtra("Username",user);
                        startActivity(i);
                        session.setLoggedin(true);
                        session.setNivelActivo(2);
                        session.setUsuarioActivo(user);
                        finish();
                    }
                    else{
                        Toast.makeText(this, "Error obteniendo nivel de usuario", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    AlertDialog.Builder ale = new AlertDialog.Builder(this);
                    ale.setMessage("El usuario no existe en el registro");
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
                    usr.setText("");
                    pss.setText("");
                    usr.requestFocus();
                }


            }
        }
    }




    public void Registrar(View v)
    {
        Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
        i.putExtra("Nivel","1");
        startActivity(i);

    }

}
