package com.autozi.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class FileUtil {

	private FileUtil() {

	}

	/**
	 * 判断指定文件夹是否存在，不存在时创建之
	 * 
	 * @param path
	 */
	public static void createDirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * @param content
	 *            要保存的内容
	 * @param fileFullPath
	 *            文件全路径
	 * @return 返回自定义路径+文件名称 字符串
	 * */
	public static String createHtmlFile(String content, String fileFullPath)
			throws IOException {
		OutputStreamWriter writer = null;
		FileOutputStream outputFile=null;
		try {
			outputFile = new FileOutputStream(fileFullPath);
			writer = new OutputStreamWriter(outputFile, "utf-8");
			writer.write(content, 0, content.length());
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if (writer != null) {
				writer.close();
			}
			if (outputFile != null) {
				outputFile.close();
			}
		}
		return fileFullPath;
	}

	/**
	 * 删除掉文件
	 * 
	 * @param filePath
	 */
	public static boolean delFile(String filePath) {

		File file = new File(filePath);

		return !file.exists() || file.delete();
	}

	/**
	 * 读取html文件
	 * 
	 * @param filePath
	 * @return String
	 * @throws Exception
	 */
	public static String readHtml(String filePath, String name)
			throws Exception {
		FileInputStream fileInputStream = null;
		try {
			if(StringUtils.isBlank(name)){
				return "" ;
			}
			fileInputStream = new FileInputStream(filePath + name);
			return IOUtils.toString(fileInputStream, "utf-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "系统找不到指定的文件。";
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param destFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功，则返回true，否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName,
			boolean overlay) {
		// 判断原文件是否存在
		File srcFile = new File(srcFileName);
		if (!srcFile.exists()) {
			// System.out.println("复制文件失败：原文件" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			// System.out.println("复制文件失败：" + srcFileName + "不是一个文件！");
			return false;
		}
		// 判断目标文件是否存在
		File destFile = new File(destFileName);
		if (destFile.exists()) {
			// 如果目标文件存在，而且复制时允许覆盖。
			if (overlay) {
				// 删除已存在的目标文件，无论目标文件是目录还是单个文件
				// System.out.println("目标文件已存在，准备删除它！");
				if (!delFile(destFileName)) {
					// System.out.println("复制文件失败：删除目标文件" + destFileName +
					// "失败！");
					return false;
				}
			} else {
				// System.out.println("复制文件失败：目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				// System.out.println("目标文件所在的目录不存在，准备创建它！");
				if (!destFile.getParentFile().mkdirs()) {
					// System.out.println("复制文件失败：创建目标文件所在的目录失败！");
					return false;
				}
			}
		}
		// 准备复制文件
		int byteread = 0;// 读取的位数
		InputStream in = null;
		OutputStream out = null;
		try {
			// 打开原文件
			in = new FileInputStream(srcFile);
			// 打开连接到目标文件的输出流
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];
			// 一次读取1024个字节，当byteread为-1时表示文件已经读完
			while ((byteread = in.read(buffer)) != -1) {
				// 将读取的字节写入输出流
				out.write(buffer, 0, byteread);
			}
			// System.out.println("复制单个文件" + srcFileName + "至" + destFileName +
			// "成功！");
			return true;
		} catch (Exception e) {
			// System.out.println("复制文件失败：" + e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			// 关闭输入输出流，注意先关闭输出流，再关闭输入流
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/***
	 * 获取指定目录下的文件
	 * 
	 * @param path
	 * @param prefix
	 *            :后缀名
	 * @return
	 */
	public static File[] getFilesByPathAndSuffix(final File path,final String sufix) {
		return getFilesByPathAndSuffix(path, sufix, null);
	}

	/***
	 * 获取指定目录下的文件
	 * 
	 * @param path
	 * @return
	 */
	public static File[] getFilesByPathAndSuffix(final File path) {
		File[] fileArr = path.listFiles();
		return fileArr;
	}

	/***
	 * 获取指定目录下的文件
	 * 
	 * @param path
	 * @param prefix
	 *            :后缀名
	 * @param reg
	 *            :自定义正则
	 * @return
	 */
	public static File[] getFilesByPathAndSuffix(final File path,final String sufix, final String reg) {

		final Pattern pat = Pattern.compile(null == reg ? "" : reg);
		File[] fileArr = path.listFiles(new FilenameFilter() {

			
			public boolean accept(File dir, String name) {
				Matcher mat = pat.matcher(name);
				if (name.endsWith(sufix) && (StringUtils.isNotBlank(reg) ? mat.find() : true)) {
					return true;
				}
				return false;
			}
		});
		return fileArr;

	}

	public static File[] getFilesByPathAndSuffix(String dir,final String sufix,String reg) {
		File path = new File(dir);
		if(StringUtils.isNotBlank(sufix) && StringUtils.isNotBlank(reg)){
			return getFilesByPathAndSuffix(path,sufix,reg);
		}else if(StringUtils.isNotBlank(sufix)){
			return getFilesByPathAndSuffix(path,sufix);
		}
		return getFilesByPathAndSuffix(path);
	}


	/**
		  * @Description: 删除文件目录
		  * @user zhiyun.chen
		  * @dateTime 2014-5-6上午11:41:54
		 */
		public static boolean deleteDirectory(String sPath) {
			// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
			if (!sPath.endsWith(File.separator)) {
				sPath = sPath + File.separator;
			}
			File dirFile = new File(sPath);
			// 如果dir对应的文件不存在，或者不是一个目录，则退出
			if (!dirFile.exists() || !dirFile.isDirectory()) {
				return false;
			}
			boolean flag = true;
			// 删除文件夹下的所有文件(包括子目录)
			File[] files = dirFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				// 删除子文件
				if (files[i].isFile()) {
					flag = delFile(files[i].getAbsolutePath());
					if (!flag)
						break;
				} // 删除子目录
				else {
					flag = deleteDirectory(files[i].getAbsolutePath());
					if (!flag)
						break;
				}
			}
			if (!flag)
				return false;
			// 删除当前目录
			if (dirFile.delete()) {
				return true;
			} else {
				return false;
			}
		}
}
