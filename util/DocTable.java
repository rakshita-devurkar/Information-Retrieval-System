package informationretrieval.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocTable {

	TreeMap<Integer, String> DocTitle = new TreeMap<Integer, String>();
	TreeMap<Integer, String> DocReviewer = new TreeMap<Integer, String>();
	TreeMap<Integer, String> DocSnippet = new TreeMap<Integer, String>();
	TreeMap<Integer, String> DocRate = new TreeMap<Integer, String>();
	public ArrayList<String> DocTable = new ArrayList<String>();
	
	
	public TreeMap<Integer, String> getDocTitle() {
		return DocTitle;
	}

	public void setDocTitle(TreeMap<Integer, String> docTitle) {
		DocTitle = docTitle;
	}

	public TreeMap<Integer, String> getDocReviewer() {
		return DocReviewer;
	}

	public void setDocReviewer(TreeMap<Integer, String> docReviewer) {
		DocReviewer = docReviewer;
	}

	public TreeMap<Integer, String> getDocSnippet() {
		return DocSnippet;
	}

	public void setDocSnippet(TreeMap<Integer, String> docSnippet) {
		DocSnippet = docSnippet;
	}

	public TreeMap<Integer, String> getDocRate() {
		return DocRate;
	}

	public void setDocRate(TreeMap<Integer, String> docRate) {
		DocRate = docRate;
	}

	public void getTitle(String temp2, int docID) {
		// TODO Auto-generated method stub
		String strTitle = "";
		Pattern pat = Pattern.compile("<TITLE>(.+?)</TITLE>");
		Matcher match = pat.matcher(temp2);
		if(match.find())
		{
			strTitle = match.group(1);
			DocTitle.put(docID, strTitle);
		}
		
	}

	public void getReviewer(String temp2, int docID) {
		// TODO Auto-generated method stub
		String strReviewer = "";
		Pattern pat = Pattern.compile("reviewed by<.*?><.*?>(.+?)</A>");
		Matcher match = pat.matcher(temp2);
		if(match.find())
		{
			strReviewer = match.group(1);
			DocReviewer.put(docID, strReviewer);
		}
		
	}

	public void Display(Result resltObj, int docID) throws IOException {
		// TODO Auto-generated method stub
		for(int i =1 ; i<=docID ; i++)
		{
			String temp = "";
			temp =  i + ","+DocTitle.get(i)+","+DocReviewer.get(i)+","+DocSnippet.get(i)+","+DocRate.get(i);
			DocTable.add(temp);
						
			
		}
		resltObj.writeToFile(DocTable);
		System.out.println();
	}

	public void getSnippet(String temp2, int docID) {
		// TODO Auto-generated method stub
		String strSnippet = "";
		String strCapsule = "";
		int snippetLength = 0;
		int startPTag = 0;
		int i = 0;
		Boolean flag = false;
		ArrayList<String> paraContent = new ArrayList<String>();
		
		try
		{
				Pattern pat = Pattern.compile("<P>(.+?)</P>");
		Matcher match = pat.matcher(temp2);
		while(match.find())
		{
			paraContent.add(match.group(1));
			i++;
		}
		//int gcount = match.groupCount();
		int count =0;
		for(int k= 0; k<i; k++)
		{
			String temp = paraContent.get(k);
			
			try
			{
				Pattern pat2 = Pattern.compile("Capsule review:");
				Matcher match2 = pat2.matcher(temp);
				if(match2.find())
				{
					count =  1;
					startPTag = k;
				}								
			}
			catch(Exception e)
			{
				count = 0;
			}
			
		}
			if(count>0)
				//Capsule review present in doc
			{
				strCapsule = paraContent.get(startPTag);
				String arrSplit[] = strCapsule.split("Capsule review:");
				strCapsule = arrSplit[1];
				snippetLength = checkWordCount(strCapsule);
				strSnippet = strSnippet + strCapsule;
				if(snippetLength<50)
				{
					
					if(startPTag+1<=(paraContent.size()-1))
					{
						String tempSnip = paraContent.get(startPTag+1);
						strSnippet = strSnippet + tempSnip;
						snippetLength = checkWordCount(strSnippet);
						if(snippetLength<50)
						{
							if(startPTag+2<=(paraContent.size()-1))
							{
								String tempSnip2 = paraContent.get(startPTag+2);
								strSnippet = strSnippet + tempSnip2;
								snippetLength = checkWordCount(strSnippet);
								if(snippetLength>50)
								{
									flag = true;
																	}
							}
						}
						else
						{
							flag = true;
													}
						
					}

									}
				else
				{
					flag = true;
									}
			}
			else
				//capsule value not present
			{
				strSnippet = paraContent.get(startPTag);
				snippetLength = checkWordCount(strSnippet);
				strSnippet = strSnippet + strSnippet;
				if(snippetLength<50)
				{
					
					if(startPTag+1<=(paraContent.size()-1))
					{
						String tempSnip = paraContent.get(startPTag+1);
						strSnippet = strSnippet + tempSnip;
						snippetLength = checkWordCount(strSnippet);
						if(snippetLength<50)
						{
							if(startPTag+2<=(paraContent.size()-1))
							{
								String tempSnip2 = paraContent.get(startPTag+2);
								strSnippet = strSnippet + tempSnip2;
								snippetLength = checkWordCount(strSnippet);
								if(snippetLength>50)
								{
									flag = true;
									//break;
								}
							}
						}
						else
						{
							flag = true;
													}
						
					}

									}
				else
				{
					flag = true;
									}

			}
		
		
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			//System.exit(1);
		}
		
		if(flag)
		{
			DocSnippet.put(docID, strSnippet);
		}
		
	}

	private int checkWordCount(String temp) {
		// TODO Auto-generated method stub
		if (temp.isEmpty() ||temp == null)
		{ 
			return 0; 
		} 
		
			String[] words = temp.split("\\s+");
			return words.length;
		
	}

	public void getRating(String temp2, int docID, int checkRate) {
		// TODO Auto-generated method stub
		String strRating = "";
		
		if(checkRate == 1)
		{
			Pattern pat = Pattern.compile("[-+]?[1-4].?[0-9]?");
			Matcher match = pat.matcher(temp2);
			if(match.find())
			{
				strRating = match.group(0);
				
			}
			if(strRating.contains("-"))
			{
				DocRate.put(docID, "N");
			}
			else
			{
				DocRate.put(docID, "P");
			}
		}
		else
		{
			DocRate.put(docID, "NA");
		}
		
		
		
		
		
	}


	
	
	
	
	
}
