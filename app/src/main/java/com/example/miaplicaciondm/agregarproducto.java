package com.example.miaplicaciondm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import sqllite.conexionsqllite;


//variables estaticas
//variables de la clave de configuracion


public class agregarproducto extends AppCompatActivity implements View.OnClickListener {

    EditText codigo, precio, cantidad, nombre, fecha;

    Button agregar;

    //scanner
    private ZXingScannerView vistascanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarproducto);




// le damos la identificacion a los items del layout
        this.codigo = findViewById(R.id.lblcodigo);
        this.precio = findViewById(R.id.lblprecio);
        this.cantidad = findViewById(R.id.lblcantidad);
        this.nombre = findViewById(R.id.lblnombre);
        this.fecha = findViewById(R.id.txtvencimiento);
        this.agregar = findViewById(R.id.btnagregar);

        // recuperamos el codigo de la activity anterior
        Bundle receptor = getIntent().getExtras();

        //vemos si recibe variables
        if(receptor == null)
        {

        }
        else
        {
            String codigos = receptor.getString("codigo");
            codigo.append(codigos);
        }


    }



    @Override
    public void onClick(View view) {
        // para validar que los campos no esten vacios, convertimos a String
        String valcodigo = codigo.getText().toString();
        String valprecio =  precio.getText().toString();
        String valcantidad = cantidad.getText().toString();
        String valnombre = nombre.getText().toString();

        switch (view.getId())
        {
            case R.id.btnagregar:
            {

                if(valcodigo.isEmpty() || valcantidad.isEmpty()  || valnombre.isEmpty()  || valprecio.isEmpty())
                {
                    Toast.makeText(this,"Digite en todos los campos", Toast.LENGTH_LONG).show();

                }
                else
                {

                    //asignamos la conexion
                    conexionsqllite admin = new conexionsqllite(this, "dbobjetos", null, 1);
                    //abrimos la base de datos
                    SQLiteDatabase bd = admin.getWritableDatabase();

                    ContentValues registro = new ContentValues();

                    registro.put("codigo", valcodigo);
                    registro.put("nombre", valnombre);
                    registro.put("precio", valprecio);
                    registro.put("fechacaducidad", fecha.getText().toString());
                    registro.put("cantidad", valcantidad);

                    //insertamos en la base de datos
                    bd.insert("productos", null, registro);
                    //cerramos la base de datos
                    bd.close();

                    //uso de supertoast
                    //usando el super toast de bienvenido
                    SuperActivityToast.create( agregarproducto.this, new Style(), Style.TYPE_PROGRESS_BAR)
                            // .setButtonText("UNDO")
                            .setButtonIconResource(R.mipmap.ic_launcher)
                            .setOnButtonClickListener("good_tag_name", null, null)
                            .setProgressBarColor(Color.WHITE)
                            .setText("Insertando datos...")
                            .setDuration(Style.DURATION_SHORT)
                            .setFrame(Style.FRAME_KITKAT)
                            .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE_GREY))
                            .setAnimations(Style.ANIMATIONS_SCALE).show();

                    Toast.makeText(this, "Datos insertados", Toast.LENGTH_SHORT).show();
                    finish();
                }

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
            codigo = (EditText) findViewById(R.id.txteditcodigo);
            codigo.setText(dato);
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
                codigo.setText(result.getContents().toString());
            }

        }

        else
        {
            super.onActivityResult(requestCode, resultCode,data);

        }

    }

}
