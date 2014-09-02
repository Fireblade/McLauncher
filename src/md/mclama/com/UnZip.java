package md.mclama.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
 
public class UnZip
{
    List<String> fileList;
    String gamePath;
    private ModManager McLauncher;
 
    /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     * Code thanks to mkyong at http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
     */
    public void unZipIt(ModManager McLauncher, String zipFile, String outputFolder){
        gamePath = McLauncher.gamePath;
        this.McLauncher = McLauncher;
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	//create output directory is not exists
    	File folder = new File(gamePath + "\\mods\\");
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	//get the zip file content
    	System.out.println(zipFile);
    	ZipInputStream zis = 
    		new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
    	ZipFile zipp = new ZipFile(zipFile);
    	int numberOfEntries = 0;
    	int numMax = zipp.size();
    	zipp.close();
    	zipp=null;
    	System.out.println("entries... " + numMax);
 
    	while(ze!=null){

    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
 
           System.out.println("file unzip : "+ newFile.getAbsoluteFile());
           numberOfEntries++;
           McLauncher.pBarExtractMod.setValue((numberOfEntries/numMax)*100);
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
           if(ze.isDirectory()) 
           {
        	   new File(newFile.getParent()).mkdirs();
           }
           else
           {
        	FileOutputStream fos = null;
        	
            new File(newFile.getParent()).mkdirs();
 
            fos = new FileOutputStream(newFile);             
 
            int len;
            while ((len = zis.read(buffer)) > 0) 
            {
       		fos.write(buffer, 0, len);
            }
          
            fos.close();   
           }
           ze = zis.getNextEntry();
           
    	}
 
        zis.closeEntry();
    	zis.close();
    	zis=null;
    	Utility util = new Utility(null);
    	//System.out.println(zipFile.substring(zipFile.lastIndexOf('\\') + 1));
    	String sendreq = util.remVer(zipFile.substring(zipFile.lastIndexOf('\\') + 1).replace(".zip",""));
    	util.SendDownloadRequest(sendreq);
    	System.out.println("entries... now..." + numberOfEntries);
    	System.out.println("Done");
    	McLauncher.lblDownloadModInfo.setText("Done");
    	McLauncher.downloading=false;
    	
    	File f = new File(zipFile);
    	f.delete();
    	McLauncher.getMods();//update the mod list
 
    }catch(IOException ex){
       ex.printStackTrace(); 
    }
   }    
}