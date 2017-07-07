package com.amazonaws.samples;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapreduce.Reducer;


public class StepTwoReduce extends Reducer<TwoIds, TwoIdftf, TwoIds, DoubleWritable >{

	
	protected void reduce(TwoIds key, Iterable<TwoIdftf> values, Context context) throws IOException, InterruptedException {
		 double mone = 0;
		 double mechaneLeft = 0;
		 double mechaneRight = 0;
		 
		// first loop and caching
		 Iterator<TwoIdftf> it = values.iterator();
		 while (it.hasNext()) {
			 TwoIdftf value = it.next();
			 double v1 = value.idftf1;
			 double v2 = value.idftf2;
			 mone += v1 * v2;
			 mechaneLeft += v1*v1;
			 mechaneRight += v2*v2;
			 
		 }
		 double cosine_Similarity = mone / (Math.sqrt(mechaneLeft)*Math.sqrt(mechaneRight));
		 
		 context.write(key, new DoubleWritable(cosine_Similarity));
	}
	
	
	
}
