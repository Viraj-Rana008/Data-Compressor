package GZipCompressor;

import java.io.*;
import java.util.zip.*;

public class GZipEncoder extends GZip{
	
	public GZipEncoder() {
		super();
	}
	public GZipEncoder(String inputFile) {
		super(inputFile);
	}
	public GZipEncoder(String inputFile, String outputFile) {
		super(inputFile, outputFile);
	}
	
	/* encodes using GZIPOutputStream API
	 * @return 	true on success in compression
	 * 			false on failure 
	 * */
	@SuppressWarnings("resource")
	public boolean encode() throws Exception{
		if(inputFileName.length()==0)
			return false;
		
		try {
			FileInputStream in = new FileInputStream(inputFileName);
			GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(outputFileName));
			
			inputFileLen = in.available();
			
			if(inputFileLen==0)		throw new Exception("Source File Empty!");
			
			summary += "Original Size :\t" + inputFileLen + "\n";
			
			byte[] buf = new byte[1024];
			int len;
			while((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			
			in.close();
			out.finish();
			out.close();
			
			outputFileLen = new File(outputFileName).length();
			float cratio = (float)((outputFileLen/inputFileLen)*100);
			summary += "Compressed File Size :\t" + outputFileLen + "\n";
			summary += "Compression Ratio    :\t" + cratio + "\n";
			
		} catch (FileNotFoundException e) {
			throw new Exception("No such File Found :(");
			//e.printStackTrace();
		} catch (IOException e) {
			throw new Exception("File Access Failed :(");
			//e.printStackTrace();
		}
		return true;
		
	}
}
