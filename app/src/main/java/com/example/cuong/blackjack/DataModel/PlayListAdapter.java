package com.example.cuong.blackjack.DataModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cuong.blackjack.R;

import java.util.List;

/**
 * Created by Cuong on 5/15/2018.
 */

public class PlayListAdapter extends ArrayAdapter<PlayList> {

    private Context context;
    private int resource;
    private List<PlayList> objects;

    public PlayListAdapter(@NonNull Context context, int resource, List<PlayList> objects) {
        super(context, resource, objects);
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

        TextView tvAPlayListName=convertView.findViewById(R.id.tvAPlayListName);
        tvAPlayListName.setText(objects.get(position).getName().toString().replace("-"," "));
        return convertView;
    }
}
