package HuffmanCompressor;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HuffmanOutputStream extends FilterOutputStream {

	protected byte[] segment;
	protected int bytesWritten;
	
	protected HuffmanOutputStream(OutputStream out) {
		super(out);
		segment = new byte[HuffmanConstant.DEFAULT_SEGMENT_SIZE_KB * 1024];
		bytesWritten = 0;
	}

	protected HuffmanOutputStream(OutputStream out, int bufSizeKB) {
		super(out);
		segment = new byte[bufSizeKB * 1024];
		bytesWritten = 0;
	}
	
	public int getBufSize() {
		return segment.length;
	}
	
	@Override
	public void write(int b) throws IOException {
		segment[bytesWritten++] = (byte)b;
		if(bytesWritten == segment.length)
			writeSegment();
	}

	@Override
	public void flush() throws IOException {
		writeSegment();
	}
	
	private void writeSegment() throws IOException {
		if(bytesWritten==0)
			return;
		
		HFreqTable freqTable = new HFreqTable();
		for(int i=0; i<bytesWritten; i++)
			freqTable.add(segment[i]);
		
		//writing segment header
		DataOutputStream dataOut = new DataOutputStream(out);
		dataOut.writeInt(HuffmanConstant.HUFFMAN_MAGIC1);
		dataOut.writeInt(bytesWritten);
		freqTable.save(dataOut);
		dataOut.writeInt(HuffmanConstant.HUFFMAN_MAGIC2);
		
		//writing coded data
		BitOutputStream bitout = new BitOutputStream(out);
		HuffmanEncoder encoder = new HuffmanEncoder(freqTable);
		for(int i=0; i<bytesWritten; i++) {
			Pair<Integer, Integer> bstr = encoder.encode(segment[i]);
			bitout.write(bstr.first, bstr.second);
		}
		
		bitout.flush();
		bytesWritten = 0;
		
	}

}
