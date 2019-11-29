package com.example.miaplicaciondm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sqllite.conexionsqllite;

public class nuevousuario extends AppCompatActivity {


    EditText usuario, credencial, repeticion;
    Button procesar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevousuario);
        usuario = findViewById(R.id.txtnuevousuario);
        credencial = findViewById(R.id.txtnuevacredencial);
        procesar = findViewById(R.id.btnnuevoprocesar);
        repeticion = findViewById(R.id.txtnuevarepetircredencial);


    }

    public void onclick(View v )
    {
        if(!usuario.getText().toString().isEmpty() && !credencial.getText().toString().isEmpty() && !repeticion.getText().toString().isEmpty() )
        {
            if (credencial.getText().toString().equals(repeticion.getText().toString()))
            {
                crearusuario();
                Intent as = new Intent( this, login.class);
                startActivity(as);
                finish();
            }
            else
            {
                Toast.makeText(this, "Credenciales incompatibles", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "llene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



    void crearusuario()
    {
        //asignamos la conexion
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrimos la base de datos
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();

        registro.put("usuario", usuario.getText().toString());
        registro.put("credencial", credencial.getText().toString());

        //insertamos en la base de datos
        bd.insert("usuarios", null, registro);
        //cerramos la base de datos
        bd.close();

        //uso de supertoast


        Toast.makeText(this, "Usuario Agregado", Toast.LENGTH_SHORT).show();

    }
}
