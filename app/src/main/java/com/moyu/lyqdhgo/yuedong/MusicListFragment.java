package com.moyu.lyqdhgo.yuedong;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moyu.lyqdhgo.yuedong.adapter.MusicAdapter;
import com.moyu.lyqdhgo.yuedong.bean.Media;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lyqdhgo on 2016/7/6.
 */

public class MusicListFragment extends Fragment {

    public static final String TAG = MusicListFragment.class.getSimpleName();

    @Bind(R.id.list_music)
    RecyclerView recyclerView;

    private MusicAdapter adapter;
    private List<Media> mediaData = new ArrayList<>();
    OnHeadlineSelectedListener callBack;

    public MusicListFragment() {
    }

    public static MusicListFragment newInstance() {
        Bundle bundle = new Bundle();
        // set Bundle data
        MusicListFragment fragment = new MusicListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // get Bundle data
            initData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView(mediaData);
        initEvent();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private void initData() {
        ContentResolver resolver = getActivity().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] object = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
        };
        Cursor cursor = resolver.query(uri, object, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(0);
                long duration = cursor.getLong(1);
                long size = cursor.getLong(2);
                String dataStr = cursor.getString(3);
                String artist = cursor.getString(4);
                Media media = new Media(size, duration, name, artist, dataStr);
                mediaData.add(media);
                Log.i(TAG, "title->" + name + "artist->" + artist + " dataStr->" + dataStr + " duration->" + duration + " size->" + size);
            }
        }
    }

    private void initView(List<Media> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter = new MusicAdapter(getActivity(), list));
    }

    private void initEvent() {
        adapter.setOnItemClickListener(new MusicAdapter.OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, Media media) {
                String name = media.getName();
                String path = media.getDataStr();
                Log.i(TAG, "name->" + name + "path->" + path);
                callBack.onMusicSelected(media, mediaData);
            }
        });
    }

    // callback data for activity
    public interface OnHeadlineSelectedListener {
        void onMusicSelected(Media media, List<Media> list);
    }
}
