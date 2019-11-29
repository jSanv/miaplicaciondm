package com.example.miaplicaciondm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

//import static  com.example.miaplicaciondm.MainActivity.listaproductos;

import adaptadores.adaptadorgestiondeproductos;
import adaptadores.adaptadrecyclerview;
import adaptadores.adaptadorrecyclergestiondeproductos;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import  objetos.productos;
import sqllite.conexionsqllite;

//variables estaticas
//variables de la clave de configuracion

import static  com.example.miaplicaciondm.preferencias.CLAVE_ORDEN;
import static  com.example.miaplicaciondm.preferencias.clave_datos;
import static com.example.miaplicaciondm.MainActivity.gestionaccion;

public class gestiondeproductos extends AppCompatActivity implements View.OnClickListener {

    RecyclerView lista;
    EditText busqueda;
   private static List<productos> datasource;

    // llamada de cargar datos
    private SharedPreferences confiduracion;


    LinearLayoutManager manager;
    adaptadorrecyclergestiondeproductos adaptadorobjeto;

    //para el escaner
    //scanner
    private ZXingScannerView vistascanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestiondeproductos);

        //accediendo a los datos del app
        this.confiduracion= this.getSharedPreferences(clave_datos,MODE_PRIVATE);


        busqueda = findViewById(R.id.txtbuscar);





        lista = findViewById(R.id.listagestionproductos);
        datasource = new ArrayList<>();
        datasource = leer();

        this.manager = new LinearLayoutManager(this);



        //adaptamos la informacion
        this.adaptadorobjeto = new adaptadorrecyclergestiondeproductos(this.datasource);




        //Establecemos las propiedades
        this.lista.setHasFixedSize(true);
        this.lista.setLayoutManager(this.manager);

        // adaptamos los datos
        this.lista.setAdapter(this.adaptadorobjeto);

        // este es el listener para seleccionar el objeto que esta en lista
        adaptadorobjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

               // Toast.makeText(gestiondeproductos.this, "Seleccionado "+datasource.get(lista.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(gestiondeproductos.this).setTitle("¡Aviso!").setMessage("¿Desea editar el registro \"" + datasource.get(lista.getChildAdapterPosition(view)).getNombre() + "\"?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Eliminamos lo seleccionado
                                iraedicion(lista.getChildAdapterPosition(view));

                            }
                        }).show();
            }
        });

    }

    //aqui hacemos la navegacion a la activity que necesitamos ir
    void iraedicion(int position)
    {
        if(gestionaccion==1)
        {
            Intent as = new Intent(this, editarproducto.class);
            as.putExtra("codigo", datasource.get(position).getCodigo());
            as.putExtra("nombre", datasource.get(position).getNombre());
            as.putExtra("cantidad", Integer.toString(datasource.get(position).getCantidad()));
            as.putExtra("vencimiento", datasource.get(position).getFechacaducidad());
            as.putExtra("precio", Double.toString(datasource.get(position).getPrecio()));
            as.putExtra("position", Integer.toString(position));
            startActivity(as);
        }
        else if(gestionaccion==2)
        {
            Intent as = new Intent(this, salidadeproductos.class);
            as.putExtra("codigo", datasource.get(position).getCodigo());
            as.putExtra("nombre", datasource.get(position).getNombre());
            as.putExtra("cantidad", Integer.toString(datasource.get(position).getCantidad()));
            as.putExtra("vencimiento", datasource.get(position).getFechacaducidad());
            as.putExtra("precio", Double.toString(datasource.get(position).getPrecio()));
            as.putExtra("position", Integer.toString(position));
            startActivity(as);
            finish();
        }


    }

    void busqueda(String buscar)
    {
        //variable para mostrar el mensaje encontrado
        Boolean encontrado = false;
        // por nombre
        for(int i = 0; i< datasource.size(); i++)
        {


            //posiciondeencuentro++;
            if(buscar.equals(datasource.get(i).getNombre()))
            {
                //asignamos los datos que vienen de la lista
            actualizardatos(i);

                Toast.makeText(this, "El nombre se encuentra en la posicion: "+(i+1), Toast.LENGTH_SHORT).show();
                encontrado = true;
            }
            else
            {

                if(buscar.equals(datasource.get(i).getCodigo()))
                {
                    actualizardatos(i);
                    Toast.makeText(this, "El codigo se encuentra en la posicion: "+(i+1), Toast.LENGTH_SHORT).show();
                    encontrado=true;
                }
                else if(buscar.equals(""))
                {
                    datasource.clear();
                   datasource = leer();
                    // adaptamos los datos
                    this.lista.setAdapter(this.adaptadorobjeto);
                    // este es el listener para seleccionar el objeto que esta en lista
                    adaptadorobjeto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {

                            // Toast.makeText(gestiondeproductos.this, "Seleccionado "+datasource.get(lista.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();

                            new AlertDialog.Builder(gestiondeproductos.this).setTitle("¡Aviso!").setMessage("¿Desea editar el registro \"" + datasource.get(lista.getChildAdapterPosition(view)).getNombre() + "\"?")
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Eliminamos lo seleccionado
                                            iraedicion(lista.getChildAdapterPosition(view));

                                        }
                                    }).show();
                        }
                    });
                }

            }
        }
        if(encontrado== false)
        {

            Toast.makeText(this, "No se encontro "+buscar, Toast.LENGTH_SHORT).show();
        }
        encontrado=true;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnbusqueda:
            {
                busqueda(busqueda.getText().toString());
                break;
            }
        }

    }


    // asignando los valores que tengo registrado en SQL para un LIST
    public List<productos> leer()
    {
        // consulta sql
        String sql;
        //validamos la opcion desde preferencias para el orden

           if(confiduracion.getInt(CLAVE_ORDEN,0)==1)
        {
            //ordenar por codigo
             sql = "SELECT * FROM productos order by codigo asc";
        }
        else
        {
            //ordenar por nombre
            sql = "SELECT * FROM productos order by nombre asc";
        }

        //abrir base de datos en dbobjetos
        conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
        //abrir conexion
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor cursor = bd.rawQuery(sql,null);

        if(cursor.moveToFirst())
        {
            do {


                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                Double precio =  cursor.getDouble(cursor.getColumnIndex("precio"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int cantidad = cursor.getInt(cursor.getColumnIndex("cantidad"));
                String fecha = cursor.getString(cursor.getColumnIndex("fechacaducidad"));

                datasource.add(new productos(nombre,fecha,cantidad,precio,codigo));
                adaptar();

            } while (cursor.moveToNext());
        }

        cursor.close();
        bd.close();
        return datasource;

    }

    public void onResume()
    {
        datasource = new ArrayList<>();
        datasource = leer();
        super.onResume();
    }

    //para adaptar la informacion cuando se busca
    private void adaptar()
    {
        this.adaptadorobjeto = new adaptadorrecyclergestiondeproductos(this.datasource);




        //Establecemos las propiedades
        this.lista.setHasFixedSize(true);
        this.lista.setLayoutManager(this.manager);

        // adaptamos los datos
        this.lista.setAdapter(this.adaptadorobjeto);

        // este es el listener para seleccionar el objeto que esta en lista
        adaptadorobjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                // Toast.makeText(gestiondeproductos.this, "Seleccionado "+datasource.get(lista.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(gestiondeproductos.this).setTitle("¡Aviso!").setMessage("¿Desea editar el registro \"" + datasource.get(lista.getChildAdapterPosition(view)).getNombre() + "\"?")
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Eliminamos lo seleccionado
                                iraedicion(lista.getChildAdapterPosition(view));

                            }
                        }).show();
            }
        });
    }


//////////////////eventos para el escaner////////////////////////////////

    class zxlsacnner implements ZXingScannerView.ResultHandler
    {


        @Override
        public void handleResult(Result result) {
            String dato = result.getText();
            setContentView(R.layout.activity_agregar);
            vistascanner.stopCamera();
            busqueda = (EditText) findViewById(R.id.txteditcodigo);
            busqueda.setText(dato);
        }
    }


    public void escaner2(View view)
    {
        IntentIntegrator intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        intent.setPrompt("Escanenado codigo");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!= null)
        {

            if(result.getContents()== null)
            {
                Toast.makeText(this, "Escaner Cancelado",Toast.LENGTH_LONG).show();

            }
            else
            {
                busqueda.setText(result.getContents().toString());
            }

        }

        else
        {
            super.onActivityResult(requestCode, resultCode,data);

        }

    }


    void actualizardatos (int i)
    {
        //para daptar y mostrar solo el resultado
        productos a = new productos();
        a.setFechacaducidad(datasource.get(i).getFechacaducidad());
        a.setCantidad(datasource.get(i).getCantidad());
        a.setPrecio(datasource.get(i).getPrecio());
        a.setCodigo(datasource.get(i).getCodigo());
        a.setNombre(datasource.get(i).getNombre());

        //ahora ponemos el resultado en la lista de datos
        datasource.clear();
        datasource.add(a);
        adaptadorobjeto.notifyDataSetChanged();

    }


}
