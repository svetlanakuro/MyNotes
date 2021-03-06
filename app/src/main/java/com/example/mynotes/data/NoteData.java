package com.example.mynotes.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteData implements Parcelable {

    private String id;
    private String title;
    private String description;
    private boolean executed;
    private Date date;

    public NoteData() {
    }

    public NoteData(String title, String description, boolean executed, Date date) {
        this.title = title;
        this.description = description;
        this.executed = executed;
        this.date = date;
    }

    protected NoteData(Parcel in) {
        title = in.readString();
        description = in.readString();
        executed = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (executed ? 1 : 0));
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExecuted() {
        return executed;
    }

    public Date getDate() {
        return date;
    }
}
