package com.example.miaplicaciondm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//clave de datos

//sqllite
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adaptadores.adaptadrecyclerview;
import objetos.productos;
import sqllite.conexionsqllite;

public class estadisticas extends AppCompatActivity {

List<productos> datasource;
RecyclerView recycler;
    LinearLayoutManager manager;
    adaptadrecyclerview adaptadorobjeto;




    //onteniendo la fecha actual
    int dia;
    int mes;
    int anio;
    String fechactual;
    Switch vencimiento;
    private  Boolean  vencidos = false ;
    TextView anuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        vencidos=false;

        //controles del switch
        vencimiento = findViewById(R.id.switchvencimiento);
        anuncio = findViewById(R.id.textView7);
        recycler = findViewById(R.id.recycler);

        Calendar fecha = Calendar.getInstance();
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        mes = (fecha.get(Calendar.MONTH)+1);
        if(mes == 13)
        {
            mes = 01;
        }
        anio = fecha.get(Calendar.YEAR);
        fechactual = dia+"/"+mes+"/"+anio;

        Toast.makeText(this, "fecha actual: "+fechactual, Toast.LENGTH_SHORT).show();



        datasource = new ArrayList<>();
        datasource = leer();


        this.manager = new LinearLayoutManager(this);


        //this.adaptadorobjeto = new adaptadrecyclerview(this.datasource);


        //Establecemos las propiedades
        this.recycler.setHasFixedSize(true);
        this.recycler.setLayoutManager(this.manager);

        // adaptamos los datos
        this.recycler.setAdapter(this.adaptadorobjeto);


    }

    // asignando los valores que tengo registrado en SQL para un LIST
    public List<productos> leer()  {
        // consulta sql
        String sql;

            //ordenar por fecha
          //  sql = "SELECT * FROM productos from fechavencimiento>"+fechactual+" desc";

        if(vencidos==false)
        {
            //no vencidos y sin vencimiento
            sql = "SELECT * FROM productos where fechacaducidad<='"+fechactual+"'";
        }
        else
        {
            sql = "SELECT * FROM productos where fechacaducidad>='"+fechactual+"'";
        }

        // java.util.Date fecha = new Date();
        //abrir base de datos en dbobjetos
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrir conexion
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor cursor = bd.rawQuery(sql,null);

        if(cursor.moveToFirst())
        {

            do{


                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                Double precio =  cursor.getDouble(cursor.getColumnIndex("precio"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                String fecha = cursor.getString(cursor.getColumnIndex("fechacaducidad"));
                datasource.add(new productos(nombre,fecha,cantidad,precio,codigo));
            } while (cursor.moveToNext());
        }

        cursor.close();
        bd.close();
        return datasource;

    }

    //control del switch
    public void onclick(View view)
    {
        if(view.getId()== R.id.switchvencimiento)
        {
            if(vencimiento.isChecked())
            {
            vencidos = false;
            anuncio.setText("Estos son los productos vencidos y los que no se vencen");
            datasource = new ArrayList<>();
            datasource = leer();
              //  adaptadorobjeto.notifyDataSetChanged();
                this.adaptadorobjeto = new adaptadrecyclerview(this.datasource);
                // adaptamos los datos
                this.recycler.setAdapter(this.adaptadorobjeto);
            }
            else if(vencimiento.isChecked()==false)
            {
              vencidos = true;
                anuncio.setText("Estos son los productos no vencidos");
                datasource = new ArrayList<>();
                datasource = leer();
                this.manager = new LinearLayoutManager(this);
               // adaptadorobjeto.notifyDataSetChanged();
                this.adaptadorobjeto = new adaptadrecyclerview(this.datasource);
                // adaptamos los datos
                this.recycler.setAdapter(this.adaptadorobjeto);

            }
        }
    }


}
