
package com.autozi.common.utils.file;

import java.io.File;
import java.io.IOException;

public interface IFileManager {
    IFile getFile(String path);
    
    IFile[] getFiles(String dir);
    
    IFile createFile(String path) throws IOException;
    
    void deleteFile(String path);
    
    public String storeFile(File file,String uploadFileName);
    
	public String[] storeFiles2Img_thumb(File file,String fileName,int[] changeSizeArray);
}
