package com.example.felix.androidtesis.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.felix.androidtesis.Conexion;
import com.example.felix.androidtesis.modelo.Paquete;
import com.example.felix.androidtesis.R;

import java.util.ArrayList;

/**
 * Created by saleventa on 1/17/17.
 */

public class PaquetesAdapter extends RecyclerView.Adapter<PaquetesAdapter.PaqueteVH> {

    private ArrayList<Paquete> mPaquetes;
    private Context mContext;
    private OnListFragmentInteractionListener mListener;


    public PaquetesAdapter(@NonNull ArrayList<Paquete> mPaquetes, @NonNull Context mContext, @NonNull OnListFragmentInteractionListener mListener) {
        this.mPaquetes = mPaquetes;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public PaqueteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.paquete_row_layout, parent, false);
        return new PaqueteVH(itemView);
    }

    @Override
    public void onBindViewHolder(PaqueteVH holder, int position) {

        final Paquete paquete = mPaquetes.get(position);

        if (paquete != null) {

            holder.tvTitulo.setText(paquete.getTitulo());

            if (paquete.getDisponible() > 1) {
                holder.tvDisponiblidad.setText(paquete.getDisponible() + " disponibles");
            } else {
                holder.tvDisponiblidad.setText("Ultimo disponible");
            }

            holder.tvPrecio.setText(paquete.getPrecio() + " Bs.F");


            if (paquete.getFotos() != null && paquete.getFotos().size() > 0) {
                Glide.with(mContext)
                        .load(Conexion.getConexion()+"uploads/" + paquete.getFotos().get(0).getFoto())
                        .into(holder.ivFoto);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onListFragmentInteraction(paquete);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mPaquetes.size();
    }

    public class PaqueteVH extends RecyclerView.ViewHolder {

        public TextView tvTitulo;
        public TextView tvDisponiblidad;
        public TextView tvPrecio;

        public ImageView ivFoto;

        public PaqueteVH(View itemView) {
            super(itemView);

            tvTitulo = (TextView) itemView.findViewById(R.id.tv_titulo);
            tvDisponiblidad = (TextView) itemView.findViewById(R.id.tv_disponiblidad);
            tvPrecio = (TextView) itemView.findViewById(R.id.tv_precio);

            ivFoto = (ImageView) itemView.findViewById(R.id.iv_imagen);
        }
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Paquete paquete);
    }


}
