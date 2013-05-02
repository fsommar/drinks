package com.inda.drinks.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Web {

	public static void download(String url, File file) throws IOException {
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;
		try {
			URL dl = new URL(url);
			rbc = Channels.newChannel(dl.openStream());
			fos = new FileOutputStream(file);
			fos.getChannel().transferFrom(rbc, 0, 1 << 24); // 2^24 ~= 16 MB
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (rbc != null) {
				rbc.close();
			}
		}
	}

}
