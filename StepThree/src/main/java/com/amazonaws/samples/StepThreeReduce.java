package com.amazonaws.samples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class StepThreeReduce extends Reducer<Text, IdAndCos, Text, MyArrayWritable >{

	
	protected void reduce(Text key, Iterable<IdAndCos> values, Context context) throws IOException, InterruptedException {
		
		 List<IdAndCos> cache = new ArrayList<IdAndCos>();

		
		 Iterator<IdAndCos> it = values.iterator();
		 while (it.hasNext()) {
			 IdAndCos value = it.next();
			 cache.add(value);
		 }
		 cache.sort(null);
		 
		 ArrayList<IdAndCos> ansArr = new ArrayList<IdAndCos>();

		 
		 for (int i=1; i < 11 ; i++){
			 IdAndCos tweet = cache.get(cache.size() - i);
			 ansArr.add(new IdAndCos( tweet.getId() , tweet.getcos() ));
		 }
			 
		 // TODO it's possible to delete the TextAndCos class...
		
	}
	
	
	
}
