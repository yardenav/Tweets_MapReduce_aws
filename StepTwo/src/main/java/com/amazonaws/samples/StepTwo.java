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

public class StepTwo {
	  public static void main(String[] args) throws Exception {
		    Configuration conf = new Configuration();
		    
		    Job job = Job.getInstance(conf, "cosine calc");
		    
		    job.setJarByClass(StepTwo.class);
		    job.setMapperClass(StepTwoMap.class);
		    job.setCombinerClass(StepTwoReduce.class);
		    job.setReducerClass(StepTwoReduce.class);
		    job.setOutputKeyClass(Text.class);
		    job.setOutputValueClass(Text.class);
		    job.setInputFormatClass(SequenceFileInputFormat.class);

			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));


			job.waitForCompletion(true);
		   
		  }
}
