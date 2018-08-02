package informationretrieval.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import informationretrieval.util.Dictionary;
public class QueryProcessor {

	ArrayList<String> qrList = new ArrayList<String>();
	ArrayList<String> queryResultList = new ArrayList<String>();
	ArrayList<String> postingList = new ArrayList<String>();
	TreeMap<String, String> finalDictionary = new TreeMap<String, String>();
	TreeMap<String, ArrayList<String>> dictionary = new TreeMap<String, ArrayList<String>>();
	TreeMap<Integer, String> DocName = new TreeMap<Integer, String>();
	TreeMap<Integer, String> DocTitle = new TreeMap<Integer, String>();
	TreeMap<Integer, String> DocReviewer = new TreeMap<Integer, String>();
	TreeMap<Integer, String> DocSnippet = new TreeMap<Integer, String>();
	TreeMap<Integer, String> DocRate = new TreeMap<Integer, String>();	
	Dictionary dict = new Dictionary();
	DocTable dtb = new DocTable();
	
	
	public QueryProcessor(Dictionary dict2, DocTable dtb2)
	{
		postingList = dict2.getPostingList();
		finalDictionary = dict2.getFinalDictionary();
		dictionary = dict2.getDictionary();
		DocTitle	= dtb2.getDocTitle();
		DocReviewer	= dtb2.getDocReviewer();
		DocSnippet	= dtb2.getDocSnippet();
		DocRate	= dtb2.getDocRate();
	}
	public void processQuery(ArrayList<String> queryList, TreeMap<Integer, String> docName) {
		// TODO Auto-generated method stub
		qrList = queryList;
		DocName = docName;
		
		//reading query form arrayList
		for(String currQuery : qrList)
		{			
			 if(currQuery.charAt(0) == 'a' && currQuery.charAt(1) == 'n' && currQuery.charAt(2) == 'd')
			 {
				 processANDQuery(currQuery);
			 }
			 else if(currQuery.charAt(0)=='o'&&currQuery.charAt(1)=='r')
			 {
				 processORQuery(currQuery);
			 }
			
			
			
		}
		
	}
	private void processORQuery(String temp2) {
		// TODO Auto-generated method stub
		TreeMap<Integer, Integer> ORqueryDocID = new TreeMap<Integer, Integer>();
		String[] tempQueryWords = temp2.split("\\s+");
		int len = tempQueryWords.length;
		for(int i=1; i<len; i++)
		{
			String word = tempQueryWords[i];
			ArrayList<String> tempList = new ArrayList<String>();
			//checking word present in dictionary
			if(dictionary.containsKey(word))
			{
				tempList= dictionary.get(word);
				//getting docID in which word present
				for(String id : tempList)
				{
					String[] tempID = id.split(",");
					int key = Integer.parseInt(tempID[0]);
					//updating docID's
					if(ORqueryDocID.containsKey(key))
					{
						int freq = ORqueryDocID.get(key);												
						freq = freq +1;						
						
						ORqueryDocID.put(key, freq);
					
					}					
					else
					{
							ORqueryDocID.put(key, 1);												
					}
				}
			}
		}
		
		//getting document details 
				Set set = ORqueryDocID.entrySet();
			      // Get an iterator
			      Iterator i = set.iterator();
				Integer currDocID;
				Integer value;
				int count = 0;
				System.out.println(temp2);
				queryResultList.add(temp2);
				ArrayList<String> tempNegList = new ArrayList<String>();
				while(i.hasNext()) {
					Map.Entry me = (Map.Entry)i.next();
					currDocID =  (Integer) me.getKey();			
					value = ORqueryDocID.get(currDocID);
					//comparing with AND word count
					if(value>=1)
					{
						
						String rate = "P";
						String tempStr = "";
						tempStr =  "File Name : "+DocName.get(currDocID) +"\n"+"Title : "+DocTitle.get(currDocID)+"\n"+"Reviewer : "+DocReviewer.get(currDocID)+"\n"+"Rate : "+DocRate.get(currDocID)+"\n"+"Snippet : "+DocSnippet.get(currDocID);
						count++;
						if(rate.equalsIgnoreCase(DocRate.get(currDocID)) )
						{
							System.out.println(tempStr);
							queryResultList.add(tempStr);
							
						}
						else
						{
							tempNegList.add(tempStr);
						}
					}
				}
				
				for(String strNeg : tempNegList)
				{
					System.out.println(strNeg);
					queryResultList.add(strNeg);
				}
				if(count == 0)
				{
					System.out.println("NO RESULTs");
					queryResultList.add("NO RESULTs");
				}
				queryResultList.add("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	private void processANDQuery(String temp) {
		// TODO Auto-generated method stub
		
		TreeMap<Integer, Integer> ANDqueryDocID = new TreeMap<Integer, Integer>();
		String[] tempQueryWords = temp.split("\\s+");
		int len = tempQueryWords.length;
		int wordcount =len-1;
		int flagNotQuery = 0;
		for(int i=1; i<len; i++)
		{
			String word = tempQueryWords[i];
			if(word.equalsIgnoreCase("and"))
			{
				if(tempQueryWords[i+1].equalsIgnoreCase("not"))
				{
					wordcount = i -1;
					flagNotQuery = 1;
				}
				
				
			}
			
			/*if(word.equalsIgnoreCase("and") || word.equalsIgnoreCase("not"))
			{
				
			}*/
			else
			{
				ArrayList<String> tempList = new ArrayList<String>();
				//checking word present in dictionary
				if(dictionary.containsKey(word))
				{
					tempList= dictionary.get(word);
					//getting docID in which word present
					for(String id : tempList)
					{
						String[] tempID = id.split(",");
						int key = Integer.parseInt(tempID[0]);
						//updating docID's
						if(ANDqueryDocID.containsKey(key))
						{
							int freq = ANDqueryDocID.get(key);
							if(flagNotQuery == 1)
							{
								freq = freq - 1;
							}
							else
							{
								freq = freq +1;
							}
							
							ANDqueryDocID.put(key, freq);
						
						}
						else
						{
							if(flagNotQuery == 1)
							{
								ANDqueryDocID.put(key, -1);
							}
							else
							{
								ANDqueryDocID.put(key, 1);
							}
							
						}
					}
				}
			}
			
			
		}
		
		//getting document details 
		Set set = ANDqueryDocID.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
		Integer currDocID;
		Integer value;
		int count = 0;
		System.out.println(temp);
		queryResultList.add(temp);
		ArrayList<String> tempNegList = new ArrayList<String>();
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			currDocID =  (Integer) me.getKey();			
			value = ANDqueryDocID.get(currDocID);
			//comparing with AND word count
			if(value==wordcount)
			{
				
				String rate = "P";
				String tempStr = "";
				tempStr =  "File Name : "+DocName.get(currDocID) +"\n"+"Title : "+DocTitle.get(currDocID)+"\n"+"Reviewer : "+DocReviewer.get(currDocID)+"\n"+"Rate : "+DocRate.get(currDocID)+"\n"+"Snippet : "+DocSnippet.get(currDocID);
				count++;
				if(rate.equalsIgnoreCase(DocRate.get(currDocID)) )
				{
					System.out.println(tempStr);
					queryResultList.add(tempStr);
					
				}
				else
				{
					tempNegList.add(tempStr);
				}
			}
		}
		
		for(String strNeg : tempNegList)
		{
			System.out.println(strNeg);
			queryResultList.add(strNeg);
		}
		if(count == 0)
		{
			System.out.println("NO RESULTs");
			queryResultList.add("NO RESULTs");
		}
		
		queryResultList.add("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
	}
	public void Display(Result resltObj) throws IOException {
		// TODO Auto-generated method stub
		resltObj.writeQueryResultToFile(queryResultList);
	}

	
}
