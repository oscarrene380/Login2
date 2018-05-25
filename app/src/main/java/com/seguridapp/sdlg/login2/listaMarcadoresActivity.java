package com.seguridapp.sdlg.login2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class listaMarcadoresActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    ArrayAdapter adaptador1;

    BDSistema bdSistema;
    SQLiteDatabase bd;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_marcadores);
        lv = findViewById(R.id.lvMarcadores);
        bdSistema = new BDSistema(this, "BDSistema", null, 1);
        bd = bdSistema.getWritableDatabase();
        Actualizar();


        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(listaMarcadoresActivity.this, "MArcador pulsado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void Actualizar(){

        lista = llenarListView();
        adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        lv.setAdapter(adaptador);

    }

    ArrayList llenarListView()
    {
        ArrayList<String> lista = new ArrayList<>();
        c = bd.rawQuery("select motivo,descripcion,idUsuario,estado from tblPosiciones",null);
        if (c.moveToFirst()){
            do{
                if (c.getInt(3) == 1){
                    lista.add(c.getString(2)+"--"+c.getString(0)+"\n\""+c.getString(1)+"\"");
                }
            }while(c.moveToNext());
        }
        return lista;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
