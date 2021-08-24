package userInterface;

public interface Constants {
	String[] algorithmNames = {"Huffman Compression", "GZip Compression", "LZW Compression"};
	String[] extensions = {".lzw", ".huf", ".gz"};
	
	final int LZW = 0;
	final int GZIP = 1;
	final int HUFFMAN = 2;
	
	final int COMPRESS = 32;
	final int DECOMPRESS = 33;
}
