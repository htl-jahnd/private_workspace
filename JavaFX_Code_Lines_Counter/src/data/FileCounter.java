package data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;

public class FileCounter extends Task<List<File>> {

	private File directory;
	private List<String> extensions;
	private final DoubleProperty doubleProp = new SimpleDoubleProperty();
	private final List<File> files = new ArrayList<File>();

	@Override
	protected List<File> call() throws Exception {
		searchInDir(directory);
		return files;
	}

	private void searchInDir(File directory) {
		for (File f : directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				for(String ext: extensions) {
					if(name.toLowerCase().endsWith(ext) || (new File(dir,name).isDirectory())){
						return true;
					}
				}
				return false;
			}
		})) {
			if (f.isDirectory()) {
				searchInDir(f);
			} else {
				files.add(f);
				
				updateValue(files);
				Platform.runLater(() -> setDoubleProp(getDoubleProp().add(1).doubleValue()));
			}
		}
	}

	public FileCounter(File directory, List<String> ext) {
		super();
		this.directory = directory;
		this.extensions=ext;
	}

	public DoubleProperty getDoubleProp() {
		return doubleProp;
	}

	private void setDoubleProp(double val) {
		doubleProp.set(val);
	}
}
