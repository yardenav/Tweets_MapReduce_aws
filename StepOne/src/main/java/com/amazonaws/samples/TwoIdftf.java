package com.amazonaws.samples;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class TwoIdftf implements Writable {

	Double idftf1;
	Double idftf2;
	
	
	
	public TwoIdftf() {
		this.idftf1 = 0.0;
		this.idftf2 = 0.0;

	}

	public TwoIdftf(double idftf1, double idftf2) {
		this.idftf1 = idftf1;
		this.idftf2 = idftf2;
	}

	@Override
	public void write(DataOutput out) throws IOException {
        out.writeDouble(idftf1);
        out.writeDouble(idftf2);		
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
        idftf1 = in.readDouble();
        idftf2 = in.readDouble();
		
	}
	
    public static TwoIdftf read(DataInput in) throws IOException {
    	TwoIdftf w = new TwoIdftf();
    	w.readFields(in);
    	return w;
    }


	@Override
	public String toString() {
		return "IDF-TF_1:" + this.idftf1.toString() + " IDF-TF_2:" + this.idftf1.toString() + "\n"; 
	}
		

}
