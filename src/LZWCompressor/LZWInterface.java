package LZWCompressor;

public interface LZWInterface {
	final int MAXCHARS = 256;
	final String strExtension = ".lzw";
	final String LZWSignature = "LZW";
	final int MAXCODES = 4096;
	final int MAXBITS = 12;
}
