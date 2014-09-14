package md.mclama.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
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
    String modPath;
    String updateStr;
    private ModManager McLauncher;
    private Console con;
 
    /**
     * Unzip it
     * @param zipFile input zip file
     * @param output zip file output folder
     * Code thanks to mkyong at http://www.mkyong.com/java/how-to-decompress-files-from-a-zip-file/
     */
    public void unZipIt(ModManager McLauncher, String zipFile, String outputFolder){
        gamePath = McLauncher.gamePath;
        this.McLauncher = McLauncher;
        this.modPath = McLauncher.modPath;
        con = McLauncher.con;
        
    	Utility util = new Utility(null);
        updateStr = CheckIfUpdate(outputFolder, util.remVer(zipFile.substring(zipFile.lastIndexOf('\\') + 1).replace(".zip","")));
 
     byte[] buffer = new byte[1024];
 
     try{
 
    	//create output directory is not exists
    	File folder = new File(modPath);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
 
    	//get the zip file content
    	con.log("Log","Zip file at.. " + zipFile);
    	ZipInputStream zis = 
    		new ZipInputStream(new FileInputStream(zipFile));
    	//get the zipped file list entry
    	ZipEntry ze = zis.getNextEntry();
    	ZipFile zipp = new ZipFile(zipFile);
    	int numberOfEntries = 0;
    	int numMax = zipp.size();
    	zipp.close();
    	zipp=null;
    	con.log("Log","entries... " + numMax);
 
    	while(ze!=null){

    	   String fileName = ze.getName();
           File newFile = new File(outputFolder + File.separator + fileName);
 
           //con.log("Log","file unzip : "+ newFile.getAbsoluteFile()); //Commented out, downloading 10+ mods used up large memory
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
    	//con.log("Log",zipFile.substring(zipFile.lastIndexOf('\\') + 1));
    	String sendreq = util.remVer(zipFile.substring(zipFile.lastIndexOf('\\') + 1).replace(".zip",""));
    	util.SendDownloadRequest(URLEncoder.encode(sendreq, "UTF-8")+updateStr);
    	con.log("Log","entries... now..." + numberOfEntries);
    	con.log("Log","Done");
    	McLauncher.lblDownloadModInfo.setText("Done");
    	McLauncher.downloading=false;
    	
    	File f = new File(zipFile);
    	f.delete();
    	McLauncher.getMods();//update the mod list
 
    }catch(IOException ex){
       ex.printStackTrace(); 
       McLauncher.canDownloadMod=true;
       McLauncher.lblDownloadModInfo.setText("Failed to extract");
       con.log("Severe", "Failed to extract... " + zipFile);
    }
   }

	private String CheckIfUpdate(String modFolder, String mod) {
		boolean foundit=false;
		
		File oldMod = new File(modFolder+"\\"+mod);
		if(oldMod.isDirectory()){
			con.log("Log","IS DIRECTORY");
			foundit=true;
		}
		else con.log("Log","NOT DIRECTORY ... " + oldMod);
		
		
		if(foundit){
			if(McLauncher.tglbtnDeleteBeforeUpdate.isSelected()){ //If we delete the old mod before extracting.
				if(oldMod.delete()){
					con.log("Log","Successfully deleted old mod for update.");
				}
			}
			return "&update=true";
		}
		return "&update=false";
	}    
}