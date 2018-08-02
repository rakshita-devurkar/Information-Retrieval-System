package informationretrieval.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import informationretrieval.util.Result;

public class Helper {
	private String temp;
	String array [];
	
	private ArrayList<String> IndexList ;
	private ArrayList<String> LexoIndexList ;
	private ArrayList<String> stopWords = new ArrayList<String>(Arrays.asList("and", "a", "the", "an", "by", "from", "for", "hence", "of", "with", "in", 
			"within", "who", "when", "where", "why", "how", "whom", "have", "had", "has", "not", "but", "do", "does", "done"));
	
	
	private ArrayList<String> arrIndexWords;
	
	public void removeTags(String temp2) {
		// TODO Auto-generated method stub
		
		
		
		LexoIndexList = new ArrayList<String>();
		IndexList = new ArrayList<String>();
		
		//removing html tags
		temp = temp2.toString().replaceAll("<.*?>"," ");
		//System.out.println(temp);
		
		lowerIndex(temp);
		
	}

	private void lowerIndex(String temp2) {
		// TODO Auto-generated method stub
		//changing content into lowers case
				temp = temp.toLowerCase();
								
				
				temp = removeSingleChar(temp);
				removeHyphen(temp);
								removeStopWords(temp);
				removeBrackets();
				removeApostrophes();
				removeSpecialChar();
								
				performStemming ();
				lexicographicOrder(); 
				
				
				
	}

	private void lexicographicOrder() {
		// TODO Auto-generated method stub
		
		for(int currentIndex = 0;currentIndex < array.length;currentIndex++)
		{
			LexoIndexList.add(array[currentIndex].trim());
		}
		
		Collections.sort(LexoIndexList);
		
		Set<String> LexoIndexRemoveDuplicate = new LinkedHashSet<String>(LexoIndexList);
		LexoIndexList.clear();
		LexoIndexList.addAll(LexoIndexRemoveDuplicate);
		
				
		
		
	}

	

	private void performStemming() {
		
		String compareStr;
		int length;
		// TODO Auto-generated method stub
		for(int currentIndex = 0;currentIndex < array.length;currentIndex++)
		{
			if(array[currentIndex]!= null)
			{
				compareStr = array[currentIndex];
				length = compareStr.length();
				
				if(length>3)//ies
				{
					if(compareStr.charAt(length - 3)=='i'&& compareStr.charAt(length - 2)=='e'&& compareStr.charAt(length - 1)=='s')
					{
						if(!(compareStr.charAt(length - 4)=='a'|| compareStr.charAt(length - 4)=='e'))
						{
							StringBuilder myStr = new StringBuilder(compareStr);
							String convStr;
							myStr.setCharAt(length - 3, 'y');
							myStr.setCharAt(length - 2, ' ');
							myStr.setCharAt(length - 1, ' ');
							convStr = myStr.toString();
							array[currentIndex] = convStr;
						}
					}
					else if(compareStr.charAt(length - 2)=='e'&& compareStr.charAt(length - 1)=='s')
					{
						if(!(compareStr.charAt(length - 3)=='a'|| compareStr.charAt(length - 3)=='e' || compareStr.charAt(length - 3)=='o'))
						{
							StringBuilder myStr = new StringBuilder(compareStr);
							String convStr;							
							myStr.setCharAt(length - 2, 's');
							myStr.setCharAt(length - 1, ' ');
							convStr = myStr.toString();
							array[currentIndex] = convStr;
						}
					}
					else if(compareStr.charAt(length - 1)=='s')
					{
						if(!(compareStr.charAt(length - 2)=='u'|| compareStr.charAt(length - 2)=='s'))
						{
							StringBuilder myStr = new StringBuilder(compareStr);
							String convStr;														
							myStr.setCharAt(length - 1, ' ');
							convStr = myStr.toString();
							array[currentIndex] = convStr;
						}
					}
				}
				else if(length == 3)
				{
					if(compareStr.charAt(length - 3)=='i'&& compareStr.charAt(length - 2)=='e'&& compareStr.charAt(length - 1)=='s')
					{
						
							StringBuilder myStr = new StringBuilder(compareStr);
							String convStr;
							myStr.setCharAt(length - 3, 'y');
							//myStr.replace(start, end, str)
							myStr.setCharAt(length - 2, ' ');
							myStr.setCharAt(length - 1, ' ');
							convStr = myStr.toString();
							array[currentIndex] = convStr;
						
					}
					else if(compareStr.charAt(length - 2)=='e'&& compareStr.charAt(length - 1)=='s')
					{
						if(!(compareStr.charAt(length - 3)=='a'|| compareStr.charAt(length - 3)=='e' || compareStr.charAt(length - 3)=='o'))
						{
							StringBuilder myStr = new StringBuilder(compareStr);
							String convStr;							
							myStr.setCharAt(length - 2, 's');
							myStr.setCharAt(length - 1, ' ');
							convStr = myStr.toString();
							array[currentIndex] = convStr;
						}
					}
					else if(compareStr.charAt(length - 1)=='s')
					{
						if(!(compareStr.charAt(length - 2)=='u'|| compareStr.charAt(length - 2)=='s'))
						{
							StringBuilder myStr = new StringBuilder(compareStr);
							String convStr;														
							myStr.setCharAt(length - 1, ' ');
							convStr = myStr.toString();
							array[currentIndex] = convStr;
						}
					}
				}
				else if(length ==2)
				{
					 if(compareStr.charAt(length - 2)=='e'&& compareStr.charAt(length - 1)=='s')
						{							
								StringBuilder myStr = new StringBuilder(compareStr);
								String convStr;							
								myStr.setCharAt(length - 2, 's');
								myStr.setCharAt(length - 1, ' ');
								convStr = myStr.toString();
								array[currentIndex] = convStr;							
						}
						else if(compareStr.charAt(length - 1)=='s')
						{
							if(!(compareStr.charAt(length - 2)=='u'|| compareStr.charAt(length - 2)=='s'))
							{
								StringBuilder myStr = new StringBuilder(compareStr);
								String convStr;				
								myStr.replace(length - 1, length - 1, "");
								//myStr.setCharAt(length - 1, ' ');
								convStr = myStr.toString();
								array[currentIndex] = convStr;
							}
						}
				}
				else if(length == 1)
				{
					array[currentIndex]= "";
				}
			}
		
		}
		/*System.out.println("STEMMING");
		for(String currIndex : array)
		{
			if(currIndex.length() != 0)
			System.out.print(currIndex+" ");
		}
		System.out.println();*/
		for(int currentIndex = 0;currentIndex < array.length;currentIndex++)
		{
			//String str = array[currentIndex].replaceAll("(.\\s)|(\\s.$)", " ");
			String str = array[currentIndex];
			int len = str.length();
			if(len == 1)
			{
				array[currentIndex] = "";
			}
			
		}
		/*System.out.println("STEMMING and SINGLE char removal");
		for(String currIndex : array)
		{
			if(currIndex.length() != 0)
			System.out.print(currIndex+" ");
		}
		System.out.println();*/
	}

	private void removeHyphen(String temp2) {
		// TODO Auto-generated method stub
		String IndexArray[];
		String correctStr;
		String newStr;
		boolean flag = false;
		IndexArray = temp2.split(" ");
		
		
		//removing single character
		
				for(int currentIndex = 0;currentIndex < IndexArray.length;currentIndex++)
		{
			correctStr = IndexArray[currentIndex];
			if(!correctStr.isEmpty())
			{
				flag = correctStr.matches("[0-9][0-9,\\-]*-[0-9,\\-]*[0-9]|[a-z][a-z,\\-]*-[a-z,\\-]*[a-z]");
				if(flag)
				{
					newStr = correctStr.replaceAll("[-]"," ");
					String arrSplit[]  =  newStr.split(" ");
					for(int z=0; z<arrSplit.length;z++)
					{
						IndexList.add(arrSplit[z]);
					}
					
				}
				else
				{
					IndexList.add(correctStr);
				}
			}
			
			
		}
		
		array = new String[IndexList.size()];
		
		for(int count = 0; count < IndexList.size();count++)
		{
			if(IndexList.get(count).compareTo("null") != 0)
			{
				array[count] = IndexList.get(count);
			}
		}

		/*System.out.println("HYPHEN");
		for(String currIndex : array)
		{
			if(currIndex != null)
			{
				if(currIndex.length() != 0)
					System.out.print(currIndex+" ");
			}
			else
			{
				continue;
			}
		}
		System.out.println();*/
	}

	
	private void removeApostrophes() {
		// TODO Auto-generated method stub
		for(int currentIndex = 0;currentIndex < array.length;currentIndex++)
		{
			//String abc = array[currentIndex].replaceAll(".*?\\(.*?","");
			
			String abc = array[currentIndex].replaceAll("[']","");
			 abc = abc.replaceAll("[\"]","");
						 			array[currentIndex] = abc;		
			
		}
		/*System.out.println("Apostrophes");
		for(String currentIndex : array)
		{
			if(currentIndex.length() != 0)
			System.out.print(currentIndex+" ");
		}
		System.out.println();*/
	}

	/*private void removeHyphen() {
		// TODO Auto-generated method stub
		for(int currentIndex = 0;currentIndex < array.length;currentIndex++)
		{
						
			String abc = array[currentIndex].replaceAll("[-]"," ");
						array[currentIndex] = abc;		
			
		}
		System.out.println("HYPHEN");
		for(String currentIndex : array)
		{
			if(currentIndex.length() != 0)
			System.out.print(currentIndex+" ");
		}
		System.out.println();
	}*/

	private void removeSpecialChar() {
		// TODO Auto-generated method stub
		for(int currentIndex = 0;currentIndex < array.length;currentIndex++)
		{			
			String abc = array[currentIndex].replaceAll("[,\\.\\?:;!@]$","");
						array[currentIndex] = abc;		
			
		}
		/*System.out.println("SPECIAL CHARACTER");
		for(String currentIndex : array)
		{
			if(currentIndex.length() != 0)
			System.out.print(currentIndex+" ");
		}
		System.out.println();*/
	}

	private void removeBrackets() {
		// TODO Auto-generated method stub
		for(int currentIndex = 0;currentIndex < array.length;currentIndex++)
		{
						String abc = array[currentIndex].replaceAll("[()]","");
			 abc = abc.replaceAll("[{}]","");
			 abc = abc.replaceAll("[\\[\\]]","");
			 
						array[currentIndex] = abc;		
			
		}
		/*System.out.println("PARANTHESIS");
		for(String currentIndex : array)
		{
			if(currentIndex.length() != 0)
			System.out.print(currentIndex+" ");
		}
		System.out.println();*/
	}

	private void removeStopWords(String temp2) {
		// TODO Auto-generated method stub
		
	
				
			for(int currentIndex = 0;currentIndex < array.length;currentIndex++)
		{
			
				for(int currentStopWordindex = 0;currentStopWordindex < stopWords.size();currentStopWordindex++)
			{
				if(array[currentIndex]!= null)
				{
					if(array[currentIndex].equals(stopWords.get(currentStopWordindex)))
					{
						array[currentIndex] = "";
					}
				}
				
			}
			
		}
		
			/*System.out.println("STOPWORD");
		for(String currentIndex : array)
		{
			if(currentIndex.length() != 0)
			System.out.print(currentIndex+" ");
		}
		System.out.println();*/
	}

	private String  removeSingleChar(String temp2) {
		// TODO Auto-generated method stub
		String opr;
		//removing single character
		opr = temp2.replaceAll("(\\s.\\s)|(\\s.$)", " ");
				return opr;
	}

	/*public void Display(Result resltObj) throws IOException {
		// TODO Auto-generated method stub
		resltObj.writeToFile(LexoIndexList);
	}*/

	public void createDictionary(Dictionary dict, int docID) {
		// TODO Auto-generated method stub
		dict.makeDict(LexoIndexList,docID);
		
			}
	
	
	

}
