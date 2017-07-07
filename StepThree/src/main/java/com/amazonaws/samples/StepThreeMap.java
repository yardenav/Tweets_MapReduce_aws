package com.amazonaws.samples;



import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StepThreeMap extends Mapper<TwoIds, DoubleWritable, Text, IdAndCos> {
		
	protected void map(TwoIds key, DoubleWritable value, Context context) {
		try {
			context.write(new Text(key.id1), new IdAndCos(key.id2,value.get()));
			context.write(new Text(key.id2), new IdAndCos(key.id1,value.get()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
		
}





	