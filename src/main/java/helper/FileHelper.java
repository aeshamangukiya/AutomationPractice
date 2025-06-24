package helper;

import java.io.File;

public class FileHelper {

	public static void createFolderNotExists(String targetFolderPath) {
		File folder = new File(targetFolderPath);
		if (!folder.exists()) {
			folder.mkdirs(); // Create folder if it does not exist
		}
	}

}
