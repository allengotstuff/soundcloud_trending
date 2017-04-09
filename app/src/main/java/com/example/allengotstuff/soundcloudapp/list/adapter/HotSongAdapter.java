package com.example.allengotstuff.soundcloudapp.list.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allengotstuff.soundcloudapp.R;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.list.holder.HotSongHolder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;

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

        if(myTrack.getArtwork_url()!=null|| !TextUtils.isEmpty(myTrack.getArtwork_url())){
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(myTrack.getArtwork_url())
                    .setAutoPlayAnimations(true)
                    .build();
            holder.artImage.setController(controller);
        }
    }

    @Override
    public int getItemCount() {
        return myTrackList.size();
    }


   public static class ItemDecoration extends RecyclerView.ItemDecoration {

        private int spacing;

        public ItemDecoration(int space) {
            spacing = space;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            outRect.set(spacing, spacing, spacing , spacing);
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            super.getItemOffsets(outRect, itemPosition, parent);
        }
    }
}
