package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class TweetValue implements Writable {    
    private String userName;
    private String text;
	private boolean favorited;
	private boolean retweeted;

	
    public TweetValue() {
		this.userName = null;
		this.text = null;
		this.favorited = false;
		this.retweeted = false;	
		}

	public TweetValue(String userName, String text, boolean favorited, boolean retweeted) {
		super();
		this.userName = userName;
		this.text = text;
		this.favorited = favorited;
		this.retweeted = retweeted;
	}

	public void write(DataOutput out) throws IOException {
      out.writeUTF(userName);
      out.writeUTF(text);
      out.writeBoolean(favorited);
      out.writeBoolean(retweeted);
    }

    public void readFields(DataInput in) throws IOException {
      userName = in.readUTF();
      text = in.readUTF();
      favorited = in.readBoolean();
      retweeted = in.readBoolean();
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

	public boolean getFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public boolean getRetweeted() {
		return retweeted;
	}

	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}
  }
