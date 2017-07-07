package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class TextAndCos implements Writable, Comparable<TextAndCos>{
	
	private String text;
	private Double cos;

	public String getText() {
		return text;
	}


	public double getcos() {
		return cos;
	}

	
	public TextAndCos() {
		this.text = null;
		this.cos = 0.0;
	}
	
	public TextAndCos(String text, double cos) {
		this.text = text;
		this.cos = cos;
	}



    public void write(DataOutput out) throws IOException {
        out.writeUTF(text);
        out.writeDouble(cos);
      }

      public void readFields(DataInput in) throws IOException {
        text = in.readUTF();
        cos = in.readDouble();
      }

      public static TextAndCos read(DataInput in) throws IOException {
    	  TextAndCos w = new TextAndCos();
        w.readFields(in);
        return w;
      }

	public int compareTo(TextAndCos arg) {
		return this.cos.compareTo(arg.cos);
	}





}
