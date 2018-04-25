package com.seguridapp.sdlg.login2;

import java.util.ArrayList;

/**
 * Created by Usuario on 18/4/2018.
 */

public class Usuarios {

    ArrayList arrayUsuarios = new ArrayList();
    ArrayList arrayPassword = new ArrayList();
    public Usuarios()
    {
        agregarRegistros();
    }

    /**
     * Método para agregar registros a los arrayList
     */
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

    /**
     * Método para saber si un usuario existe o no
     * @param usuario recibe el correo electrónico a evaluar
     * @return retorna verdadero si existe y falso si no existe
     */
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
    }
}
