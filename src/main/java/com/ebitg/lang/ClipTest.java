package com.ebitg.lang;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipTest {
	public static void main(String[] args) throws UnsupportedFlavorException, IOException {
		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipT = sysClip.getContents(null);
		String content = null;
		if (clipT != null) {
			// ��������Ƿ����ı�����
			if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				content = (String) clipT.getTransferData(DataFlavor.stringFlavor);
			}
		}
		String result = content.replaceAll("(\n|\t|\n\t)+", " ");
		System.out.println(result);
	}
}
