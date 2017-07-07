package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class IdAndCos implements Writable, Comparable<IdAndCos>{
	
	private String id;
	private Double cos;

	public String getId() {
		return id;
	}


	public double getcos() {
		return cos;
	}

	
	public IdAndCos() {
		this.id = null;
		this.cos = 0.0;
	}
	
	public IdAndCos(String id, double cos) {
		this.id = id;
		this.cos = cos;
	}



    public void write(DataOutput out) throws IOException {
        out.writeUTF(id);
        out.writeDouble(cos);
      }

      public void readFields(DataInput in) throws IOException {
        id = in.readUTF();
        cos = in.readDouble();
      }

      public static IdAndCos read(DataInput in) throws IOException {
    	  IdAndCos w = new IdAndCos();
        w.readFields(in);
        return w;
      }

	public int compareTo(IdAndCos arg) {
		return this.cos.compareTo(arg.cos);
	}





}
