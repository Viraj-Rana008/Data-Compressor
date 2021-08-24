package LZWCompressor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

import FileBitIO.FileBitReader;

public class LZWDecoder extends LZW{

	private FileOutputStream fout;
	private BufferedOutputStream out;
	
	public LZWDecoder(){
		super();
	}
	public LZWDecoder(String inputFile){
		super(inputFile);
	}
	public LZWDecoder(String inputFile, String outputFile){
		super(inputFile, outputFile);
	}
	
	
	public boolean decode() throws Exception{
		if(inputFileName.length()==0)
			return false;
		FileBitReader in;
		
		try {
			in = new FileBitReader(inputFileName);
			
			fout = new FileOutputStream(outputFileName);
			out = new BufferedOutputStream(fout);
		}
		catch(Exception e){
			throw e;
		}
		
		try {
			summary = "";
			inputFileLen = in.available();
			
			if(inputFileLen==0)
				throw new Exception("File is Empty !!");
			summary += "Compressed File Size :\t" + inputFileLen + "\n";
			
			Hashtable<Integer, String> table = new Hashtable<Integer, String>();
			
			for(int k=0;k<MAXCHARS; k++) {
				String buf = "" + (char)k;
				table.put(new Integer(k), buf);
			}
			
			String sig = in.getBytes(LZWSignature.length());
			if(!sig.equals(LZWSignature))	
				return false;
			outputFileLen = Long.parseLong(in.getBits(32), 2);
			
			long i = 0;
			int codeUsed = MAXCHARS;
			int encodedCodeWord = 0;
			String prevStr = "", codeWord = "";
			
			//first byte
			encodedCodeWord = Integer.parseInt(in.getBits(MAXBITS), 2);
			String encodedString = table.get(encodedCodeWord);
			out.write(encodedString.getBytes());
			i += encodedString.length();
			codeWord = encodedString;
			
			while(i<outputFileLen) {
				encodedCodeWord = Integer.parseInt(in.getBits(MAXBITS), 2);
				encodedString = table.get(encodedCodeWord);
				
				if(encodedString != null)
					prevStr = encodedString;
				else 
					prevStr = codeWord + codeWord.charAt(0);
				
				for(int j=0; j<prevStr.length(); j++)
					out.write(prevStr.charAt(j));
				
				i += prevStr.length();
				if(codeUsed<MAXCODES) 
					table.put(codeUsed++, codeWord + prevStr.charAt(0));
				
				codeWord = prevStr;
			}
			out.close();
		}
		catch(Exception e) {
			throw e;
		}
		
		outputFileLen = new File(outputFileName).length();
		summary += "Output File Size :\t" + outputFileLen + "\n";
		
		return true;
	}
}
