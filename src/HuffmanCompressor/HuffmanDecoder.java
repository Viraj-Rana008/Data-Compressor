package HuffmanCompressor;

import java.util.HashMap;
import java.util.Map;

public final class HuffmanDecoder {
	protected final HFreqTable freqTable;
	protected final HuffmanTree htree;
	protected final Map<Long, Integer> symMap;
	
	public HuffmanDecoder(HFreqTable table) {
		this.freqTable = table;
		htree = new HuffmanTree(table);
		symMap = new HashMap<Long, Integer>();
		buildSymMap(htree.getRoot(), 0, 0);
	}
	
	protected void buildSymMap(HuffmanNode root, int bitString, int length) {
		if(!root.isLeaf()) {
			buildSymMap(root.getLeft(), bitString, length+1);
			buildSymMap(root.getRight(), bitString | (1<<length), length+1);
		}
		else {
			//Huffman stored in 64 bits
			//Lower 32 bits --> huffman code
			//Upper 32 bits --> length of code
			symMap.put((long)bitString | (long)length<<32, root.getSymbol());
		}
	}
	
	public boolean hasCode(int bitString, int length) {
		long key = (long)bitString | 1<<length;
		return symMap.containsKey(key);
	}
	
	public int decode(int bitString, int length) {
		long key = (long)bitString | 1<<length;
		return symMap.get(key);
	}
	
	public HuffmanTree getTree() {
		return htree;
	}
}
