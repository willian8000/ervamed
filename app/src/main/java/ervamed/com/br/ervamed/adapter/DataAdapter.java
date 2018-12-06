package ervamed.com.br.ervamed.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.lang.reflect.Array;
import java.util.ArrayList;

import ervamed.com.br.ervamed.ErvaDetalhes;
import ervamed.com.br.ervamed.MainActivity;
import ervamed.com.br.ervamed.R;
import ervamed.com.br.ervamed.entity.Erva;
import ervamed.com.br.ervamed.model.ModelImagem;
import ervamed.com.br.ervamed.model.ModelImagem_Table;
import ervamed.com.br.ervamed.model.ModelPlanta;

import static android.support.constraint.Constraints.TAG;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {


    private ArrayList<ModelPlanta> ervas;
    private ArrayList<ModelPlanta> ervasFull;
    private ArrayList<ModelImagem> listImgErva;

    private Context mContext;

    public DataAdapter(Context context, ArrayList<ModelPlanta> android) {
        this.mContext = context;
        this.ervas = android;
        ervasFull = new ArrayList<>(ervas);
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final ModelPlanta ervaItem = ervas.get(position);

        holder.nomeCientifico.setText(ervas.get(position).getNome_cientifico());
        holder.nomesPopuplares.setText(ervas.get(position).getNomes_populares().replace('\n', ' '));
        //holder.imageErva.setImageResource(R.drawable.pimenta);

        listImgErva = (ArrayList<ModelImagem>) new SQLite().select(ModelImagem_Table.url).from(ModelImagem.class).where(ModelImagem_Table.id_erva_id.eq(ervaItem.getId())).queryList();
        Log.d("URL_PRINC", listImgErva.get(0).getUrl());

        Glide.with(mContext).load(listImgErva.get(0).getUrl()).into(holder.imageErva);
        //img_erva

        //holder.imageErva.setTransitionName("transition" + position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, String.valueOf(holder.getAdapterPosition()));

                Bundle bundle =  new Bundle();
                bundle.putString("nomeCientifico", ervaItem.getNome_cientifico().replace('\n', ' ').replace("  ","") );
                bundle.putString("nomePopulares", ervaItem.getNomes_populares().replace('\n', ' ').replace("  ","") );
                bundle.putString("FinsMedicinais", ervaItem.getFins_medicinais().replace('\n', ' ').replace("  ","") );
                bundle.putString("FormasUso", ervaItem.getFormas_de_uso().replace('\n', ' ').replace("  ","") );
                bundle.putString("RiscosUso", ervaItem.getRiscos_de_uso().replace('\n', ' ').replace("  ","") );
                bundle.putInt("ErvaID", ervaItem.getId());

                listImgErva = (ArrayList<ModelImagem>) new SQLite().select().from(ModelImagem.class).where(ModelImagem_Table.id_erva_id.eq(ervaItem.getId())).queryList();
                //bundle.putString("IMG", listImgErva.get(0).getEncodedImage() );


                //Log.d("BASE64", listImgErva.get(0).getEncodedImage());
                //Toast.makeText(mContext, listImgErva.get(0).getEncodedImage(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, ErvaDetalhes.class);
                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ervas.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ModelPlanta> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(ervasFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ModelPlanta item : ervasFull) {
                    if ((item.getNomes_populares().toLowerCase().contains(filterPattern))||(item.getNome_cientifico().toLowerCase().contains(filterPattern))) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ervas.clear();
            ervas.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeCientifico;
        private TextView nomesPopuplares;
        private ImageView imageErva;

        public ViewHolder(View itemView) {
            super(itemView);
            imageErva = itemView.findViewById(R.id.img_erva);
            nomeCientifico = itemView.findViewById(R.id.nome_cientifico);
            nomesPopuplares = itemView.findViewById(R.id.nomes_popuplares);
        }
    }
}
