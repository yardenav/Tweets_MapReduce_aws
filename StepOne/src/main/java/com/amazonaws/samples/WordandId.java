package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class WordandId implements Writable{

	private String word;
	private String id;
	
	public WordandId() {
		this.word = null;
		this.id = null;
	}
	
	public WordandId(String word, String id) {
		this.word = word;
		this.id = id;
	}

    public void write(DataOutput out) throws IOException {
        out.writeUTF(word);
        out.writeUTF(id);

      }

      public void readFields(DataInput in) throws IOException {
          word = in.readUTF();    	  
          id = in.readUTF();

      }

      public static WordandId read(DataInput in) throws IOException {
    	  WordandId w = new WordandId();
        w.readFields(in);
        return w;
      }

}
