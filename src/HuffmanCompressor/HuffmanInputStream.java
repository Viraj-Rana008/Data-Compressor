package HuffmanCompressor;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HuffmanInputStream extends FilterInputStream {
	protected byte[] segment;
	protected int byteRead;
	protected boolean eof;
	
	protected HuffmanInputStream(InputStream in) {
		super(in);
		byteRead = 0;
		segment = null;
		eof = false;
	}
	
	@Override
	public int read() throws IOException{
		if(segment==null)
			readSegment();
		if(segment==null && eof)
			return -1;
		
		int ret = segment[byteRead++];
		if(byteRead==segment.length) {
			byteRead = 0;
			segment = null;
		}
		
		return (ret & 0xff);
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException{
		int rdLen;
		for(rdLen=0; rdLen<len; rdLen++) {
			int val = read();
			if(val==-1) {
				if(rdLen==0)
					return -1;
				break;
			}
			b[off+rdLen] = (byte)val;
		}
		return rdLen;
	}

	private void readSegment() throws IOException {
		DataInputStream dataIn = new DataInputStream(in);
		try {
			int magic = dataIn.readInt();
			if(magic != HuffmanConstant.HUFFMAN_MAGIC1)
				throw new IOException("Can't read segemnt header, magic1 mismacth");
			
			int segSz = dataIn.readInt();
			segment = new byte[segSz];
			HFreqTable freqTable = HFreqTable.restore(dataIn);
			
			magic = dataIn.readInt();
			if(magic != HuffmanConstant.HUFFMAN_MAGIC2) 
				throw new IOException("Can't read segemnt header, magic2 mismacth");
			
			//decode segemnt
			HuffmanDecoder decoder = new HuffmanDecoder(freqTable);
			BitInputStream bitin = new BitInputStream(in);
			int bytesDecoded = 0;
			while(bytesDecoded < segSz) {
				int bitString = 0, length = 0;
				
				while(!decoder.hasCode(bitString, length)) {
					int bit = bitin.readBit();
					if(bit==-1)
						break;
					bitString |= (bit<<length);
					length++;
					if(length>=32)
						throw new IOException("Huffman code is too long !");
				}
				
				if(length!=0)
					segment[bytesDecoded++] = (byte)decoder.decode(bitString, length);
				else
					eof = true;
			}
		}
		catch(EOFException e) {
			eof = true;
		}
		
	}

}
