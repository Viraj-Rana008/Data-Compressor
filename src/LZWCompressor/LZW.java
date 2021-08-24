package LZWCompressor;

public class LZW implements LZWInterface{
	
	protected String inputFileName, outputFileName;
	protected long inputFileLen, outputFileLen;
	protected String summary;
	
	public LZW(){}
	public LZW(String inputFile){
		inputFileName = inputFile;
		outputFileName = inputFile + strExtension;
	}
	public LZW(String inputFile, String outputFile){
		inputFileName = inputFile;
		outputFileName = outputFile;
	}
	
	protected String leftPadder(String text, int n) {
		while(text.length()<n) {
			text += "0" + text;
		}
		return text;
	}
	
	public String getSummary() {
		return summary;
	}
}
