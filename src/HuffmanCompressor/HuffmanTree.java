package HuffmanCompressor;

import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
	protected HuffmanNode root;
	protected int depth;
	
	public HuffmanTree(HFreqTable freqTable) {
		if(freqTable.numSym==0)
			throw new IllegalArgumentException("Frequency Table is Empty");
		
		PriorityQueue<HuffmanNode> pqueue = new PriorityQueue<HuffmanNode>();
		for(Map.Entry<Integer, Integer> elem : freqTable.entrySet()) {
			pqueue.add(new HuffmanNode(elem.getKey(), elem.getValue()));
		}
		
		while(pqueue.size() > 1) {
			HuffmanNode left = pqueue.poll();
			HuffmanNode right = pqueue.poll();
			pqueue.add(new HuffmanNode(left, right));
		}
		
		root = pqueue.peek();
	}
	
	public HuffmanNode getRoot() {
		return root;
	}
	
	public int getDepth() {
		return treeDepth(root);
	}
	
	private int treeDepth(HuffmanNode root){
		if(root.isLeaf())
			return 1;
		return 1+Math.max(treeDepth(root.getLeft()), treeDepth(root.getRight()));
	}
	
	public void huffmanCode() {
		printCode(root, "");
	}
	
	protected void printCode(HuffmanNode root, String strcode) {
		if(!root.isLeaf()) {
			printCode(root.getLeft(), strcode + "0");
			printCode(root.getRight(), strcode + "1");
		}
		else
			System.out.format("%d     :  %s\n", root.getSymbol(), strcode);
	}
	
}
