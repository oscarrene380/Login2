package com.seguridapp.sdlg.login2;

import android.content.ContentValues;
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

    sesion session;
    public usuarios(Context context)
    {
        bdSistema = new BDSistema(context,"BDSistema",null,1);
        bd = bdSistema.getWritableDatabase();
        bd=bdSistema.getReadableDatabase();
        email=null;
        password=null;
        session=new sesion(context);
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

    public int getNivel() {
        return Nivel;
    }

    public void setNivel(int nivel) {
        Nivel = nivel;
    }

    private int Nivel;

    public boolean registrarUsuario()
    {
        String consulta=null;
        boolean resultado=false;
        try
        {
            consulta = "insert into tblUsuarios (Email,Password,Estado,Nivel) VALUES ('"+getEmail()+"' , '"+getPassword()+"',1,"+getNivel()+")";
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

    public String consultarPassword(String user)
    {
        String pass=null;
        String consulta="select Password from tblUsuarios where Email='"+user+"' ";
        try
        {
            c=bd.rawQuery(consulta,null);
            if(c.moveToFirst())
            {
                do
                {
                    pass=c.getString(0);
                }while(c.moveToNext());

            }
            else
            {
                pass="Error al conectar al servidor!";
            }
        }
        catch (Exception ex)
        {
            pass="Error al conectar al servidor!";
        }
        return  pass;
    }

    public boolean actulizarDatos(String email)
    {
        boolean result=false;
        try
        {
            ContentValues registro= new ContentValues();
            registro.put("Email",getEmail());
            registro.put("Password",getPassword());
            int i=bd.update("tblUsuarios",registro,"Email='"+email+"' ",null);
            if(i>0)
            {
                result=true;
                session.setUsuarioActivo(getEmail());
            }

        }
        catch (Exception ex)
        {

        }
        return result;
    }

    public boolean actulizarEmail(String email)
    {
        boolean result=false;
        try
        {
            ContentValues registro= new ContentValues();
            registro.put("Email",getEmail());
            int i=bd.update("tblUsuarios",registro,"Email='"+email+"' ",null);
            if(i>0)
            {
                result=true;
                session.setUsuarioActivo(getEmail());
            }

        }
        catch (Exception ex)
        {

        }
        return result;
    }
    public boolean actulizarPassword(String email)
    {
        boolean result=false;
        try
        {
            ContentValues registro= new ContentValues();
            registro.put("Password",getPassword());
            int i=bd.update("tblUsuarios",registro,"Email='"+email+"' ",null);
            if(i>0)
            {
                result=true;
            }

        }
        catch (Exception ex)
        {

        }
        return result;
    }

    public boolean desactivarCuenta(String email)
    {
        boolean result=false;
        try
        {
            ContentValues registro= new ContentValues();
            registro.put("Estado",0);
            int i=bd.update("tblUsuarios",registro,"Email='"+email+"' ",null);
            if(i>0)
            {
                result=true;
                session.setLoggedin(false);
            }

        }
        catch (Exception ex)
        {

        }
        return result;
    }
}
