package com.pingwinno.cityinfoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pingwinno.cityinfoapp.models.Country;

import java.util.List;

public class CounntryListAdapter extends ArrayAdapter<Country> {

    private LayoutInflater inflater;
    private int layout;
    private List<Country> countries;

    public CounntryListAdapter(Context context, int resource, List<Country> countries) {
        super(context, resource, countries);
        this.countries = countries;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Country country = countries.get(position);

        viewHolder.textView.setText(country.getCountryName());

        return convertView;
    }

    private class ViewHolder {

        final TextView textView;

        ViewHolder(View view) {

            textView = view.findViewById(R.id.country_name);

        }
    }


}
