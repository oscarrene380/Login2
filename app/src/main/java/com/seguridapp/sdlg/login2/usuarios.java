package com.seguridapp.sdlg.login2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Don Jose on 13/05/2018.
 */

public class usuarios
{
    private String email;
    private String password;
    BDSistema bdSistema;
    String Consulta;
    SQLiteDatabase bd;
    Cursor c;


    public usuarios(Context context)
    {
        bdSistema = new BDSistema(context,"BDSistema",null,1);
        bd = bdSistema.getWritableDatabase();
        bd=bdSistema.getReadableDatabase();
        email=null;
        password=null;
    }
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

    public boolean registrarUsuario()
    {
        String consulta=null;
        boolean resultado=false;
        try
        {
            consulta = "insert into tblUsuarios (Email,Password) VALUES ('"+getEmail()+"' , '"+getPassword()+"')";
            bd.execSQL(consulta);
            resultado=true;
        }
        catch (Exception ex)
        {
            resultado=false;
            System.out.println("---aqui");
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
}
