package fileIO;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * This class manages all interactions with files.
 * (Some Code was reused and modified from assignment 2)
 * 
 * Responsibility: 
 * 1. check if wordlist exist
 * 2. read in the entire wordList file.
 * 3. get the path of current directory
 * 4. check if the video reward file exist
 * 5. Provide methods for reading in images.
 * 
 * @author victor
 *
 */
public class FileManager {
	public final String WORDLIST = "NZCER-spelling-lists.txt";
	public final String VIDEO = "big_buck_bunny_1_minute.avi";
	public final String REVERSEVIDEO = "reversed.avi";
	
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
	 * This is a method for getting the absolute path of a file
	 * provided the string of the folderName.
	 * @param fileName
	 * @param folderName
	 * @return
	 */
	public String getAbsolutePath(String fileName, String folderName){
		return folderName + fileName;
	}
	
	/**
	 * This method checks if the default wordlist file exist. 	
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
	public List<List<String>> readWordList(String path){
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
	
	/**
	 * This method is intended to be called to verify the new input file
	 * meets all the requirements.
	 * 
	 * The wordlist must have at least 10 words in each level, 
	 * and exactly 11 levels. (due to the way it is stored, 
	 * it will have 12 elements, the first one being blank)
	 * @return
	 */
	public boolean validateWordlist(List<List<String>> wordlist){
		if (wordlist.size()!=12){
			return false;
		}
		else {
			for (List<String> individualLevel: wordlist){
				if (individualLevel.size()<10 && wordlist.indexOf(individualLevel)!=0){
					return false;
				}
			}
		}
		return true;
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
	
	public BufferedImage readInImage(String imageName){
		String imageDir = getCurrentDir()+ "images/";
		String imagePath = getAbsolutePath(imageName,imageDir);
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File(imagePath));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return img;
	}
}
