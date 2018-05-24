package com.seguridapp.sdlg.login2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

public class EditUserActivity extends AppCompatActivity
{
    CheckBox cbEditarUser;
    CheckBox satView;
    CheckBox cbEditarPass;
    TextView txtNuevoUser;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);
        /*cbEditarUser=(CheckBox)findViewById(R.id.cbEditarUser);
        cbEditarPass=(CheckBox)findViewById(R.id.cbEditarPass);
        txtNuevoUser=(TextView)findViewById(R.id.txtNuevoUser);
        txtNuevoUser.setEnabled(false);
        //cbEditarUser.setOnCheckedChangeListener(new MyCheckedChangeListener(1));
        //cbEditarPass.setOnCheckedChangeListener(new MyCheckedChangeListener(2));*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }






}
