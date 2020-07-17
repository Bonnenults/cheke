
package com.autozi.common.utils.file;

import com.autozi.common.core.utils.ApplicationPropertiesHelper;

public class FileManagerFactory {
	private static IFileManager tempFileManager = null;
	private static IFileManager imgFileManager = null;
	private static IFileManager attachmentFileManager = null;
	private static IFileManager contractDocManager = null;
				
  public synchronized static IFileManager getTempFileManager() {
  	if (tempFileManager == null) {
  	  tempFileManager = new LocalFileManager(ApplicationPropertiesHelper.getTempFilePath());
    }
    
    return tempFileManager;
  }
  
  public synchronized static IFileManager getImgFileManager() {
      if (imgFileManager == null) {
          imgFileManager = new LocalFileManager(ApplicationPropertiesHelper.getImgFilePath());
      }
      
      return imgFileManager;
  }
  
  public synchronized static IFileManager getAttDocManager() {
      if (attachmentFileManager == null) {
          attachmentFileManager = new LocalFileManager(ApplicationPropertiesHelper.getAttDocPath());
      }
      
      return attachmentFileManager;
  }
  
  public synchronized static IFileManager getContractFileManager() {
      if (contractDocManager == null) {
          contractDocManager = new LocalFileManager(ApplicationPropertiesHelper.getContractFilePath());
      }
      
      return contractDocManager;
  }
}
