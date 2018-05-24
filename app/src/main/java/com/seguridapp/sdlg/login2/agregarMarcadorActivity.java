package com.seguridapp.sdlg.login2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class agregarMarcadorActivity extends AppCompatActivity {

    String usuario;
    double latitud;
    double longitud;
    String consulta;
    Spinner sp ;

    BDSistema bdSistema;
    SQLiteDatabase bd;
    Cursor c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_marcador);
        usuario = getIntent().getStringExtra("Usuario");
        latitud = Double.parseDouble(getIntent().getStringExtra("latitud"));
        longitud = Double.parseDouble(getIntent().getStringExtra("longitud"));
        Toast.makeText(this, usuario+"\n"+latitud+"\n"+longitud, Toast.LENGTH_LONG).show();
        bdSistema = new BDSistema(this, "BDSistema", null, 1);
        bd = bdSistema.getWritableDatabase();
        sp = findViewById(R.id.spMotivo);
        //llenarCombo();

    }

    public void CancelarButton(View v)
    {
        Intent i = new Intent(agregarMarcadorActivity.this,MapsActivity.class);
        i.putExtra("Username",usuario);
        startActivity(i);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /*public void llenarCombo()
    {
        List<String> motivos = new ArrayList<>();
        c = bd.rawQuery("Select * from tblMotivos", null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                motivos.add(c.getString(0));
            } while (c.moveToNext());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,R.layout.activity_agregar_marcador,motivos);
        adaptador.setDropDownViewResource(R.layout.activity_agregar_marcador);
        sp.setAdapter(adaptador);

    }*/

    public void Aceptar(View v)
    {
        EditText descripcion1 = findViewById(R.id.etDescripcion);
        String descripcion = descripcion1.getText().toString();

        //String motivo = sp.getSelectedItem().toString();
        consulta = "insert into tblPosiciones(latitud,longitud,idUsuario,motivo,descripcion)";
        consulta += "values (" + latitud + "," + longitud + ",'"+usuario+"','Asalto','"+descripcion+"')";
        //Toast.makeText(this, consulta, Toast.LENGTH_LONG).show();
        if (validarCampos()){
            try {
                bd.execSQL(consulta);
                Toast.makeText(getApplicationContext(), "Marcador guardado en\nLatitud: " + latitud + "\nLongitud:  " + longitud, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(agregarMarcadorActivity.this,MapsActivity.class);
                i.putExtra("Username",usuario);
                startActivity(i);
                this.finish();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No se pudieron guardar los datos", Toast.LENGTH_LONG).show();
            }
        }
    }

    boolean validarCampos(){
        String descripcion = ((EditText)findViewById(R.id.etDescripcion)).getText().toString();
        if (descripcion.equals("") || descripcion.equals(" ")){
            Toast.makeText(getApplicationContext(),"Llene todos los campos",Toast.LENGTH_SHORT);
            return false;
        }
        else{
            return true;
        }
    }
}
