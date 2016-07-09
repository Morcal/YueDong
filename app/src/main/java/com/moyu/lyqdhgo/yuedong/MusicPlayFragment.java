package com.moyu.lyqdhgo.yuedong;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moyu.lyqdhgo.yuedong.bean.Media;
import com.moyu.lyqdhgo.yuedong.util.CommonUtils;
import com.moyu.lyqdhgo.yuedong.util.Player;
import com.moyu.lyqdhgo.yuedong.weight.PlayerDiscView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lyqdhgo on 2016/7/9.
 */

public class MusicPlayFragment extends Fragment implements View.OnClickListener {

    private static final String DAFAULT_PLAY_COVER = "http://7xrfxa.com1.z0.glb.clouddn.com/bg_play.jpg";

    @Bind(R.id.musics_player_name)
    TextView player;
    @Bind(R.id.musics_player_songer_name)
    TextView songer;
    @Bind(R.id.musics_player_current_time)
    TextView currentTime;
    @Bind(R.id.musics_player_total_time)
    TextView totalTime;
    @Bind(R.id.musics_player_disc_view)
    PlayerDiscView playerDiscView;
    @Bind(R.id.musics_player_seekbar)
    SeekBar seekBar;
    @Bind(R.id.musics_player_play_prev_btn)
    ImageButton prevBut;
    @Bind(R.id.musics_player_play_ctrl_btn)
    ImageButton ctrlBut;
    @Bind(R.id.musics_player_play_next_btn)
    ImageButton nextBut;

    private int size;
    private int playCurposition;
    private boolean isPlaying;
    private Long duration;
    private String name;
    private String artist;
    private String path;
    private static List<Media> mediaData;
    private Player play;

    public MusicPlayFragment() {

    }

    public static MusicPlayFragment newInstance(Media media, List<Media> list, int position) {
        Bundle bundle = new Bundle();
        // name list<media> duration
        mediaData = list;
        bundle.putInt("SIZE", list.size());
        bundle.putInt("POSITION", position);
        bundle.putLong("DURATION", media.getDuration());
        bundle.putString("NAME", media.getName());
        bundle.putString("ARTIST", media.getArtist());
        bundle.putString("PATH", media.getDataStr());
        MusicPlayFragment musicPlayFragment = new MusicPlayFragment();
        musicPlayFragment.setArguments(bundle);
        return musicPlayFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // get Bundle data
            Bundle bundle = getArguments();
            name = bundle.getString("NAME");
            path = bundle.getString("PATH");
            size = bundle.getInt("SIZE");
            artist = bundle.getString("ARTIST");
            duration = bundle.getLong("DURATION");
            playCurposition = bundle.getInt("POSITION");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_musics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initEvent();
    }

    private void initView() {
        player.setText(artist);
        songer.setText(name);
        totalTime.setText(CommonUtils.durFormant(duration));
        seekBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        play = new Player(seekBar);

        playerDiscView.loadAlbumCover(DAFAULT_PLAY_COVER);
    }

    private void initEvent() {
        prevBut.setOnClickListener(this);
        ctrlBut.setOnClickListener(this);
        nextBut.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        play.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        play.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.musics_player_play_prev_btn:
                playerDiscView.next();
                if (playCurposition - 1 >= 0 && playCurposition <= size) {
                    playCurposition--;
                } else if (playCurposition == size) {
                    Toast.makeText(getActivity(), "已是最后一首", Toast.LENGTH_SHORT).show();
                }
                updateNext(playCurposition);
                play.playUrl(path);
                play.pause();
                break;
            case R.id.musics_player_play_ctrl_btn:
                play.playUrl(path);
                if (isPlaying) {
                    // 暂停
                    playerDiscView.pause();
                    ctrlBut.setImageResource(R.drawable.btn_pause_selector);
                    playCurposition = play.mediaPlayer.getCurrentPosition();
                    isPlaying = false;
                } else {
                    // 恢复播放
                    playerDiscView.rePlay();
                    ctrlBut.setImageResource(R.drawable.btn_play_selector);
                    isPlaying = true;
                }
                play.pause();
                break;
            case R.id.musics_player_play_next_btn:
                playerDiscView.next();
                if (playCurposition + 1 <= size && playCurposition >= 0) {
                    playCurposition++;
                } else if (playCurposition == size) {
                    Toast.makeText(getActivity(), "已是最后一首", Toast.LENGTH_SHORT).show();
                }
                updateNext(playCurposition);
                play.playUrl(path);
                play.pause();
                break;
            default:
                break;
        }
    }

    private void updateNext(int position) {
        Media media = mediaData.get(position);
        path = media.getDataStr();
        player.setText(media.getArtist());
        songer.setText(media.getName());
        totalTime.setText(CommonUtils.durFormant(media.getDuration()));
    }

    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.progress = progress * play.mediaPlayer.getDuration() / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            play.mediaPlayer.seekTo(progress);
        }
    }
}
