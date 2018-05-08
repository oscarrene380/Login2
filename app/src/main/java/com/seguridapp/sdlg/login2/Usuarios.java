package com.seguridapp.sdlg.login2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Usuario on 18/4/2018.
 */

public class Usuarios extends AppCompatActivity
{

    /*ArrayList arrayUsuarios = new ArrayList();
    ArrayList arrayPassword = new ArrayList();
    public Usuarios()
    {
        agregarRegistros();
    }


    void agregarRegistros()
    {
        arrayUsuarios.add("hola@gmail.com");
        arrayUsuarios.add("oscar@gmail.com");
        arrayUsuarios.add("fernando@gmail.com");
        arrayUsuarios.add("diego@gmail.com");
        arrayUsuarios.add("alexx@gmail.com");

        arrayPassword.add("123");
        arrayPassword.add("oscar");
        arrayPassword.add("fernando");
        arrayPassword.add("diego");
        arrayPassword.add("alex");
    }

    //MÉTODOS PARA OBTENER DATOS
    public String getPassword(String usuario)
    {
        int n = arrayUsuarios.indexOf(usuario);
        return arrayPassword.get(n).toString();
    }

    //Validar cuentas


    public boolean validarCorreo(String usuario)
    {
        return arrayUsuarios.contains(usuario);
    }

    public void setUsuario(String correo)
    {
        arrayUsuarios.add(correo);
    }

    public void setPassword(String password)
    {
        arrayPassword.add(password);
    }

    public boolean validarPassword(String pass, String email)
    {
        int n = arrayUsuarios.indexOf(email);
        if (arrayPassword.get(n).toString().equals(pass)){
            return true;
        }
        else{
            return false;
        }
    }*/

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
    public Usuarios()
    {
        bdSistema = new BDSistema(this,"BDSistema",null,1);
        bd = bdSistema.getWritableDatabase();
        bd=bdSistema.getReadableDatabase();
    }
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
            resultado=false;
        }
        return resultado;
    }
}
