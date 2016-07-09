package com.moyu.lyqdhgo.yuedong.bean;

/**
 * Created by lyqdhgo on 2016/7/8.
 */

public class Media {

    private long size;
    private long duration;
    private String name;
    private String artist;
    private String dataStr;

    public Media(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public Media(long size, long duration, String name, String artist, String dataStr) {
        this.size = size;
        this.duration = duration;
        this.name = name;
        this.artist = artist;
        this.dataStr = dataStr;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    @Override
    public String toString() {
        return "Media{" +
                "size=" + size +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", dataStr='" + dataStr + '\'' +
                '}';
    }
}
