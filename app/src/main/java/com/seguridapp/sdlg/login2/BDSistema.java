package com.seguridapp.sdlg.login2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Don Jose on 26/04/2018.
 */

public class BDSistema extends SQLiteOpenHelper {

    String sqlCrear = "CREATE TABLE tblUsuarios(idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, Email TEXT, Password TEXT)";
    String sqlCrear1 = "CREATE TABLE tblUsuarios(idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, Email TEXT, Password TEXT, Alias TEXT)";
    String sqlPosiciones = "CREATE TABLE tblPosiciones(latitud DECIMAL(10,8),longitud DECIMAL(10,8))";

    public BDSistema(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Si no existe la BD, crea la BD, la crea y ejecuta los siguientes comandos.
        db.execSQL(sqlCrear);
        db.execSQL(sqlPosiciones);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Se elimina la versión anterior de la BD
        db.execSQL("DROP TABLE IF EXISTS tblUsuarios");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCrear);

    }

}
