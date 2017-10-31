package cc.notalk.v.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FileDownloadService {

	public boolean downLoad(String uri, String fileDir, String fileTitle) {
		System.out.print("准备下载地址:[" + uri + ",文件名称:" + fileTitle + "]  >>>>>");
		boolean finished = false;
		File file = new File(fileDir + File.separator + StringUtils.substringAfterLast(uri, "/"));
		try {
			FileUtils.copyURLToFile(new URL(uri), file);
			finished = true;
			System.out.println(" 文件下载成功");
		} catch (MalformedURLException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		//		// 下载网络文件
		//		int byteread = 0;
		//		URL url;
		//		FileOutputStream fs = null;
		//		try {
		//			url = new URL(uri);
		//			URLConnection conn = url.openConnection();
		//			InputStream inStream = conn.getInputStream();
		//			fs = new FileOutputStream(file);
		//			byte[] buffer = new byte[1204];
		//			while ((byteread = inStream.read(buffer)) != -1) {
		//				fs.write(buffer, 0, byteread);
		//			}
		//			finished = true;
		//		} catch (MalformedURLException e1) {
		//			e1.printStackTrace();
		//		} catch (FileNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		} finally {
		//			if (null != fs) {
		//				try {
		//					fs.close();
		//				} catch (IOException e) {
		//					e.printStackTrace();
		//				}
		//			}
		//		}
		return finished;

	}

}
