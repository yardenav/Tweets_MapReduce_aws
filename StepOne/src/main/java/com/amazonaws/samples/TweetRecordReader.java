package com.amazonaws.samples;

import java.io.IOException;
 
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;
import org.json.JSONException;
import org.json.JSONObject;
 
 
public class TweetRecordReader extends RecordReader<TweetKey,TweetValue> { 
 
    LineRecordReader reader;
    
    TweetRecordReader() {
        reader = new LineRecordReader(); 
    }
    
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context)
            throws IOException, InterruptedException {
        reader.initialize(split, context);
    }
 
 
    @Override
    public void close() throws IOException {
        reader.close();        
    }
    
    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        return reader.nextKeyValue();
    }
    
    @Override
    public TweetKey getCurrentKey() throws IOException, InterruptedException {
    	String line = reader.getCurrentValue().toString();
		String created_at = null;
		String user_id = null;
        String[] tuple = line.split("\\n");
        try{
            for(int i=0;i<tuple.length; i++){
                JSONObject obj = new JSONObject(tuple[i]);
                created_at = obj.getString("created_at");
                user_id = obj.getString("id_str");
            }
        }catch(JSONException e){
    		System.out.println("## ERROR1 ## There was a problem with the json or recordReader");        	
            e.printStackTrace();
        }
		
    	if (created_at == null || user_id == null ) {
    		System.out.println("## ERROR2 ## There was a problem with the json or recordReader");
    		return null;
    	}
    	else
    		return new TweetKey(created_at,user_id);
    }
    
    @Override
    public TweetValue getCurrentValue() throws IOException, InterruptedException {
    	String line = reader.getCurrentValue().toString();
        String userName = null;
        String text = null;
    	boolean favorited = false;
    	boolean retweeted = false;
        String[] tuple = line.split("\\n");
        try{
            for(int i=0;i<tuple.length; i++){
                JSONObject obj = new JSONObject(tuple[i]);
                userName = obj.getJSONObject("user").getString("name");
                favorited = obj.getBoolean("favorited");
                retweeted = obj.getBoolean("retweeted");
                text = obj.getString("text");
                
            }
        }catch(JSONException e){
    		System.out.println("## ERROR1 ## There was a problem with the json or recordReader (value)");        	
            e.printStackTrace();
        }
		
    	if (userName == null || text == null ) {
    		System.out.println("## ERROR2 ## There was a problem with the json or recordReader (value)");
    		return null;
    	}
    	else
    		return new TweetValue(userName,text,favorited,retweeted);
    }
    
    @Override
    public float getProgress() throws IOException, InterruptedException {
        return reader.getProgress();
    }
    
    
 
    
}

