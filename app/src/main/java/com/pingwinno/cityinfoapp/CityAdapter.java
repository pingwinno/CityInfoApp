package com.pingwinno.cityinfoapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pingwinno.cityinfoapp.models.City;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<City> cities;
    private View.OnClickListener onItemClickListener;

    CityAdapter(Context context, List<City> cities) {
        this.cities = cities;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, int position) {
        City city = cities.get(position);
        //  holder.imageView.setImageURI(city.getImage());
        holder.nameView.setText(city.getCityName());

    }

    @Override
    public int getItemCount() {
        return cities.size();
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
