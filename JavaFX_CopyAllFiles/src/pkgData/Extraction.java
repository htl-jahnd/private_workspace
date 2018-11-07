package pkgData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javafx.concurrent.Task;

public class Extraction extends Task<ArrayList<File>> {
	
	private ArrayList<File> filesToCopy=new ArrayList<File>();
	private int editedFiles;
	private String src;
	private String dest;
	
	
	public int getFileAmmount() {
		filesToCopy= new ArrayList<File>();
		getAllFiles(src);
		return filesToCopy.size() ; // 2 arbeitsschritte --> deswegen in progressbar auch doppelte file anzahl
	}

	
	public Extraction(String src1, String dest1) {
		src = src1;
		dest = dest1;
	}
	
	private void getAllFiles(String directoryName) {
	    File directory = new File(directoryName);
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	        		filesToCopy.add(file);
	        } else if (file.isDirectory()) {
	            getAllFiles(file.getAbsolutePath());
	        }
	    }
	}
	
	private void copyFiles(String directoryTo) throws IOException {
		Path dest = Paths.get(directoryTo);
		for(File file : filesToCopy) {
			Files.copy(file.toPath(), dest.resolve(file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
			editedFiles++;
			this.updateProgress(editedFiles, filesToCopy.size());
		}		
	}
	


	public int getEditedFiles() {
		return editedFiles;
	}


	@Override
	protected ArrayList<File> call() throws Exception {
		ArrayList<File> copied=new ArrayList<File>();
		editedFiles=0;
		filesToCopy=new ArrayList<File>();
		getAllFiles(src);
		Path dest1 = Paths.get(dest);
		for(File file : filesToCopy) {
			this.updateMessage("Copying: " + file.getAbsolutePath());
			Files.copy(file.toPath(), dest1.resolve(file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
			editedFiles++;
			copied.add(file);
			this.updateProgress(editedFiles, filesToCopy.size());
		}	
		this.updateMessage("Copying finished");
		return copied;
	}

}
