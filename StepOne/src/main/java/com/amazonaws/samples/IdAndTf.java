package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class IdAndTf implements Writable, Comparable<IdAndTf>{
	
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getTf() {
		return tf;
	}

	public void setTf(double tf) {
		this.tf = tf;
	}

	private double tf;
	
	public IdAndTf() {
		this.id = null;
		this.tf = 0.0;
	}
	
	public IdAndTf(String id, double tf) {
		this.id = id;
		this.tf = tf;
	}



    public void write(DataOutput out) throws IOException {
        out.writeUTF(id);
        out.writeDouble(tf);
      }

      public void readFields(DataInput in) throws IOException {
        id = in.readUTF();
        tf = in.readDouble();
      }

      public static IdAndTf read(DataInput in) throws IOException {
    	  IdAndTf w = new IdAndTf();
        w.readFields(in);
        return w;
      }

	public int compareTo(IdAndTf arg) {
		return this.id.compareTo(arg.id);
	}

}
