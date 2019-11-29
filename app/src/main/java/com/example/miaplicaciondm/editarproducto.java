package com.example.miaplicaciondm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;

//import static  com.example.miaplicaciondm.MainActivity.listaproductos;

import adaptadores.adaptadorgestiondeproductos;
import adaptadores.adaptadorproductos;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import  objetos.productos;

//sqllite
import sqllite.conexionsqllite;

public class editarproducto extends AppCompatActivity implements View.OnClickListener {

    EditText txtcodigo, txtnombre, txtprecio, txtvencimiento, txtcantidad;
    Button confirmar, eliminar;
    int posicion;

    //scanner
    private ZXingScannerView vistascanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarproducto);
        txtcodigo = findViewById(R.id.txteditcodigo);
        txtnombre = findViewById(R.id.txteditnombre);
        txtprecio = findViewById(R.id.txteditprecio);
        txtvencimiento = findViewById(R.id.txteditvencimiento);
        txtcantidad = findViewById(R.id.txteditcantidad);
        confirmar = findViewById(R.id.btneditagregar);
        eliminar = findViewById(R.id.btnediteliminar);

        // recuperamos el codigo de la activity anterior
        Bundle receptor = getIntent().getExtras();

        //vemos si recibe variables
        if(receptor == null)
        {

        }
        else
        {
            String codigo = receptor.getString("codigo");
            String cantidad = receptor.getString("cantidad");
            String vencimiento = receptor.getString("vencimiento");
            String precio = receptor.getString("precio");
            String nombre = receptor.getString("nombre");
           String posici = receptor.getString("position");

            txtcodigo.setText(codigo);
            txtcantidad.setText(cantidad);
            txtvencimiento.setText(vencimiento);
            txtprecio.setText(precio);
            txtnombre.setText(nombre);
            posicion = Integer.parseInt(posici);
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btneditagregar:
            {

                //asignamos la conexion
                conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
                //abrimos la base de datos
                SQLiteDatabase bd = admin.getWritableDatabase();

                ContentValues registro = new ContentValues();

                registro.put("codigo", txtcodigo.getText().toString());
                registro.put("nombre", txtnombre.getText().toString());
                registro.put("precio", Double.parseDouble(txtprecio.getText().toString()));
                registro.put("fechacaducidad", txtvencimiento.getText().toString());
                registro.put("cantidad", Integer.parseInt(txtcantidad.getText().toString()));


                // int cantidad = bd.update("productos", registro, "codigo= 1"+ codigo, null);
                //comando de actualizar por defecto de SQL
                int cantidad = bd.update("productos", registro, "codigo="+ txtcodigo.getText().toString(), null);
                //cerramos la base de datos
                bd.close();

                //si el comando da 1 es TRUE y elimino
                if(cantidad==1){
                    Toast.makeText(this,"Modificacion exitosa", Toast.LENGTH_SHORT).show();
                }else {
                    // no existe en la base de datos por quedarse en 0 que es FALSE
                    Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
                }

                finish();
                break;
            }

            case R.id.btnediteliminar:
            {
                //asignamos la conexion
                conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
                //abrimos la base de datos
                SQLiteDatabase bd = admin.getWritableDatabase();

                if(!txtcodigo.getText().toString().isEmpty()){
                    //comando de SQLLITE para eliminar donde el CODIGO = codigo(de la caja de texto)
                    int cantidad =  bd.delete("productos", "codigo= " + txtcodigo.getText().toString(), null);
                    //cerramos la base de datos
                    bd.close();

                    // si la eliminacion es 1 es por que es TRUE y se ha eliminado
                    if(cantidad == 1){
                        Toast.makeText(this, "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        // no existe el codigo en la base de datos
                        Toast.makeText(this, "El codigo no existe", Toast.LENGTH_SHORT).show();
                    }

                    // no esta ingresando codigo en la caja de texto
                }else{
                    Toast.makeText(this, "Introduce el codigo", Toast.LENGTH_SHORT).show();
                }
                finish();

                break;
            }
        }



        }



    //***********************************************************************************************
    //eventos para el scanner


    class zxlsacnner implements ZXingScannerView.ResultHandler
    {


        @Override
        public void handleResult(Result result) {
            String dato = result.getText();
            setContentView(R.layout.activity_agregar);
            vistascanner.stopCamera();
            txtcodigo = (EditText) findViewById(R.id.txteditcodigo);
            txtcodigo.setText(dato);
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
                txtcodigo.setText(result.getContents().toString());
            }

        }

        else
        {
            super.onActivityResult(requestCode, resultCode,data);

        }

    }

}
