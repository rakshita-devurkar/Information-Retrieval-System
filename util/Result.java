package informationretrieval.util;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Result {
	private String outputFileName1;
	private String outputFileName2;
	private String outputFileName3;
	private String outputFileName4;
		
	
	public Result(String outputfile1, String outputfile2, String outputfile3, String outputfile4) {
		// TODO Auto-generated constructor stub
		
		this.outputFileName1=outputfile1;
		this.outputFileName2=outputfile2;
		this.outputFileName3=outputfile3;
		this.outputFileName4=outputfile4;
	}
	
	
	public void writeToFile(ArrayList<String> arrList) throws IOException {
		// TODO Auto-generated method stub
		FileWriter FWrt = null;
		try
		{
			FWrt = new FileWriter(this.outputFileName3);
		}
		catch(FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		
		for(String currentLine : arrList)
		{
			if(!currentLine.isEmpty())
			{
				if(currentLine.length() != 0)
				{
				FWrt.write(currentLine);
				FWrt.write("\r\n");
				}
			}
			
		}
		FWrt.close();
	}


	public void writeDictToFile(TreeMap<String, String> finalDictionary) throws IOException {
		// TODO Auto-generated method stub
		FileWriter FWrt = null;
		try
		{
			FWrt = new FileWriter(this.outputFileName1);
		}
		catch(FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		FWrt.write("Term,df,offset");
		FWrt.write("\r\n");
		 // Get a set of the entries
		Set set = finalDictionary.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
		String currTerm="";
		String value="";
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			currTerm = (String) me.getKey();
			if(!currTerm.isEmpty())
			{
				if(currTerm.length() != 0)
				{
					value = finalDictionary.get(currTerm);
				FWrt.write(currTerm+","+value);
				FWrt.write("\r\n");
				}
			}
			
		}
		
		FWrt.close();
	}


	public void writePostingToFile(ArrayList<String> postingList) throws IOException {
		// TODO Auto-generated method stub
		FileWriter FWrt = null;
		try
		{
			FWrt = new FileWriter(this.outputFileName2);
		}
		catch(FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		FWrt.write("DocID,tf");
		FWrt.write("\r\n");
		for(String currentLine : postingList)
		{
			if(!currentLine.isEmpty())
			{
				if(currentLine.length() != 0)
				{
				FWrt.write(currentLine);
				FWrt.write("\r\n");
				}
			}
			
		}
		FWrt.close();
	}


	public void writeQueryResultToFile(ArrayList<String> queryResultList) throws IOException {
		// TODO Auto-generated method stub
		FileWriter FWrt = null;
		try
		{
			FWrt = new FileWriter(this.outputFileName4);
		}
		catch(FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		
		for(String currentLine : queryResultList)
		{
			if(!currentLine.isEmpty())
			{
				if(currentLine.length() != 0)
				{
				FWrt.write(currentLine);
				FWrt.write("\r\n");
				}
			}
			
		}
		FWrt.close();
	}


	

}
