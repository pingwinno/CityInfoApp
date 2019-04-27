package com.pingwinno.cityinfoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pingwinno.cityinfoapp.models.Country;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Country> countries;
    private View.OnClickListener onItemClickListener;

    CountryAdapter(Context context, List<Country> countries) {
        this.countries = countries;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {
        Country country = countries.get(position);
        //  holder.imageView.setImageURI(country.getImage());
        holder.nameView.setText(country.getCountryName());

    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // final ImageView imageView;
        final TextView nameView;

        ViewHolder(View view) {
            super(view);
            //  imageView = view.findViewById(R.id.image);
            nameView = view.findViewById(R.id.country_name);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }
}
