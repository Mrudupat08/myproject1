package test;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class first {
	
    public static String extractTimestamp(String json)//Declares a public class "first"contains a static method called "extractTimestamp" that takes in a string (json) parameter and returns a string (timestamp).
    {
        String timestamp = "";//Initializes an empty string variable called "timestamp"
        Pattern pattern = Pattern.compile("\"timestamp\":(\\d+)");//Creates a pattern for regular expression matching using the "compile" method from the Pattern class. The regular expression is looking for a "timestamp" field in the json string and captures the numeric value after it.
        Matcher matcher = pattern.matcher(json);//Creates a Matcher object using the pattern and the json string passed in as a parameter.
        if (matcher.find()) {
            timestamp = matcher.group(1);
        }//If the Matcher object finds a match, it assigns the captured value (the timestamp) to the "timestamp" variable.
        return timestamp;
    }
    public static String extractContent(String json) {
        String Content = "";
        Pattern pattern = Pattern.compile("content\":\"(.+?)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
        	Content = matcher.group(1);
        }
        return Content;
    }
    public static void main(String[]args) throws FileNotFoundException, IOException  {
    	
        File myFile= new File("C:\\Users\\mrudula.patankar\\eclipse-workspace\\test\\src\\cerence_ark_sdk_2023-01-18_18-05-07.log");
        String SearchWord;//Declares a string variable "SearchWord" to store the word the user wants to search for.
        String SearchWord2;//Declares a string variable "SearchWord2" to store the word the user wants to search for.
        Scanner sc=new Scanner(System.in);//Declares a Scanner object to read user input.
        System.out.print("Enter word you want to search");
        SearchWord=sc.nextLine();
        System.out.print("Enter word you want to search");
        SearchWord2=sc.nextLine();
        
  
        List<String> timestamps = new ArrayList<>();
        List<String> timestamps2 = new ArrayList<>();//Creates a new List object called "timestamps" to store the timestamps of any lines that contain the search word.
        List<String> contents = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(myFile)))//Uses try block to create a BufferedReader object to read the file. 
      //Declares a string variable "json" to temporarily store each line of the file as it is read.
        {
            String json;
            while ((json = br.readLine()) != null)//Begins a while loop that continues until all lines of the file have been read.
            	//Reads the next line of the file and assigns it to the "json" variable.
            	{
            	if(json.contains("Hey Cerence")) {
            		continue;
            	}
                if (json.contains(SearchWord )&& json.contains("TimeMarker")) {
                   
                    String timestamp = extractTimestamp(json);
                    timestamps.add(timestamp);//Calls the "extractTimestamp" method on the current line to get the timestamp value and adds it to the "timestamps" list.
                }
                if (json.contains(SearchWord2)&&json.contains("TimeMarker")) {
                   
                    String timestamp2 = extractTimestamp(json);
                    timestamps2.add(timestamp2);
                }
                if(json.contains(SearchWord)&&json.contains("ace.TimeMarker ")) {
                	 if (json.contains("content")) {
                		 String content=extractContent(json);
                		 contents.add(content);
                	 } 
                } 
               
            }
        }
        for (int i = 0; i < timestamps.size(); i++) {
            timestamps.set(i, timestamps.get(i).replaceAll("[^\\d]", ""));
        }
        
        for (int i = 0; i < timestamps2.size(); i++) {
            timestamps2.set(i, timestamps2.get(i).replaceAll("[^\\d]", ""));
        }
       
        
        List<Long> differences = new ArrayList<>();
        for (int i = 0; i < timestamps.size(); i++) {
            long timestamp1 = Long.parseLong(timestamps.get(i));
            long timestamp2 = Long.parseLong(timestamps2.get(i));
            long difference = timestamp1- timestamp2;
            differences.add(difference); //calculates difference and add it to difference list	
        }
       
        for (int i = 0; i < timestamps.size(); i++) {
   
        	 System.out.printf("For utterance: %s, the timestamps are: %s and %s and latency is %s .\n", contents.get(i), timestamps.get(i),timestamps2.get(i),differences.get(i));
        }
        
        
        FileWriter writer = new FileWriter("C:\\Users\\mrudula.patankar\\eclipse-workspace\\test\\src\\test\\Solution.txt");
        writer.write("Differences between timestamps: " + differences + "\n");
        for (int i = 0; i < timestamps.size(); i++) {
            writer.write(String.format("%s %s %s %s \n", contents.get(i), timestamps.get(i),timestamps2.get(i),differences.get(i)));
        }//extract from lists
        writer.write("utterances:"+contents + "\n");
        writer.write("Timestamps for searchWord1: " + timestamps + "\n");
        writer.write("Timestamps for searchWord2: " + timestamps2 + "\n");
        writer.close();//to write in output file.
        sc.close();
    }
}