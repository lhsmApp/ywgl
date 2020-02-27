package com.fh.util;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载文件
 * 
 * @ClassName: FileDownload
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lhsmplus
 * @date 2017年6月30日
 *
 */
public class FileDownload {

	/**
	 * @param response
	 * @param filePath
	 *            //文件完整路径(包括文件名和扩展名)
	 * @param fileName
	 *            //下载后看到的文件名
	 * @return 文件名
	 */
	public static void fileDownload(final HttpServletResponse response, String filePath, String fileName)
			throws Exception {
		byte[] data=null;
		try {
			data = FileUtil.toByteArray2(filePath);
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
			response.flushBuffer();
		} catch (Exception ex) {
			String strData="您访问的文件不存在";
			throw new Exception(strData);
		}
	}

}
