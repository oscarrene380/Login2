package com.seguridapp.sdlg.login2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class EditUserActivity extends AppCompatActivity
{
    TextView txtUser;
    TextView txtPassword;

    CheckBox cbUser;
    CheckBox cbPass;

    TextView txtNewUser;
    TextView txtNewPassword;
    TextView txtCheckPassword;
    TextView txtPasswordA;

    Button btnSave;
    Button btnDesactivar;

    usuarios user;
    sesion session;
    String contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);
        session=new sesion(this);
        user=new usuarios(this);
        contraseña=user.consultarPassword(session.usuarioActivo());

        btnSave=(Button)findViewById(R.id.btnSave);
        btnSave.setEnabled(false);
        btnDesactivar=(Button)findViewById(R.id.btnDesactivar);

        txtUser=(TextView)findViewById(R.id.txtUser);
        txtUser.setEnabled(false);
        txtUser.setText(session.usuarioActivo());
        txtPassword=(TextView)findViewById(R.id.txtPassword);
        txtPassword.setEnabled(false);
        txtPassword.setText(contraseña);
        txtPasswordA=(TextView)findViewById(R.id.txtPasswordA);

        txtNewUser=(TextView)findViewById(R.id.txtNewUser);
        txtNewUser.setEnabled(false);
        txtNewPassword=(TextView)findViewById(R.id.txtNewPass);
        txtNewPassword.setEnabled(false);
        txtCheckPassword=(TextView)findViewById(R.id.txtCheckPass);
        txtCheckPassword.setEnabled(false);

        cbUser=(CheckBox)findViewById(R.id.cbUser);
        cbUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean checked=((CheckBox)view).isChecked();
                if(checked)
                {
                    txtNewUser.setEnabled(true);
                    btnSave.setEnabled(true);
                }
                else
                {
                    txtNewUser.setEnabled(false);
                    txtNewUser.setText(null);
                }
            }
        });

        cbPass=(CheckBox)findViewById(R.id.cbPass);
        cbPass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean checked=((CheckBox)view).isChecked();
                if(checked)
                {
                    txtNewPassword.setEnabled(true);
                    txtCheckPassword.setEnabled(true);
                    btnSave.setEnabled(true);
                }
                else
                {
                    txtNewPassword.setEnabled(false);
                    txtNewPassword.setText(null);
                    txtCheckPassword.setEnabled(false);
                    txtCheckPassword.setText(null);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void cancelar(View view)
    {
        Intent i = new Intent(EditUserActivity.this, MapsActivity.class);
        startActivity(i);
        finish();
    }

    private boolean validarEmail(String email)
    {
        Pattern pattern= Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();

    }

    public void actualizar(View view)
    {
        boolean cbpass,cbuser;
        cbuser=cbUser.isChecked();
        cbpass=cbPass.isChecked();
        String usuario=txtUser.getText().toString();
        String newUsuario=txtNewUser.getText().toString();
        String contraseñaActual=txtPasswordA.getText().toString();
        String nuevaContra=txtNewPassword.getText().toString();
        String checkNuevaContra=txtCheckPassword.getText().toString();
        user.setEmail(newUsuario);
        if(cbuser==true && cbpass==true)
        {
            if(!validarEmail(txtNewUser.getText().toString()))
            {
                txtNewUser.setError("Ingrese un email valido!");
            }
            else if(txtNewPassword.getText().toString()==null)
            {
                txtNewPassword.setError("Ingrese una contraseña!");
            }
            else if(txtCheckPassword.getText().toString()==null)
            {
                txtCheckPassword.setError("Ingrese nuevamente la contraseña!");
            }
            else if(!contraseñaActual.equals(contraseña))
            {
                txtPasswordA.setError("Ingrese la contraseña actual!");
            }
            else if(!nuevaContra.equals(checkNuevaContra))
            {
                txtCheckPassword.setError("conraseña no coincide!");
            }
            else if(user.validarCorreo())
            {
                txtNewUser.setError("El correo ya se encuentra registrado!");
            }
            else
            {
                user.setEmail(newUsuario);
                user.setPassword(nuevaContra);
                if(user.actulizarDatos(usuario))
                {
                    txtUser.setText(session.usuarioActivo());
                    txtPassword.setText(user.consultarPassword(session.usuarioActivo()));
                    Toast.makeText(getApplicationContext(),"Actualizado con exito!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No se pudo actualizar!",Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if(cbuser)
        {
            if(!validarEmail(txtNewUser.getText().toString()))
            {
                txtNewUser.setError("Ingrese un email valido!");
            }
            else if(!contraseñaActual.equals(contraseña))
            {
                txtPasswordA.setError("Ingrese la contraseña actual!");
            }
            else if(!nuevaContra.equals(checkNuevaContra))
            {
                txtCheckPassword.setError("conraseña no coincide!");
            }
            else if(user.validarCorreo())
            {
                txtNewUser.setError("El correo ya se encuentra registrado!");
            }
            else
            {
                user.setEmail(newUsuario);
                if(user.actulizarEmail(usuario))
                {
                    txtUser.setText(session.usuarioActivo());
                    txtPassword.setText(user.consultarPassword(session.usuarioActivo()));
                    Toast.makeText(getApplicationContext(),"Actualizado con exito!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No se pudo actualizar!",Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if(cbpass)
        {
            if(txtNewPassword.getText().toString()==null)
            {
                txtNewPassword.setError("Ingrese una contraseña!");
            }
            else if(txtCheckPassword.getText().toString()==null)
            {
                txtCheckPassword.setError("Ingrese nuevamente la contraseña!");
            }
            else if(!contraseñaActual.equals(contraseña))
            {
                txtPasswordA.setError("Ingrese la contraseña actual!");
            }
            else if(!nuevaContra.equals(checkNuevaContra))
            {
                txtCheckPassword.setError("conraseña no coincide!");
            }
            else
            {
                user.setPassword(nuevaContra);
                if(user.actulizarPassword(usuario))
                {
                    txtUser.setText(session.usuarioActivo());
                    txtPassword.setText(user.consultarPassword(session.usuarioActivo()));
                    Toast.makeText(getApplicationContext(),"Actualizado con exito!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No se pudo actualizar!",Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    public void desactivar(View view)
    {
        String usuario=txtUser.getText().toString();
        if(user.desactivarCuenta(usuario))
        {
            Toast.makeText(getApplicationContext(),"Gracias por usar la aplicacion!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(EditUserActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Lo sentimos no se pudo desactivar por el momento!",Toast.LENGTH_SHORT).show();
        }
    }


}
