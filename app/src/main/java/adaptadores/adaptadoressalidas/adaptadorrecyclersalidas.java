package adaptadores.adaptadoressalidas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miaplicaciondm.R;

import java.util.List;

import adaptadores.viewholderproductosestadisticas;
import objetos.productossalida;

public class adaptadorrecyclersalidas extends RecyclerView.Adapter<viewholdersalidas> {


    private List<productossalida> listContactos;

    public adaptadorrecyclersalidas(List<productossalida> datasources){
        //Inicializamos el datasources
        this.listContactos = datasources;
    }

    @NonNull
    @Override
    public viewholdersalidas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creamos la vista usando el Layout de plantilla
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantillasalidas,parent,false);
        viewholdersalidas vhContactos = new viewholdersalidas(v);
        return vhContactos;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholdersalidas holder, int position) {
        //Establecemos la información del datasources al ViewHolder
        //  holder.getLbldificultad().setText(this.listContactos.get(position).getDificultad());
        holder.getLblnombre().setText( this.listContactos.get(position).getNombre());

        // convertimos a String el Integer, osino da error
        holder.getLblcantidad().setText("cantidad: "+ Integer.toString( this.listContactos.get(position).getCantidad()));
        holder.getLblcodigo().setText("codigo: "+  this.listContactos.get(position).getCodigo());

        //validamos si existe fecha de vencimiento en el producto
        if(!listContactos.get(position).getFechacaducidad().equals(""))
        {
            holder.getLblfechavencimiento().setText("Vencimiento: "+  this.listContactos.get(position).getFechacaducidad());
        }
        else { holder.getLblfechavencimiento().setText("Sin fecha de vencimiento"); }

        holder.getLblprecio().setText("Precio total: "+ Double.toString( this.listContactos.get(position).getPrecio()));

        holder.getLblfechasalida().setText("salida: "+this.listContactos.get(position).getFechsalida());
        holder.getLblcorrelativo().setText("Correlativo: "+ Integer.toString( this.listContactos.get(position).getCorrelativo()));


        //precio unitario
        holder.getLblsalidapreciounitario().setText("Precio unitario: "+(Double.toString( this.listContactos.get(position).getPrecio()/ this.listContactos.get(position).getCantidad()) ));

    }

    @Override
    public int getItemCount() {
        return this.listContactos.size();
    }
}
