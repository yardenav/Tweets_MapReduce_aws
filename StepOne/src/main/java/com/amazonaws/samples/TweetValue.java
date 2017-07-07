package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class TweetValue implements Writable {    
    private String userName;
    private String text;
	private long favorited;
	private long retweeted;

    public void write(DataOutput out) throws IOException {
      out.writeUTF(userName);
      out.writeUTF(text);
      out.writeLong(favorited);
      out.writeLong(retweeted);
    }

    public void readFields(DataInput in) throws IOException {
      userName = in.readUTF();
      text = in.readUTF();
      favorited = in.readLong();
      retweeted = in.readLong();
    }

    public static TweetValue read(DataInput in) throws IOException {
    	TweetValue w = new TweetValue();
      w.readFields(in);
      return w;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getFavorited() {
		return favorited;
	}

	public void setFavorited(long favorited) {
		this.favorited = favorited;
	}

	public long getRetweeted() {
		return retweeted;
	}

	public void setRetweeted(long retweeted) {
		this.retweeted = retweeted;
	}
  }
