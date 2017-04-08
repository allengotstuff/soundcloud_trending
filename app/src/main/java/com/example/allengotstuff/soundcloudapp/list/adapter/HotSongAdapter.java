package com.example.allengotstuff.soundcloudapp.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allengotstuff.soundcloudapp.R;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.list.holder.HotSongHolder;

import java.util.List;

/**
 * Created by allengotstuff on 4/8/2017.
 */

public class HotSongAdapter extends RecyclerView.Adapter<HotSongHolder>  {

    private List<Track> myTrackList;

    public HotSongAdapter (List datalist){
        myTrackList = datalist;
    }


//    public void updateList( ){
//
//        notifyDataSetChanged();
//    }


    @Override
    public HotSongHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hotsongholder, parent, false);

        HotSongHolder vh = new HotSongHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(HotSongHolder holder, int position) {

        Track myTrack = myTrackList.get(position);
        holder.title.setText(myTrack.getTitle());
    }

    @Override
    public int getItemCount() {
        return myTrackList.size();
    }
}
