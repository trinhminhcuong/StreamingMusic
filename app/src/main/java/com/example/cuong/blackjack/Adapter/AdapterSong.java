package com.example.cuong.blackjack.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cuong.blackjack.DataModel.Song;
import com.example.cuong.blackjack.R;

import java.util.List;

/**
 * Created by Cuong on 5/16/2018.
 */

public class AdapterSong extends ArrayAdapter<Song> {
    private Context context;
    private int resource;
    private List<Song> objects;
    public AdapterSong(@NonNull Context context, int resource,List<Song> objects) {
        super(context, resource,objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView=layoutInflater.inflate(resource,null);
        }
        TextView tvANameSong=convertView.findViewById(R.id.tvASongName);
        TextView tvASinger=convertView.findViewById(R.id.tvASinger);

        tvANameSong.setText(objects.get(position).getName());
        tvASinger.setText(objects.get(position).getSinger());

        return convertView;
    }
}
