package com.amazonaws.samples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class StepOne {
	  public static void main(String[] args) throws Exception {
		    Configuration conf = new Configuration();
		    Long numberOfTweets = countTweets("hdfs://localhost:19000/user/Owner/" + args[0], conf);
		    conf.setLong("TweetNum", numberOfTweets);
		    conf.set("Num", numberOfTweets.toString());
		    
		    Job job = Job.getInstance(conf, "tfidfPairs");
		    System.out.println("####### Starting to configure mapred job StepOne ##########");

		    job.setJarByClass(StepOne.class);
		    job.setMapperClass(StepOneMap.class);
		    //job.setCombinerClass(StepOneReduce.class);
		    job.setReducerClass(StepOneReduce.class);
		    job.setMapOutputKeyClass(Text.class);
		    job.setMapOutputValueClass(IdAndTf.class);
		    job.setOutputKeyClass(TwoIds.class);
		    job.setOutputValueClass(TwoIdftf.class);
		    //job.setPartitionerClass(StepOnePartitioner.class);
		    job.setInputFormatClass(TextInputFormat.class);
		    System.out.println("####### Finished configuring mapred job StepOne ##########");
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));

		    System.out.println("####### inputPath is: " + job.getConfiguration().get("map.input.dir") + "\n");

			
			job.waitForCompletion(true);
		   
		  }
	  
	  private static long countTweets(String inputPath, Configuration conf) {
		  LineNumberReader lnr;
		  long numOfLines = 0;
		try {
			Path pt = new Path(inputPath);
			FileSystem fs = FileSystem.get(conf);
		    FSDataInputStream inputStream = fs.open(pt);
			BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            line=br.readLine();
            while (line != null){
                    System.out.println(line);
                    line=br.readLine();
                    numOfLines++;
            }
            fs.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		System.out.println(" ### sooo...num of line is " + numOfLines + "\n");
		return numOfLines;
	  }
}

//public static class StepOnePartitioner extends Partitioner<Text, IntWritable> {
//    @Override
//    public int getPartition(Text key, IntWritable value, int numPartitions) {
//      return getLanguage(key) % numPartitions;
//    }
//  
//    private int getLanguage(Text key) {
//       if (key.getLength() > 0) {
//          int c = key.charAt(0);
//          if (c >= Long.decode("0x05D0").longValue() && c <= Long.decode("0x05EA").longValue())
//             return 1;
//       }
//       return 0;
//    }
//  }
