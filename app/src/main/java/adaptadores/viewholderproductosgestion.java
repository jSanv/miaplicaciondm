package adaptadores;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miaplicaciondm.R;

public class viewholderproductosgestion extends RecyclerView.ViewHolder {
    // declaracion de variables
    private TextView lblnombre;
    private TextView lblfechavencimiento;
    private TextView lblcodigo;
    private TextView lblprecio;
    private TextView lblcantidad;
    private TextView lblcorrelativo;


    public viewholderproductosgestion(@NonNull View itemView) {
        super(itemView);
        this.lblnombre=((itemView.findViewById(R.id.lblnombre)));
        this.lblfechavencimiento=((itemView.findViewById(R.id.lblfechavencimiento)));
        this.lblcodigo=((itemView.findViewById(R.id.lblcodigo)));
        this.lblprecio=((itemView.findViewById(R.id.lblprecio)));
        this.lblcantidad=((itemView.findViewById(R.id.lblcantidad)));
        this.lblcorrelativo=((itemView.findViewById(R.id.lblcorrelativo)));

    }

    public TextView getLblcorrelativo() {
        return lblcorrelativo;
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
