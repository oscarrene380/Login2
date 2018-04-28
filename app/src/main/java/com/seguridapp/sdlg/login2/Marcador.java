package com.seguridapp.sdlg.login2;

/**
 * Created by Don Jose on 27/04/2018.
 */

public class Marcador
{
    /*private String titulo;
    private double latitud,longitud;
    public Marcador(String titulo,double latitud,double longitud)
    {
        this.titulo=titulo;
        this.latitud=latitud;
        this.longitud=longitud;
    }
    public Marcador()
    {

    }
    public String getTitulo(){return titulo;};
    public void setTitulo(String titulo){this.titulo=titulo;}

    public double getLatitud(){return  latitud;}
    public void setLatitud(double latitud){this.latitud=latitud;}

    public double getLongitud(){return  longitud;}
    public void setLongitud(double longitud){this.longitud=longitud;}

    public String toString(){return titulo;}

    public SQLiteDatabase lectura(Context context)
    {
        BDSistema sqlHelper=new BDSistema(context);
        return sqlHelper.getReadableDatabase();
    }
    public SQLiteDatabase escritura(Context context)
    {
        BDSistema sqlHelper=new BDSistema(context);
        return sqlHelper.getWritableDatabase();
    }

    public ArrayAdapter<Object> obtenerMarcadores(Context context)
    {
        try
        {
            ArrayAdapter<Object> adapter=new ArrayAdapter<>(context,android.R.layout.simple_list_item_1);
            ArrayList<Object> arrayList=new ArrayList<>();
            Cursor c=lectura(context).rawQuery("Select * from marcadores",null);
            while(c.moveToNext())
            {
                arrayList.add(new Marcador(c.getString(0),c.getDouble(1),c.getDouble(2)));
            }
            adapter.addAll(arrayList);
            return  adapter;
        }
        catch(Exception ex)
        {
            return new ArrayAdapter<Object>(context,android.R.layout.simple_list_item_1);
        }

    }

    public long ingresar(Context context)
    {
        ContentValues content=new ContentValues();
        content.put("titulo",titulo);
        content.put("latitud",latitud);
        content.put("longitud",longitud);
        return escritura(context).insert("marcadores",null,content);
    }

*/
}
