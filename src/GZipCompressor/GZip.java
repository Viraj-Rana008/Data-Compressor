package GZipCompressor;

public class GZip {
	protected String inputFileName, outputFileName;
	protected long inputFileLen, outputFileLen;
	protected String summary;
	
	public GZip() {}
	public GZip(String inputFile) {
		this.inputFileName = inputFile;
		this.outputFileName = inputFile + ".gz";
	}
	public GZip(String inputFile, String outputFile) {
		this.inputFileName = inputFile;
		this.outputFileName = outputFile;
	}
	
	
	public String getSummary() {
		return summary;
	}
}
