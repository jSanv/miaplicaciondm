package com.example.miaplicaciondm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

//variables estaticas
//variables de la clave de configuracion


public class Agregar extends AppCompatActivity implements View.OnClickListener {

    Button captura;
    EditText codigo;
    private ZXingScannerView vistascanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        this.captura = findViewById(R.id.btncaptura);
        this.codigo = findViewById(R.id.lblcodigo);
    }

    class zxlsacnner implements ZXingScannerView.ResultHandler
    {


        @Override
        public void handleResult(Result result) {
            String dato = result.getText();
            setContentView(R.layout.activity_agregar);
            vistascanner.stopCamera();
            codigo = (EditText) findViewById(R.id.lblcodigo);
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnagregar:
            {

                Intent hola = new Intent(this, agregarproducto.class);
                hola.putExtra("codigo", codigo.getText().toString());
                startActivity(hola);
                break;
            }

            case R.id.btncaptura:
            {
                escaner2(view);

            }
            case R.id.btnregresarprincipal:
            {
                Intent as = new Intent(this, MainActivity.class);
                startActivity(as);
                break;
            }
        }

    }


}
