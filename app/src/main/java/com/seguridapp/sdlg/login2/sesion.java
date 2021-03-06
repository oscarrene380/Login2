package com.seguridapp.sdlg.login2;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Don Jose on 10/05/2018.
 */

public class sesion
{
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public sesion(Context ctx)
    {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin)
    {
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public void setNivelActivo(int nivelUsuario)
    {
        editor.putInt("nivelUsuario",nivelUsuario);
        editor.commit();
    }

    public int nivelActivo()
    {
        return  prefs.getInt("nivelUsuario",0);
    }

    public void setUsuarioActivo(String usuarioActivo)
    {
        editor.putString("usuarioActivo",usuarioActivo);
        editor.commit();
    }

    public String usuarioActivo()
    {
        return  prefs.getString("usuarioActivo","");
    }


    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }
}
