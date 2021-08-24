package HuffmanCompressor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class HFreqTable {
	protected LinkedHashMap<Integer, Integer> freqTable;
	protected int numSym;
	
	public HFreqTable(){
		freqTable = new LinkedHashMap<Integer, Integer>();
		numSym = 0;
	}
	
	protected HFreqTable(LinkedHashMap<Integer, Integer> map, int num) {
		freqTable = map;
		numSym = num;
	}
	
	/* Add code to table
	 * */
	public void add(int code) {
		int freq = 0;
		if(freqTable.containsKey(code))
			freq = freqTable.get(code);
		freqTable.put(code, freq+1);
		numSym++;
	}
	
	/* Get frequency of code
	 * */
	public int getFreq(int code) {
		if(!freqTable.containsKey(code))
			return -1;
		return freqTable.get(code);
	}
	
	/* Get entry set of frequency table
	 * */
	public Set<Map.Entry<Integer, Integer>> entrySet(){
		return freqTable.entrySet();
	}
	
	public int getNumSymbols() {
		return numSym;
	}
	
	/* Prints the table in standard output
	 * */
	public void dump() {
		System.out.println("=========Frequency Table==========");
		System.out.println("Code        Frequency");
		for(Map.Entry<Integer, Integer> elem : entrySet()) {
			System.out.format("%04d       %9d\n", elem.getKey(), elem.getValue());
		}
	}
	
	
	/* Saves the table in binary form in given output stream
	 * @param out : DataOutputStream where to store table
	 * */
	public void save(DataOutputStream out) throws  IOException{
		out.writeInt(numSym);
		out.writeInt(freqTable.size());
		for(Map.Entry<Integer, Integer> elem : entrySet()) {
			out.writeInt(elem.getKey());
			out.writeInt(elem.getValue());
		}
	}
	
	
	/* Restore frequency table stored in given InputStream
	 * @return restored HFreqTable
	 **/
	public static HFreqTable restore(DataInputStream in) throws IOException{
		try {
			int numSym = in.readInt();
			int tableSize = in.readInt();
			LinkedHashMap<Integer,Integer> map = new LinkedHashMap<Integer, Integer>();
			for(int read=0; read<tableSize; read++)
				map.put(in.readInt(), in.readInt());
			
			return new HFreqTable(map, numSym);
		}
		catch(EOFException e) {
			throw new IOException("Table in Wrong Format");
		}
	}
	
	@Override
	public boolean equals(Object map) {
		HFreqTable table = (HFreqTable)map;
		if(numSym != table.getNumSymbols())
			return false;
		return freqTable.equals(table.freqTable);
	}
	
}
