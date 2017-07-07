package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class TwoIds implements Writable {

	String id1;
	String id2;
	
	
	
	public TwoIds() {
		this.id1 = null;
		this.id2 = null;

	}

	public TwoIds(String id1, String id2) {
		this.id1 = id1;
		this.id2 = id2;
	}

	@Override
	public void write(DataOutput out) throws IOException {
        out.writeUTF(id1);
        out.writeUTF(id2);		
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
        id1 = in.readUTF();
        id2 = in.readUTF();
		
	}
	
    public static TwoIds read(DataInput in) throws IOException {
    	TwoIds w = new TwoIds();
    	w.readFields(in);
    	return w;
    }



	

}
