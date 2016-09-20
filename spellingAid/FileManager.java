package spellingAid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages all interactions with files.
 * (Some Code was reused and modified from assignment 2)
 * 
 * Responsibility: 
 * 1. check if wordlist exist
 * 2. read in the entire wordList file.
 * 3. get the path of current directory
 * 4. check if the video reward file exist
 * 
 * @author victor
 *
 */
public class FileManager {
	public final String WORDLIST = "NZCER-spelling-lists.txt";
	public final String VIDEO = "big_buck_bunny_1_minute.avi";
	
	/**
	 * This method would return the currentDirectory of the jar file/class files.
	 * @return
	 */
	public String getCurrentDir(){
		URL s = getClass().getProtectionDomain().getCodeSource().getLocation();
		String string = s.getPath();
		if (string.endsWith("/")){
			string = string.substring(0, string.length()-1);
		}
		
		int lastIndex = string.lastIndexOf("/");
		string = string.substring(0, lastIndex+1);
		return string;
	}
	
	/**
	 * This is a method for getting the absolute
	 * path of a file.
	 * @param fileName
	 * @return
	 */
	public String getAbsolutePath(String fileName){
		return getCurrentDir()+fileName;
	}
	
	/**
	 * This method checks if the wordlist file exist. 	
	 * @return
	 */
	public boolean checkWordList(){
		File f = new File(getAbsolutePath(WORDLIST));
		if(f.exists() && !f.isDirectory()){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This method is called by the wordList class to read in all the words
	 * from a wordlist file. The name of the file can be specified as
	 * the parameter.
	 * @param fileName
	 * @return
	 */
	public List<List<String>> readWordList(){
		String path = getAbsolutePath(WORDLIST);
		String line = null;
		List<List<String>> allLevels = new ArrayList<List<String>>();
		List<String> singleLevel = new ArrayList<String>();
		
		try{
			//Use FileReader to read in files
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader (fileReader);
			
			while ((line = bufferedReader.readLine()) != null) {
				if (!line.trim().equals("")&&!line.trim().startsWith("%")){
					singleLevel.add(line);
				}
				else if (line.trim().startsWith("%")){
					List<String> copy = new ArrayList<String>();
					copy.addAll(singleLevel);
					allLevels.add(copy);
					singleLevel.clear();
				}
			}
			List<String> finalLevel = new ArrayList<String>();
			finalLevel.addAll(singleLevel);
			allLevels.add(finalLevel);
			
			bufferedReader.close();
		}
		catch(FileNotFoundException ex){
			//all files are checked so cannot happen.
			System.out.println("No" + WORDLIST + "i file found" );
			throw new RuntimeException ("File Not Found: wordlist");
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
		return allLevels;
	}
	
	public boolean checkFileExist(String fileName){
		String path = getAbsolutePath(fileName);
		File f = new File (path);
		
		if (f.exists() && !f.isDirectory()){
			return true;
		}
		else{
			return false;
		}
	}
}
