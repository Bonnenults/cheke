 package com.autozi.common.utils.util3;
 import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

 public class SFTPUtil {
	 protected static Logger logger = LoggerFactory.getLogger("TGY");
     protected static String host;//sftp服务器ip
     protected static String username;//用户名
     protected static String password;//密码
     protected static String privateKey;//密钥文件路径
     protected static String passphrase;//密钥口令
     protected static int port;//默认的sftp端口号是22

     
     public static void startTransferForList(Map<String,String> linkInfo,String dir,List<String> fileNameList) throws Exception {
     	
    	 	host=linkInfo.get("host");					//连接信息
    	 	port=Integer.valueOf(linkInfo.get("port"));
    	 	username=linkInfo.get("username");
    	 	password=linkInfo.get("password");
    	 	if(linkInfo.containsKey("privateKey"))
    	 	{
    	 		privateKey=linkInfo.get("privateKey");	
    	 		passphrase=linkInfo.get("passphrase");	
    	 	}
    	 	
    	 	SFTPUtil sftpUtil = new SFTPUtil();
 			ChannelSftp  ChannelSftp = sftpUtil.connectSFTP();
 			
// 			for(String d : dir.split("\\\\")){
 			for(String d : dir.split(File.separator)){
 				try{
 					ChannelSftp.cd(d);
 				}catch(Exception e){
 					ChannelSftp.mkdir(d);
 					ChannelSftp.cd(d);
 				}
 			}
 			
// 			String pwd = ChannelSftp.pwd();	//根目录
// 			String regi_id = dir;			//启购客户id
// 			ChannelSftp.mkdir(regi_id);
// 			ChannelSftp.cd(regi_id);
 			
 			for(String fileName :fileNameList){
	 			File file = new File(fileName);		//上传文件
	 			try {
					ChannelSftp.put(new FileInputStream(file), file.getName());
					logger.info("本地文件："+fileName+"上传至服务器"+host+":"+port+"完成");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					logger.info("本地文件："+fileName+"上传至服务器"+host+":"+port+"失败，原因："+e.getStackTrace());
					throw e;
				} catch (SftpException e) {
					e.printStackTrace();
					logger.info("本地文件："+fileName+"上传至服务器"+host+":"+port+"失败，原因："+e.getStackTrace());
					throw e;
				}
 			}
 			
 			ChannelSftp.disconnect();
 	
 	}
     
     /**
      * 获取连接
      * @return channel
      */
     public ChannelSftp connectSFTP() {
         JSch jsch = new JSch();
         Channel channel = null;
         try {
             if (privateKey != null && !"".equals(privateKey)) {
                 //使用密钥验证方式，密钥可以使有口令的密钥，也可以是没有口令的密钥
                 if (passphrase != null && "".equals(passphrase)) {
                     jsch.addIdentity(privateKey, passphrase);
                 } else {
                     jsch.addIdentity(privateKey);
                 }
             }
             Session session = jsch.getSession(username, host, port);
             if (password != null && !"".equals(password)) {
                 session.setPassword(password);
             }
             Properties sshConfig = new Properties();
             sshConfig.put("StrictHostKeyChecking", "no");// do not verify host key
             session.setConfig(sshConfig);
             // session.setTimeout(timeout);
             session.setServerAliveInterval(92000);
             session.connect();
             //参数sftp指明要打开的连接是sftp连接
             channel = session.openChannel("sftp");
             channel.connect();
         } catch (JSchException e) {
             e.printStackTrace();
         }
         return (ChannelSftp) channel;
     }
     
     /**
      * 上传文件
      * 
      * @param directory
      *            上传的目录
      * @param uploadFile
      *            要上传的文件
      * @param sftp
      */
     public void upload(String directory, String uploadFile, ChannelSftp sftp) {
         try {
             sftp.cd(directory);
             File file = new File(uploadFile);
             sftp.put(new FileInputStream(file), file.getName());
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     /**
      * 下载文件
      * 
      * @param directory
      *            下载目录
      * @param downloadFile
      *            下载的文件
      * @param saveFile
      *            存在本地的路径
      * @param sftp
      */
     public void download(String directory, String downloadFile,
             String saveFile, ChannelSftp sftp) {
         try {
             sftp.cd(directory);
             sftp.get(downloadFile,saveFile);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }

     /**
      * 删除文件
      * 
      * @param directory
      *            要删除文件所在目录
      * @param deleteFile
      *            要删除的文件
      * @param sftp
      */
     public void delete(String directory, String deleteFile, ChannelSftp sftp) {
         try {
             sftp.cd(directory);
             sftp.rm(deleteFile);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     
     public void disconnected(ChannelSftp sftp) throws JSchException{
         if (sftp != null) {
             sftp.getSession().disconnect();
             sftp.disconnect();
         }
     }
 }
