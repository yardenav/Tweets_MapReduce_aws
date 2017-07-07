package com.amazonaws.samples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class StepOneReduce extends Reducer<Text, IdAndTf, TwoIds, TwoIdftf >{

	private long numOfTweets;
	
	protected void reduce(Text key, Iterable<IdAndTf> values, Context context) throws IOException, InterruptedException {
		 long count = 0;
		 List<IdAndTf> cache = new ArrayList<IdAndTf>();
		 
		// first loop and caching
		 Iterator<IdAndTf> it = values.iterator(); 
		 while (it.hasNext()) {
		    IdAndTf value = it.next();
		    count++;
		    cache.add(value);
		 }
		 double idf = Math.log(numOfTweets/count);

		 // second loop
		 cache.sort(null);
		 for (int i = 0; i < cache.size(); i++)
			    for (int j = i + 1; j < cache.size(); j++){
			    	String id1 = cache.get(i).getId();
			    	String id2 = cache.get(j).getId();
			    	double idftf1 = cache.get(i).getTf()*idf;
			    	double idftf2 = cache.get(j).getTf()*idf;
			    	context.write(new TwoIds(id1,id2), new TwoIdftf(idftf1,idftf2));
			    }
			    	
		 
		 
		 


	}
	
	protected void setup (Context context){
		numOfTweets = context.getConfiguration().getLong("num of tweets", 1);
	}
	
	
}
