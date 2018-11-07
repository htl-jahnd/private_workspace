package GenericBinaryFileSaver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FunWithFiles<T> {
	private T typeToSave;
	
	public FunWithFiles() {
	}
	public T getTypeToSave() {
		return typeToSave;
	}

	public void setTypeToSave(T typeToSave) {
		this.typeToSave = typeToSave;
	}
	
	public void writeBin(String path, T content) throws IOException {
		FileOutputStream fs = new FileOutputStream(path);
		ObjectOutputStream os = new ObjectOutputStream(fs);
		os.writeObject(content);
		os.flush();
 		os.close();
 		fs.close();
	}
	
	public T readBin(String path) throws IOException, ClassNotFoundException {
		FileInputStream fs = new FileInputStream(path);
		ObjectInputStream os = new ObjectInputStream(fs);
		T returnValue=(T)os.readObject();
 		os.close();
 		fs.close();
 		return returnValue;
	}
	
	


}
