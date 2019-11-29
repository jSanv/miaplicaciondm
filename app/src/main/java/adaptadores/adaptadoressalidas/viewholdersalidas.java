package adaptadores.adaptadoressalidas;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miaplicaciondm.R;

public class viewholdersalidas extends RecyclerView.ViewHolder {

    // declaracion de variables
    private TextView lblnombre;
    private TextView lblfechavencimiento;
    private TextView lblcodigo;
    private TextView lblprecio;
    private TextView lblcantidad;
    private TextView lblcorrelativo;
    private TextView lblfechasalida;
    private TextView lblsalidapreciounitario;


    public viewholdersalidas(@NonNull View itemView) {
        super(itemView);
        this.lblnombre=((itemView.findViewById(R.id.lblsalidanombre)));
        this.lblfechavencimiento=((itemView.findViewById(R.id.lblsalidafechavencimiento)));
        this.lblcodigo=((itemView.findViewById(R.id.lblsalidacodigo)));
        this.lblprecio=((itemView.findViewById(R.id.lblsalidaprecio)));
        this.lblcantidad=((itemView.findViewById(R.id.lblsalidacantidad)));
        this.lblcorrelativo=((itemView.findViewById(R.id.lblsalidacorrelativo)));
        this.lblfechasalida=((itemView.findViewById(R.id.lblsalidafechasalida)));
        this.lblsalidapreciounitario=((itemView.findViewById(R.id.lblsalidapreciounitario)));
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

    public TextView getLblcorrelativo() {
        return lblcorrelativo;
    }

    public TextView getLblfechasalida() {
        return lblfechasalida;
    }

    public TextView getLblsalidapreciounitario() {
        return lblsalidapreciounitario;
    }
}
