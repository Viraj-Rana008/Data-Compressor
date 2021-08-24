package HuffmanCompressor;

public class HuffmanNode implements Comparable<HuffmanNode>{
	
	private int sym;	//symbol
	private int freq;	//frequency
	private boolean leaf;
	private HuffmanNode left;
	private HuffmanNode right;
	private HuffmanNode parent;
	
	public HuffmanNode(int sym, int freq) {
		this.sym = sym;
		this.freq = freq;
		leaf = true;
	}
	
	public HuffmanNode(HuffmanNode left, HuffmanNode right) {
		this.freq = left.freq + right.freq;
		leaf = false;
		this.left = left;
		this.right = right;
	}
	
	public void setParent(HuffmanNode parent) {
		this.parent = parent;
	}
	
	public boolean isLeaf() {
		return leaf;
	}
	
	public int getSymbol() {
		return this.sym;
	}
	
	public int getFreq() {
		return this.freq;
	}
	
	public HuffmanNode getLeft() {
		return left;
	}

	public HuffmanNode getRight() {
		return right;
	}

	public HuffmanNode getParent() {
		return parent;
	}
	
	public String toString() {
		String res = "[F: " + freq + "; Leaf: " + leaf;
		if(leaf)
			res += String.format("; C: %c", sym);
		return res + "]";
	}

	@Override
	public int compareTo(HuffmanNode node) {
		if(freq > node.freq)
			return 1;
		else if(freq < node.freq)
			return -1;
		else 
			return 0;
	}

}
