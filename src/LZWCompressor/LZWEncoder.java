package LZWCompressor;

import java.util.*;
import java.io.*;
import FileBitIO.FileBitWriter;

public class LZWEncoder extends LZW{
	
	private FileInputStream fin;
	private BufferedInputStream in;
	
	public LZWEncoder() {
		super();
	}
	public LZWEncoder(String inputFile) {
		super(inputFile);
	}
	public LZWEncoder(String inputFile, String outputFile) {
		super(inputFile, outputFile);
	}
	
	public boolean encode() throws Exception{
		
		if(inputFileName.length()==0)
			return false;
		FileBitWriter out;
		
		try {
			fin = new FileInputStream(inputFileName);
			in = new BufferedInputStream(fin);
			
			out = new FileBitWriter(outputFileName);
		}
		catch(Exception e) {
			throw e;
		}
		
		try {
			summary = "";
			inputFileLen = in.available();
			if(inputFileLen == 0)
				throw new Exception("File is Empty !!");
			summary += "Original File Size :\t" + inputFileLen + "\n";
			
			Hashtable<String, Integer> table = new Hashtable<String, Integer>();
			
			for(int k=0; k<MAXCHARS;k++){
				String buf = "" + (char)k;
				table.put(buf, new Integer(k));
			}
			
			out.putString(LZWSignature);
			out.putBits(leftPadder(Long.toString(inputFileLen, 2), 32));
			
			long i = 0;
			int codesUsed = MAXCHARS;
			
			int currentCh = 0;
			String prevStr = "";
			
			while(i<inputFileLen) {
				currentCh = in.read();
				i++;
				String temp = prevStr + (char)currentCh;
				Integer e = table.get(temp);
				
				if(e==null) {
					if(codesUsed < MAXCHARS)
						table.put(temp, codesUsed++);
					Integer encoded = table.get(prevStr);
					if(encoded!=null){
						String wri = leftPadder(Integer.toString(encoded.intValue(), 2), MAXBITS);
						out.putBits(wri);
					}
					prevStr = "" + (char)currentCh;
				}
				else {
					prevStr = temp;
				}
			}
			
			Integer encoded = table.get(prevStr);
			if(encoded!=null) {
				String wri = leftPadder(Integer.toString(encoded.intValue(), 2), MAXBITS);
				out.putBits(wri);
			}
			
			out.closeFile();
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		
		outputFileLen  = new File(outputFileName).length();
		float cratio = (float)((outputFileLen/inputFileLen)*100);
		summary += "Compressed File Size :\t" + outputFileLen + "\n";
		summary += "Compression Ratio    :\t" + cratio + "\n";
		
		return true;
	}
	
}
