package com.example.allengotstuff.soundcloudapp.gethotsongs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.allengotstuff.soundcloudapp.R;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.utils.Constant;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by allengotstuff on 4/9/2017.
 */

public class SongDetailActivity extends AppCompatActivity {

    private Track myTrack;

    @BindView(R.id.draweeView_art_image)
    SimpleDraweeView draweeView_art;

    @BindView(R.id.tv_track_detail)
    TextView textview_track;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotsong_detail);
        ButterKnife.bind(this);

        initdata();
        initView();
    }

    private void initdata() {
        myTrack = getIntent().getExtras().getParcelable(Constant.DELIVERY_TRACK);
    }


    private void initView() {
        if (myTrack.getArtwork_url() != null || !TextUtils.isEmpty(myTrack.getArtwork_url())) {
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(myTrack.getArtwork_url())
                    .setAutoPlayAnimations(true)
                    .build();
            draweeView_art.setController(controller);
        }

        if (myTrack != null)
            textview_track.setText(myTrack.toString());

    }


}
