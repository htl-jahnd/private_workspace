package pkgData;


import javafx.scene.control.ProgressBar;
import pkgData.Extraction;

public class BarThread extends Thread {
	  ProgressBar progressBar;
	  Extraction ex;

	  public BarThread(ProgressBar bar, Extraction ex1) {
	    progressBar = bar;
	    ex=ex1;
	  }

	  public void run() {
		  int minimum=0;
		  int maximum =ex.getFileAmmount();
		  for (int i = minimum; i < maximum; i++) {
		      progressBar.setProgress(ex.getEditedFiles());
		   }
	}
}

