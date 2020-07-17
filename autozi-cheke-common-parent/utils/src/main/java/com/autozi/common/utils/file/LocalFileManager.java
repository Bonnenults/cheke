

package com.autozi.common.utils.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

public class LocalFileManager implements IFileManager {
    private String root;
    
    public LocalFileManager(String root) {
        this.root = root;
    }

    public IFile createFile(String path) throws IOException {
        String fullPath = getFullPath(path);
        File file = new File(fullPath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        file.createNewFile();
        IFile _file = new LocalFile(root, path);
        return _file;
    }

    public void deleteFile(String path) {
        String fullPath = getFullPath(path);
        File file = new File(fullPath);
        file.delete();
    }

    public IFile getFile(String path) {
        IFile file = new LocalFile(root, path);
        return file;
    }

    public IFile[] getFiles(String dir) {
        String fullPath = getFullPath(dir);
        File file = new File(fullPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null || files.length ==0) {
                return null;
            }
            
            List<IFile> ret = new ArrayList<IFile>();
            for(File f : files) {
                if (f.isFile()) {
                    String path = f.getPath().substring(root.length());
                    path = path.replace('\\', '/');
                    if (path.startsWith("/")) {
                        path = path.substring(1);
                    }
                    IFile _f = new LocalFile(root, path);
                    ret.add(_f);
                }
            }
            
            return ret.toArray(new IFile[0]);
        }
        return null;
    }

    private String getFullPath(String path) {
        if (path == null) {
            return root;
        }
        
        String fullPath = root;
        if (!fullPath.endsWith("/")) {
            fullPath += "/";
        }
        fullPath += path;
        return fullPath;
    }
    
    public  String storeFile(File file,String uploadFileName) {
        String storeUploadFileName=null;
		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		int index = uploadFileName.lastIndexOf(".");
		if (index != -1) {
			storeUploadFileName = UUID.randomUUID().toString() + uploadFileName.substring(index);
		} else {
			storeUploadFileName = UUID.randomUUID().toString();
		}
		String storeUploadFilePath  = today+"/"+storeUploadFileName;
        String fullPath = getFullPath(storeUploadFilePath);
        File fullPathFile = new File(fullPath);
        File dir = fullPathFile.getParentFile();
		if(!dir.exists()){
			 dir.mkdirs();
		}
		InputStream is=null;
		OutputStream os=null;
		File newfile=null;
        try {
        	newfile = new File(dir, storeUploadFileName);
    		is = new FileInputStream(file);
    		os = new FileOutputStream(newfile);
    		byte[] buf = new byte[1024];
    		int len = -1;
    		while ((len = is.read(buf)) != -1) {
    			os.write(buf, 0, len);
    		}
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally{
    	    try{
    	    	if(is!=null)is.close();
    	    	if(os!=null)os.close();
            }catch (Exception e) {
            	e.printStackTrace();
            	throw new RuntimeException(e);
            }
        }
        return storeUploadFilePath;
    }
	public  String[] storeFiles2Img_thumb(File file,String fileName,int[] changeSizeArray) {
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String[] filePaths = new String[4];
		    String newImgUploadFileName=null;
			int index = fileName.lastIndexOf(".");
			if (index != -1) {
				newImgUploadFileName = UUID.randomUUID().toString() + fileName.substring(index);
			} else {
				newImgUploadFileName = UUID.randomUUID().toString();
			}
	        for(int i=0 ;i<changeSizeArray.length ;i++){
	        	InputStream in=null;
	        	ByteArrayOutputStream byteOut=null;
				try {
					in=new FileInputStream(file);
					BufferedImage image_thumb = ImageIO.read(in);
					DisposePictures dp_thumb = new DisposePictures();
					image_thumb = dp_thumb.resize(image_thumb, changeSizeArray[i], changeSizeArray[i]);
					byteOut = new ByteArrayOutputStream(); 
					ImageIO.write(image_thumb, newImgUploadFileName.substring(newImgUploadFileName.lastIndexOf(".")+1), byteOut);
				
					String thumbImageNamePath  = today+"/thumb_"+changeSizeArray[i]+"/"+ "thumb_"+changeSizeArray[i]+"_"+changeSizeArray[i]+newImgUploadFileName;
	                IFile ifile = createFile(thumbImageNamePath);
	                ifile.saveContent(byteOut.toByteArray());
	                filePaths[i]=thumbImageNamePath;
				}catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}finally{
					try{
		    		   if(in!=null) in.close();  
		    		   if(byteOut!=null) byteOut.close(); 
			        }catch(Exception e) {
			           e.printStackTrace();
			           throw new RuntimeException(e);
			        }
				}
	        } 
			return  filePaths;
		 }
	 
}
