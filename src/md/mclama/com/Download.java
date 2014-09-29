package md.mclama.com;

//Author kevin esche from http://stackoverflow.com/questions/14069848/download-a-file-while-also-updating-a-jprogressbar


import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

// This class downloads a file from a URL.
class Download extends Observable implements Runnable {

// Max size of download buffer.
private static final int MAX_BUFFER_SIZE = 1024;

// These are the status names.
public static final String STATUSES[] = {"Downloading",
"Paused", "Complete", "Cancelled", "Error"};

// These are the status codes.
public static final int DOWNLOADING = 0;
public static final int PAUSED = 1;
public static final int COMPLETE = 2;
public static final int CANCELLED = 3;
public static final int ERROR = 4;

private URL url; // download URL
private int size; // size of download in bytes
private int downloaded; // number of bytes downloaded
private int status; // current status of download
private String gamePath;
private ModManager McLauncher;
private Console con;

// Constructor for Download.
public Download(URL url, ModManager McLauncher) {
    this.url = url;
    size = -1;
    downloaded = 0;
    status = DOWNLOADING;
    //gamePath = McLauncher.gamePath;
    gamePath = System.getProperty("java.io.tmpdir");
    this.McLauncher = McLauncher;
    con = McLauncher.con;

    // Begin the download.
    download();
}

// Get this download's URL.
public String getUrl() {
    return url.toString();
}

// Get this download's size.
public int getSize() {
    return size;
}

// Get this download's progress.
public float getProgress() {
    return ((float) downloaded / size) * 100;
}

// Get this download's status.
public int getStatus() {
    return status;
}

// Pause this download.
public void pause() {
    status = PAUSED;
    stateChanged();
    con.log("log","download paused");
}

// Resume this download.
public void resume() {
    status = DOWNLOADING;
    stateChanged();
    download();
}

// Cancel this download.
public void cancel() {
    status = CANCELLED;
    stateChanged();
    McLauncher.CurrentlyDownloading=false;
    con.log("log","download canceled");
}

// Mark this download as having an error.
private void error() {
    status = ERROR;
    stateChanged();
    con.log("Error","Download error");
}

// Start or resume downloading.
private void download() {
	con.log("log","Starting download.");
	McLauncher.lblDownloadModInfo.setText("Downloading...");
	McLauncher.pBarDownloadMod.setValue(0);
	McLauncher.pBarExtractMod.setValue(0);
    Thread thread = new Thread(this);
    thread.start();
}

// Get file name portion of URL.
private String getFileName(URL url) {
    String fileName = url.getFile();
    return fileName.substring(fileName.lastIndexOf('/') + 1);
}

// Download file.
public void run() {
    RandomAccessFile file = null;
    InputStream stream = null;

    try {
        // Open connection to URL.
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        // Specify what portion of file to download.
        connection.setRequestProperty("Range",
                "bytes=" + downloaded + "-");

        // Connect to server.
        connection.connect();

        // Make sure response code is in the 200 range.
        if (connection.getResponseCode() / 100 != 2) {
            error();
        }

        // Check for valid content length.
        int contentLength = connection.getContentLength();
        if (contentLength < 1) {
            error();
        }

  /* Set the size for this download if it
     hasn't been already set. */
        if (size == -1) {
            size = contentLength;
            stateChanged();
        }

        // Open file and seek to the end of it.
        //file = new RandomAccessFile(getFileName(url), "rw");
        file = new RandomAccessFile(gamePath+"/"+getFileName(url), "rw");
        
        file.seek(downloaded);

        stream = connection.getInputStream();
        while (status == DOWNLOADING) {
    /* Size buffer according to how much of the
       file is left to download. */
            byte buffer[];
            if (size - downloaded > MAX_BUFFER_SIZE) {
                buffer = new byte[MAX_BUFFER_SIZE];
            } else {
                buffer = new byte[size - downloaded];
            }

            // Read from server into buffer.
            int read = stream.read(buffer);
            if (read == -1)
                break;

            // Write buffer to file.
            file.write(buffer, 0, read);
            downloaded += read;
            stateChanged();
            McLauncher.pBarDownloadMod.setValue((int) getProgress());
            McLauncher.lblDownloadModInfo.setText("Downloading... " + displaySize(downloaded) + "/" + displaySize(size));
        }

  /* Change status to complete if this point was
     reached because downloading has finished. */
        if (status == DOWNLOADING) {
            status = COMPLETE;
            stateChanged();
        }
    } catch (Exception e) {
        error();
    } finally {
        // Close file.
        if (file != null) {
            try {
                file.close();
                file=null;
                con.log("log","download finished. Extracting now.");
                McLauncher.lblDownloadModInfo.setText("Extracting...");
            	UnZip zip = new UnZip();
            	con.log("Log",gamePath+"/"+getFileName(url));
            	zip.unZipIt(McLauncher,gamePath+"/"+getFileName(url),McLauncher.modPath);

            	//File f = new File(gamePath+"\\"+getFileName(url));
            	//f.delete();
            } catch (Exception e) {}
        }

        // Close connection to server.
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {}
        }
    }
}

private String displaySize(int iSize){
	int divby =0;
	
	while(iSize>=1024){
		iSize = iSize/1024;
		divby++;
	}
	
	switch(divby){
		case 0:
			return iSize+""; //bytes
		case 1:
			return iSize+"kB";
		case 2:
			return iSize+"mB"; //bytes
	}
	
	
	return "0";
}

// Notify observers that this download's status has changed.
private void stateChanged() {
    setChanged();
    notifyObservers();
}
}