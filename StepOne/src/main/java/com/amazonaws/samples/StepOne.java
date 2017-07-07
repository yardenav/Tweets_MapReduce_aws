package com.amazonaws.samples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class StepOne {
	  public static void main(String[] args) throws Exception {
		    Configuration conf = new Configuration();
		    
		    conf.setLong("num of tweets", countTweets(args[0]));
		    Job job = Job.getInstance(conf, "tfidf calc");
		    
		    job.setJarByClass(StepOne.class);
		    job.setMapperClass(StepOneMap.class);
		    job.setCombinerClass(StepOneReduce.class);
		    job.setReducerClass(StepOneReduce.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(Text.class);
		    job.setInputFormatClass(SequenceFileInputFormat.class);

			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));


			job.waitForCompletion(true);
		   
		  }
	  
	  private static long countTweets(String inputPath) {
		  LineNumberReader lnr;
		  long numOfLines = 0;
		try {
			lnr = new LineNumberReader(new FileReader(new File(inputPath)));
			lnr.skip(Long.MAX_VALUE);
			numOfLines = lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
			  // Finally, the LineNumberReader object should be closed to prevent resource leak
			  lnr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		return numOfLines;
	  }
}
