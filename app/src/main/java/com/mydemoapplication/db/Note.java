package com.mydemoapplication.db;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.*;
import com.mydemoapplication.Constant;

import java.util.Date;

@Entity(tableName = Constant.TABLE_NAME_NOTE)
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long note_id;

    @ColumnInfo(name = "note_content") // column name will be "note_content" instead of "content" in table
    private String content;

    private String title;

    @TypeConverters(DateRoomConverter.class)
    private Date date;

//    public Note(int note_id, String content, String title, Date date) {
//        this.note_id = note_id;
//        this.content = content;
//        this.title = title;
//        this.date = date;
//    }

    public Note(String content, String title) {
        this.content = content;
        this.title = title;
        this.date = new Date(System.currentTimeMillis());
    }

    @Ignore
    public Note(){}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getNote_id() {
        return note_id;
    }

    public void setNote_id(long note_id) {
        this.note_id = note_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;

        Note note = (Note) o;

        if (note_id != note.note_id) return false;
        return title != null ? title.equals(note.title) : note.title == null;
    }



    @Override
    public int hashCode() {
        int result = (int)note_id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.note_id);
        dest.writeString(this.content);
        dest.writeString(this.title);
    }

    protected Note(Parcel in) {
        this.note_id = in.readLong();
        this.content = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}