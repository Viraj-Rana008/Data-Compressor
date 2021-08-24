package userInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import HuffmanCompressor.*;
import GZipCompressor.*;
import LZWCompressor.*;

public class GphWorkingDlg extends JDialog implements ActionListener, Constants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1465315082670668386L;
	private JFrame owner;
	private JProgressBar prgBar;
	private JButton btnCancel;
	private JLabel lblNote;
	private String gSummary = "";
	private String iFilename, oFilename;
	private boolean bCompress = false;
	private int algoSelected;

	public GphWorkingDlg(JFrame parent) {

		super(parent, true);
		owner = parent;
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				setTitle("Thwarted user attempt to close window.");
			}
		});

		setSize(300, 120);
		centerWindow();
		buildDlg();
		setResizable(false);
		btnCancel.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

	private void buildDlg() {
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		setLayout(gridbag);
		prgBar = new JProgressBar();
		prgBar.setSize(100, 30);
		prgBar.setStringPainted(false);
		prgBar.setIndeterminate(true);
		btnCancel = new JButton("Cancel");
		lblNote = new JLabel("hahah", JLabel.CENTER);

		constraints.insets = new Insets(3, 3, 3, 3);

		buildConstraints(constraints, 1, 0, 2, 1, 50, 30);
		gridbag.setConstraints(lblNote, constraints);
		add(lblNote);
		buildConstraints(constraints, 0, 1, 4, 1, 100, 40);
		gridbag.setConstraints(prgBar, constraints);
		add(prgBar);
		buildConstraints(constraints, 1, 2, 2, 1, 50, 30);
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(btnCancel, constraints);
		add(btnCancel);

	}

	private void buildConstraints(GridBagConstraints constraints, int gx, int gy, int gw, int gh, int wx, int wy) {
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = gx;
		constraints.gridy = gy;
		constraints.gridwidth = gw;
		constraints.gridheight = gh;
		constraints.weightx = wx;
		constraints.weighty = wy;
	}

	private void centerWindow() {
		// TODO Auto-generated method stub

	}

	public void doWork(String inputFileName, String outputFileName, int mode, int algorithm) {
		File infile = new File(inputFileName);

		if (!infile.exists()) {
			gSummary += "File Does not Exits!\n";
			return;
		}
		bCompress = (mode == COMPRESS);
		if (bCompress)
			lblNote.setText("Compressing " + infile.getName());
		else {
			lblNote.setText("Decompressing " + infile.getName());

		}
		setTitle(lblNote.getText());

		final int algo = algorithm;
		iFilename = inputFileName;
		oFilename = outputFileName;
		gSummary = "";

		final Runnable closeRunner = new Runnable() {
			public void run() {
				setVisible(false);
				dispose();
			}
		};

		System.out.println(algo);

		Runnable workingThread = new Runnable() {
			public void run() {
				try {
					boolean success = false;
					switch (algo) {
					case HUFFMAN:
						if (bCompress) {
							// HuffmanEncoder huff = new HuffmanEncoder(iFileName, oFileName);
						}
						break;

					case GZIP:
						if (bCompress) {
							GZipEncoder gzip = new GZipEncoder(iFilename, oFilename);
							success = gzip.encode();
							gSummary += gzip.getSummary();
						} else {
							GZipDecoder gzip = new GZipDecoder(iFilename, oFilename);
							success = gzip.decode();
							gSummary += gzip.getSummary();
						}
						break;

					case LZW:
						if (bCompress) {
							LZWEncoder lzw = new LZWEncoder(iFilename, oFilename);
							success = lzw.encode();
							gSummary += lzw.getSummary();
						} else {
							LZWDecoder lzw = new LZWDecoder(iFilename, oFilename);
							success = lzw.decode();
							gSummary += lzw.getSummary();
						}
						break;
					}
				} catch (Exception e) {
					gSummary += e.getMessage();
				}

				try {
					SwingUtilities.invokeAndWait(closeRunner);
				} catch (Exception e) {
					gSummary += "\n" + e.getMessage();
				}
			}
		};

		Thread work = new Thread(workingThread);
		work.start();

		setVisible(true);
	}

	public String getSummary() {
		if (gSummary.length() > 0) {
			String line = "------------------------------";
			return line + "\n" + gSummary + line;
		} else
			return "";
	}

}
