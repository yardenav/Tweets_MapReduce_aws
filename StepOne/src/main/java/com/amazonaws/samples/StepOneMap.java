package com.amazonaws.samples;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class StepOneMap extends Mapper<LongWritable, Text, Text, IdAndTf> {
		
	private ArrayList<String> stopWords = new ArrayList<String>();		

	protected void map(LongWritable key, Text value, Context context) {
		System.out.println("#### Starting a map work");        	

        String[] tuple = value.toString().split("\\n");
        String id = null;
        String text = null;
        	
            for(int i=0;i<tuple.length; i++){
            	text = tuple[i].substring(tuple[i].indexOf(" "),tuple[i].length());
                id = tuple[i].substring(0,tuple[i].indexOf(" "));

                
        		List<String> words = getWords(text);
        		if (words.size() >= 1) {
        			List<WordTF> wordsF = getF(words);
        			double maxF = wordsF.get(wordsF.size()-1).tf;
					System.out.println("## the list of words F's is: \n" + wordsF + "\n");        					
        			for (WordTF wordF : wordsF) {
        				double ansTF = 0.5 + 0.5*(wordF.tf/maxF);
        				Text ansWord = new Text(wordF.word);
        				
        				IdAndTf ansValue = new IdAndTf(id,ansTF);
        				try {
        					System.out.println("## Writing the couple ID: " + id + " and TF: " + ansTF + " for WORD: " + wordF.word + "\n");
        					
        					context.write(ansWord, ansValue);
        					
        					
        				} catch (IOException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        				
        				
        				
        			}
        		}               
                
                
                
            }
 
            
		
	}
	

	 
	 private static List<String> getWords(String text) {
		    List<String> words = new ArrayList<String>();
		    BreakIterator breakIterator = BreakIterator.getWordInstance();
		    breakIterator.setText(text);
		    int lastIndex = breakIterator.first();
		    while (BreakIterator.DONE != lastIndex) {
		        int firstIndex = lastIndex;
		        lastIndex = breakIterator.next();
		        if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text.charAt(firstIndex))) {
		            words.add(text.substring(firstIndex, lastIndex));
		        }
		    }

		    return words;
		}
	 
		public List<WordTF> getF(List<String> words){
			
			int wordCount = 0;
			
			List<WordTF> counts = new ArrayList<WordTF>();
			List<String> alreadyInside = new ArrayList<String>();
			for (String word : words) {
				wordCount = countWords(word, words);
				if (!stopWords.contains(word) && !alreadyInside.contains(word)){
					alreadyInside.add(word);
					counts.add(new WordTF(word,(double)wordCount));
				}				
				
			}
			counts.sort(null);
			
			return counts;
			
		}
		
		public int countWords(String word, List<String> words) {
			int counter = 0;
			for (String currWord : words) {
				if (currWord.equals(word)){
					counter++;
				}
					
			}
			return counter;
		}
		
		protected void setup (Context context){
		  	try {
		  		String stopW_Url = "http://web.archive.org/web/20080501010608/http://www.dcs.gla.ac.uk/idom/ir_resources/linguistic_utils/stop_words";		  		
		  		URL webS = new URL(stopW_Url);
		  		ReadableByteChannel readableBC = Channels.newChannel(webS.openStream());
		  		FileOutputStream fos = new FileOutputStream("stop_words");
		  		fos.getChannel().transferFrom(readableBC, 0, Long.MAX_VALUE);
		  		fos.close();
			
		  		// Extract stop Words From File
		  		BufferedReader buffReader = new BufferedReader(new InputStreamReader(new FileInputStream("stop_words"), "UTF-8"));
		  		String line;
			
		  		while ((line = buffReader.readLine()) != null) {
		  			stopWords.add(line);
		  		}
			    buffReader.close();
		  	}
		  	catch (Exception e) {
		  		e.printStackTrace();
		  	}		
		}
		
		
}

final class WordTF implements Comparable<WordTF>{
	public String word;
	public Double tf;
	
	public WordTF(String word, Double tf) {
		this.word = word;
		this.tf = tf;
	}
	

	@Override
	public String toString() {
		return "WordTF [word=" + word + ", tf=" + tf + "]";
	}


	@Override
	public int compareTo(WordTF arg) {
		return this.tf.compareTo(arg.tf);
	}
}




	