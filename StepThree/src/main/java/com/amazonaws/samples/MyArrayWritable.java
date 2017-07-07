package com.amazonaws.samples;



import org.apache.hadoop.io.ArrayWritable;


public class MyArrayWritable extends ArrayWritable {

    public MyArrayWritable(TextAndCos[] values) {
        super(TextAndCos.class, values);
    }

    @Override
    public TextAndCos[] get(){
        return (TextAndCos[]) super.get();
    }

    @Override
    public String toString() {
    	//TextAndCos[] values = get();
        return "ipmlement the to_string if you want";
    }
	

}