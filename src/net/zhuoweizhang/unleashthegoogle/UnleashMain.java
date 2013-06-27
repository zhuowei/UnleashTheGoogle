package net.zhuoweizhang.unleashthegoogle;

import java.io.*;
import java.util.*;
import java.nio.charset.Charset;

public class UnleashMain {

	public static byte[] finderPattern = "debug_features_level ".getBytes(Charset.forName("UTF-8"));

	/** 
	 * @param prefFile the ProtoBuf preference file to be edited
	 * @param newLevel the new LogLevel
	 */
	public static void unleash(File prefFile, int newLevel) throws Exception {
		byte[] fileBytes = readFile(prefFile);
		for (int i = 0; i < fileBytes.length; i++) {
			boolean found = true;
			for (int j = 0; j < finderPattern.length; j++) {
				int index = i + j;
				if (index >= fileBytes.length || fileBytes[index] != finderPattern[j]) {
					found = false;
					break;
				}
			}
			if (!found) continue;
			System.out.println("index: " + i);
			int offsetIndex = i + finderPattern.length;
			System.out.println("offsetIndex: " + offsetIndex + ":" + fileBytes[offsetIndex]);
			fileBytes[offsetIndex] = (byte) newLevel;
			writeFile(prefFile, fileBytes);
			break;
		}
			
	}

	public static byte[] readFile(File patchf)
			throws IOException {
		byte[] ret = new byte[(int) patchf.length()];
		InputStream is = new FileInputStream(patchf);
		is.read(ret, 0, ret.length);
		is.close();
		return ret;
	}

	public static void writeFile(File file, byte[] buf) throws IOException {
		OutputStream os = new FileOutputStream(file);
		os.write(buf);
		os.close();
	}

	public static void main(String[] args) {
		try {
			unleash(new File(args[0]), Integer.parseInt(args[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
