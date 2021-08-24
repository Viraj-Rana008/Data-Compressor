package HuffmanCompressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1)
			usage();

		try {
			if (args[0].equals("enc"))
				doEncode(args);
			else if (args[0].equals("dec"))
				doDecode(args);
			else
				usage();
		} catch (Exception e) {
			System.err.println("Error : " + e.toString());
			usage();
		}
		System.exit(0);
	}

	private static void doDecode(String[] args) throws Exception {
		if (args.length < 3)
			usage();

		File inFile = new File(args[1]);
		File outFile = new File(args[2]);
		HuffmanInputStream hin = new HuffmanInputStream(new FileInputStream(inFile));
		OutputStream out = new FileOutputStream(outFile);
		byte buf[] = new byte[4096];
		int len;

		while ((len = hin.read(buf)) != -1)
			out.write(buf, 0, len);

		hin.close();
		out.close();
		System.out.println("Decompression: done");
		System.out.println("Original file size:     " + inFile.length());
		System.out.println("Decompressed file size: " + outFile.length());

	}

	private static void doEncode(String[] args) throws Exception {
		if (args.length < 3)
			usage();

		File inFile = new File(args[1]);
		File outFile = new File(args[2]);
		InputStream in = new FileInputStream(inFile);
		HuffmanOutputStream hout = new HuffmanOutputStream(new FileOutputStream(outFile));
		byte buf[] = new byte[4096];
		int len;

		while ((len = in.read(buf)) != -1)
			hout.write(buf, 0, len);

		in.close();
		hout.close();

		System.out.println("Compression: done");
		System.out.println("Original file size:     " + inFile.length());
		System.out.println("Compressed file size:   " + outFile.length());
		System.out.print("Compression efficiency: ");
		if (inFile.length() > outFile.length()) {
			System.out.format("%.2f%%\n", (100.0 - (((double) outFile.length() / (double) inFile.length()) * 100)));
		} else
			System.out.println("none");

	}

	private static void usage() {
		System.err.println("USAGE: HuffmanDemo enc|dec");
		System.err.print("       enc <input-file> <output-file>: " + "encode input file and save");
		System.err.println("" + "the results to output file");
		System.err.print("       dec <input-file> <output-file>: " + "decode input file and save");
		System.err.println("" + "the results to output file");
		System.exit(1);
	}

}
