package com.amazonaws.samples;



import java.io.IOException;


import org.apache.hadoop.mapreduce.Mapper;

public class StepTwoMap extends Mapper<TwoIds, TwoIdftf, TwoIds, TwoIdftf> {
		
	protected void map(TwoIds key, TwoIdftf value, Context context) {
		try {
			context.write(key, value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
		
}





	