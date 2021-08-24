package GZipCompressor;

import java.io.*;
import java.util.zip.*;

public class GZipDecoder extends GZip {
	
	public GZipDecoder() {
		super();
	}
	public GZipDecoder(String inputFile) {
		super(inputFile);
	}
	public GZipDecoder(String inputFile, String outputFile) {
		super(inputFile, outputFile);
	}
	
	
	/* decodes using GZIPOutputStream API
	 * @return 	true on success
	 * 			false on failure
	 * */
	public boolean decode() throws Exception{
		try {
			inputFileLen = new File(inputFileName).length();
			GZIPInputStream in = new GZIPInputStream(new FileInputStream(inputFileName));
			OutputStream out = new FileOutputStream(outputFileName);
			
			byte[] buf = new byte[1024];
			int len;
			while((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			
			in.close();
			out.close();
			outputFileLen = new File(outputFileName).length();
			summary += "Compressed File Size :\t" + inputFileLen + "\n";
			summary += "Original   File Size :\t" + outputFileLen + "\n";
		}
		catch(Exception e) {
			throw new Exception("File Access Failed :(");
		}
		return true;
	}	
}
