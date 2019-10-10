package data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

public class LineCounter extends Task<Double> {
	
	private List<File> filesToCount;
	private DoubleProperty lines = new SimpleDoubleProperty();

	@Override
	protected Double call() throws Exception {
		for(File f: filesToCount) {
			int i =this.countLinesNew(f);
			Platform.runLater(() -> setLines(this.getLines().doubleValue()+i));
			updateValue(getLines().doubleValue());
		}
		return null;
	}

	public LineCounter(List<File> filesToCount) {
		super();
		this.filesToCount = filesToCount;
	}

	public DoubleProperty getLines() {
		return lines;
	}
	
	

	private void setLines(double lines) {
		this.lines.set(lines);
	}
	
	private int countLinesNew(File file) throws IOException{
	    InputStream is = new BufferedInputStream(new FileInputStream(file));
	    try {
	        byte[] c = new byte[1024];

	        int readChars = is.read(c);
	        if (readChars == -1) {
	            // bail out if nothing to read
	            return 0;
	        }

	        // make it easy for the optimizer to tune this loop
	        int count = 0;
	        while (readChars == 1024) {
	            for (int i=0; i<1024;) {
	                if (c[i++] == '\n') {
	                    ++count;
	                }
	            }
	            readChars = is.read(c);
	        }

	        // count remaining characters
	        while (readChars != -1) {
	            for (int i=0; i<readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	            readChars = is.read(c);
	        }

	        return count == 0 ? 1 : count;
	    } finally {
	        is.close();
	    }
	}

}
