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
import com.example.felix.androidtesis.R;
import com.example.felix.androidtesis.modelo.Hotel;

import java.util.ArrayList;

/**
 * Created by saleventa on 2/3/17.
 */
public class HotelesAdapter extends RecyclerView.Adapter<HotelesAdapter.HotelVH> {

    private ArrayList<Hotel> mHoteles;
    private Context mContext;
    private HotelesAdapter.OnListFragmentInteractionListener mListener;


    public HotelesAdapter(@NonNull ArrayList<Hotel> mHoteles, @NonNull Context mContext, @NonNull HotelesAdapter.OnListFragmentInteractionListener mListener) {
        this.mHoteles = mHoteles;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public HotelesAdapter.HotelVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.paquete_row_layout, parent, false);
        return new HotelesAdapter.HotelVH(itemView);
    }

    @Override
    public void onBindViewHolder(HotelesAdapter.HotelVH holder, int position) {

        final Hotel hotel = mHoteles.get(position);

        if (hotel != null) {

            holder.tvTitulo.setText(hotel.getHotel());

            holder.tvDisponiblidad.setText(hotel.getDescripcion() != null
                    && hotel.getDescripcion().length() > 0 ? hotel.getDescripcion().substring(0, 18) + "..." : "");
//            if (hotel.getDisponible() > 1) {
//                holder.tvDisponiblidad.setText(hotel.getDisponible() + " disponibles");
//            } else {
//                holder.tvDisponiblidad.setText("Ultimo disponible");
//            }

            holder.tvPrecio.setText(hotel.getEstado().getEstado());


            if (hotel.getFotos() != null && hotel.getFotos().size() > 0) {
                Glide.with(mContext)
                        .load(Conexion.getConexion()+"uploads/hoteles/" + hotel.getFotos().get(0).getFoto())
                        .into(holder.ivFoto);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onListFragmentInteraction(hotel);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mHoteles.size();
    }

    public class HotelVH extends RecyclerView.ViewHolder {

        public TextView tvTitulo;
        public TextView tvDisponiblidad;
        public TextView tvPrecio;

        public ImageView ivFoto;

        public HotelVH(View itemView) {
            super(itemView);

            tvTitulo = (TextView) itemView.findViewById(R.id.tv_titulo);
            tvDisponiblidad = (TextView) itemView.findViewById(R.id.tv_disponiblidad);
            tvPrecio = (TextView) itemView.findViewById(R.id.tv_precio);

            ivFoto = (ImageView) itemView.findViewById(R.id.iv_imagen);
        }
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Hotel hotel);
    }
}
