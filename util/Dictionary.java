package informationretrieval.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Dictionary {

	//int[] dictInfo =new int[2];
	//int[] tempArr =new int[2];
	TreeMap<String, ArrayList<String>> dictionary = new TreeMap<String, ArrayList<String>>();
	TreeMap<String, String> finalDictionary = new TreeMap<String, String>();
	ArrayList<String> postingList = new ArrayList<String>();
	Enumeration collection;
	Boolean flag = false;
	public void makeDict(ArrayList<String> lexoIndexList, int docID) {
		// TODO Auto-generated method stub
		
		for(String currIndex : lexoIndexList)
		{			
			if(!currIndex.isEmpty())
			{
				if(dictionary.containsKey(currIndex))
				{
					ArrayList<String> tempList = new ArrayList<String>();
					String temp = "";
					int freq = 0;
					tempList = dictionary.get(currIndex);
					int length = tempList.size();
					temp = tempList.get(length-1);
					String [] arrInfo = temp.split(",");
					if(Integer.parseInt(arrInfo[0])==docID)
					{
						freq = Integer.parseInt(arrInfo[1]);
						freq = freq +1;
						tempList.set(length-1, Integer.toString(docID)+","+freq);
					}
					else
					{
						tempList.add(Integer.toString(docID)+","+1);
					}
					
					dictionary.put(currIndex, tempList);
				}
				else
					//when term is not present in dictionary
				{
					//int postListLength = postingList.size();
					ArrayList<String> dictInfo =new ArrayList<String>();
					dictInfo.add(Integer.toString(docID)+","+1);
					dictionary.put(currIndex, dictInfo);
				}
				
			}
			
		}
		//System.out.println();
	}
	public void createPostingList() {
		// TODO Auto-generated method stub
		int offset = 0;
		
		 // Get a set of the entries
	      Set set = dictionary.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
		String currTerm="";
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			currTerm = (String) me.getKey();
			ArrayList<String> tempList = new ArrayList<String>();
			tempList= dictionary.get(currTerm);
			int length = tempList.size();
			finalDictionary.put(currTerm, length+","+offset);
			offset = offset + length;
			for(String currIndex : tempList)
			{
				postingList.add(currIndex);
			}
		}
		
	//System.out.println("done");			
	}
	public void Display(Result resltObj) throws IOException {
		// TODO Auto-generated method stub
		resltObj.writeDictToFile(finalDictionary);
		resltObj.writePostingToFile(postingList);
	}
	public ArrayList<String> getPostingList() {
		return postingList;
	}
	public TreeMap<String, String> getFinalDictionary() {
		return finalDictionary;
	}
	public TreeMap<String, ArrayList<String>> getDictionary() {
		return dictionary;
	}
	
	
	
	
	
	
	
}
