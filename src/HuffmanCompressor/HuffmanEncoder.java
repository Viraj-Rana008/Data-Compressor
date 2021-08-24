package HuffmanCompressor;

import java.util.HashMap;
import java.util.Map;

public final class HuffmanEncoder {
	protected final HuffmanTree htree;
	protected final HFreqTable freqTable;
	protected final Map<Integer, Long> codeMap;
	
	HuffmanEncoder(HFreqTable table){
		this.freqTable = table;
		htree = new HuffmanTree(table);
		codeMap = new HashMap<Integer, Long>();
		buildCodeMap(htree.getRoot(), 0, 0);
	}
	
	/* Maps symbol with huffman code value	
	 * */
	protected void buildCodeMap(HuffmanNode root, int bitString, int length) {
		if(!root.isLeaf()) {
			buildCodeMap(root.getLeft(), bitString, length+1);
			buildCodeMap(root.getRight(), bitString | (1<<length), length+1);
		}
		else {
			//Huffman code store in 64 bits
			//Lower 32 bits --> huffman code
			//Upper 32 bits --> length of code
			long code = (long)length<<32 | bitString;
			codeMap.put(root.getSymbol(), code);
		}
	}
	
	public Pair<Integer, Integer> encode(int symbol){
		long code = codeMap.get(symbol);
		return new Pair<Integer, Integer>((int)code, (int)(code>>32));
	}
	
	public final HuffmanTree getTree() {
		return htree;
	}
	
}
