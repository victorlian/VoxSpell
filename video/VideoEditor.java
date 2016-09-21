package video;

import javax.swing.SwingWorker;

import spellingAid.FileManager;

/**
 * FFMPEG code is added, this class would create an FFMPEG video if video does not already exist.
 * The video created would be placed in the same directory as the old video.
 * User can then decide which video to play.
 * Generating the video is in background when the application got started. (SwingWorker)
 *
 * @author victor 
 */

public class VideoEditor extends SwingWorker<Void,Void>{
	
	private FileManager _fm = new FileManager();
	
	/**
	 * This method will execute in background to generate the video modified by FFMPEG
	 * if the video is not found. 
	 */
	@Override
	protected Void doInBackground() throws Exception {
		String filename = _fm.VIDEO;
        String path = _fm.getAbsolutePath(filename);
        
        String outputName = _fm.REVERSEVIDEO;
        String outputPath = _fm.getAbsolutePath(outputName);
                
        ProcessBuilder builder = new ProcessBuilder ("/bin/bash", "-c", "ffmpeg -i " + path + " -vf reverse -af areverse " + outputPath);
		Process process = builder.start();
		process.waitFor();
		
		return null;
	}
	
	/**
	 * This method will be called when the video has finished being created or 
	 * is already existing.
	 */
	@Override
	protected void done(){
		VideoReward.getInstance().setEditedVideoAvaliable();
	}

}
