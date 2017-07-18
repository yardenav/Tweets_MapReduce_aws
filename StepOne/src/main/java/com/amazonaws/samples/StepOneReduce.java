package com.amazonaws.samples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.TaskCounter;
import org.apache.hadoop.mapreduce.Counter;

public class StepOneReduce extends Reducer<Text, IdAndTf, TwoIds, TwoIdftf >{

	private long numOfTweets;
	
	protected void reduce(Text key, Iterable<IdAndTf> values, Context context) throws IOException, InterruptedException {
		 int count = 0;
		 ArrayList<IdAndTf> cache = new ArrayList<IdAndTf>();
		System.out.println("## Launching reduce for WORD: " + key.toString() + " and VALUES:\n " + values.toString() + "\n");        					

		// first loop and caching
		 Iterator<IdAndTf> it = values.iterator(); 
		 while (it.hasNext()) {
		    IdAndTf value = it.next();
		    count++;
		    cache.add(new IdAndTf(value.getId(),value.getTf()));

		 }
		 
		 double idf = Math.log((double)numOfTweets/count);
		 // second loop
		 cache.sort(null);
		 for (int i = 0; i < cache.size(); i++)
			    for (int j = i + 1; j < cache.size(); j++){
			    	String id1 = cache.get(i).getId();
			    	String id2 = cache.get(j).getId();
			    	double idftf1 = cache.get(i).getTf()*idf;
			    	double idftf2 = cache.get(j).getTf()*idf;
					System.out.println("## Writing the couple ID: " + id1 + " , " + id2 + " and IDFTF: " + idftf1 + " , " + idftf2 +  " for WORD: " + key + "\n");        					
			    	context.write(new TwoIds(id1,id2), new TwoIdftf(idftf1,idftf2));
			    }
			    	
		 // TODO Add to context fti_sum= tfi_sum + tfi^2
		 


	}
	
	protected void setup (Context context){
		numOfTweets = context.getConfiguration().getLong("TweetNum", -1);
		long number = Long.valueOf(context.getConfiguration().get("Num")).longValue();
		long number2 = Long.parseLong(context.getConfiguration().get("Num"));
		System.out.println("## andddd.... The num of Tweets is: " + numOfTweets + "\n");		
		System.out.println("## andddd.... The num of Tweets2 is: " + number + "\n");
		System.out.println("## andddd.... The num of Tweets2 is: " + number2 + "\n");
		
	}
	
	
}
