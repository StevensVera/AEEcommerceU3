/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.aeecommerce.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.servlet.http.Part;

/**
 *
 * @author HP
 */
public class Image2 {
    
    public String createImg(Part filePart, String tab, int imgid){
        try{
            //String folder = "/Users/HP/Desktop/img";
            String folder = "C:\\var\\www\\images\\products\\";
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            
            String ext=".png";
            if(fileName.endsWith(".jpg"))
                ext = ".jpg";
            
            InputStream input = filePart.getInputStream();
            Path file = Files.createTempFile(Paths.get(folder), tab+imgid+"-", ext);
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            
            File fo = file.toFile();
            File fr = new File(folder+"/"+tab+imgid+ext);
            fo.renameTo(fr);
            
            return folder+"/"+fr.getName();
        }catch(IOException e){
        }
        
        return "";
    }
    
    public String updateImg(String img, Part filePart, String tab, int imgid){
        deleteImg(img);
        return createImg(filePart, tab, imgid);
    }
    
    public void deleteImg(String img){
        File f = new File(img);
        f.delete();
    }
}
