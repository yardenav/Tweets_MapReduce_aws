package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class TweetKey implements Writable {    
    private String userID;
    private String created_at;

    public TweetKey() {
		this.userID = null;
		this.created_at = null;
	}
    
    public TweetKey(String userID, String created_at) {
		this.userID = userID;
		this.created_at = created_at;
	}
    
    public void write(DataOutput out) throws IOException {
      out.writeUTF(userID);
      out.writeUTF(created_at);
    }

	public String getUserID() {
		return userID;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void readFields(DataInput in) throws IOException {
    	userID = in.readUTF();
    	created_at = in.readUTF();
    }

    public static TweetKey read(DataInput in) throws IOException {
    	TweetKey w = new TweetKey();
      w.readFields(in);
      return w;
    }
  }
