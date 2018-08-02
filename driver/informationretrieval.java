package informationretrieval.driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import assignmentP3.util.Dictionary;
import assignmentP3.util.DocTable;
import assignmentP3.util.FileProcessor;
import assignmentP3.util.Helper;
import assignmentP3.util.QueryProcessor;
import assignmentP3.util.Result;

public class informationretrieval {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputFilePath = "";
		String outputfile1 = "";
		String outputfile2 = "";
		String outputfile3 = "";
		String outputfile4 = "";
				String strCurrLine = "";
		String temp = "";
		String temp3 = "";
		ArrayList<String> QueryList = new ArrayList<String>();
		
		int docID = 0;
		try
		{
			if(args.length !=2)
			{			
				throw new RuntimeException("Please provide 2 arguments");
			}
			else
			{
				//Assigning text file names
				inputFilePath = args[0];
				outputfile1 = "Dictionary.csv";
				outputfile2 = "Postings.csv";	
				outputfile3 = "DocsTable.txt";	
				outputfile4 = args[1];
							}
			
			FileProcessor fprObj;
			Helper hObj = new Helper();
			Dictionary dict = new Dictionary();
			Result resltObj = new Result(outputfile1,outputfile2,outputfile3,outputfile4);
			DocTable dtb = new DocTable();
			Scanner scr = new Scanner(System.in);
			TreeMap<Integer, String> DocName = new TreeMap<Integer, String>();
			
			File folder = new File(inputFilePath);
			File[] fileList = folder.listFiles();
			
					
			for(File currFile : fileList)
			{
				if(currFile.isFile())
				{
					
					fprObj =  new FileProcessor(currFile);
					String temp2 = "";
					String currLine = "";
					int checkRate = 0;
					//incrementing doc id
					docID ++;
					DocName.put(docID, currFile.getName());					FileReader fileReader = new FileReader(currFile);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
					StringBuffer stringBuffer = new StringBuffer();
					String line = "";
					String prevLine = "";
					
					while ((line = bufferedReader.readLine()) != null) 
					{
						prevLine = line;
						if(line.contains("-4 to +4"))
						{
							checkRate = 1;
							line = line.replace("-4 to +4", "");
							dtb.getRating(line,docID,checkRate);
							
						}
					}
					
				while((currLine = fprObj.readLine())!= null)
				{
					if(!currLine.isEmpty())
					{
						
						/*if(currLine.equalsIgnoreCase("<TITLE>")||currLine.equalsIgnoreCase("</TITLE>"))
						{
							strTitle = strTitle + currLine;
						}*/
						currLine = currLine + " ";
						
						temp2=temp2+currLine;
					}
				}
				
				
				//get title and reviewer of document
				
				dtb.getTitle(temp2,docID);
				dtb.getReviewer(temp2,docID);
				dtb.getSnippet(temp2,docID);
				if(checkRate == 0)
				{
					dtb.getRating(temp2,docID,checkRate);
				}
				
								hObj.removeTags(temp2);
				
				hObj.createDictionary(dict,docID);
				}
			}
	
			dict.createPostingList();
			
			QueryProcessor qrpObj = new QueryProcessor(dict,dtb);
			
			System.out.println("Enter command");
			//reading query file
			while(!(strCurrLine = (strCurrLine = scr.nextLine()).toLowerCase().trim()).equalsIgnoreCase("exit"))
			{
				if(!strCurrLine.isEmpty())
				{
									strCurrLine = strCurrLine.toLowerCase();
					QueryList.add(strCurrLine);
					
				}
				System.out.println("Enter command");
			}
			
						qrpObj.processQuery(QueryList,DocName);
			
			dict.Display(resltObj);
			dtb.Display(resltObj,docID);
			qrpObj.Display(resltObj);
		}
		catch(FileNotFoundException fileNotFndexp)
		{
			System.err.println("File not found: "+fileNotFndexp);
			System.exit(1);
		}
		catch(IOException ioexp)
		{
			System.err.println("Error in I/O: "+ioexp);
			System.exit(1);
		}
		catch(IndexOutOfBoundsException indexOutExp)
		{
			System.err.println("Input file is empty: "+indexOutExp);
			System.exit(1);
		}
		catch(NullPointerException nullPtrExp)
		{
			System.err.println("Input file is empty: "+nullPtrExp);
			System.exit(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}
