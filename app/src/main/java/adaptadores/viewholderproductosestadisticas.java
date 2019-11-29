package adaptadores;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miaplicaciondm.R;

public class viewholderproductosestadisticas extends RecyclerView.ViewHolder {

    // declaracion de variables
    private TextView lblnombre;
    private TextView lblfechavencimiento;
    private TextView lblcodigo;
    private TextView lblprecio;
    private TextView lblcantidad;


    public viewholderproductosestadisticas(@NonNull View itemView) {
        super(itemView);
        this.lblnombre=((itemView.findViewById(R.id.lblnombre)));
        this.lblfechavencimiento=((itemView.findViewById(R.id.lblfechavencimiento)));
        this.lblcodigo=((itemView.findViewById(R.id.lblcodigo)));
        this.lblprecio=((itemView.findViewById(R.id.lblprecio)));
        this.lblcantidad=((itemView.findViewById(R.id.lblcantidad)));

    }


    public TextView getLblnombre() {
        return lblnombre;
    }


    public TextView getLblfechavencimiento() {
        return lblfechavencimiento;
    }


    public TextView getLblcodigo() {
        return lblcodigo;
    }


    public TextView getLblprecio() {
        return lblprecio;
    }


    public TextView getLblcantidad() {
        return lblcantidad;
    }

}
