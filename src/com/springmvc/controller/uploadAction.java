package com.springmvc.controller;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.springmvc.util.Plupload;
import com.springmvc.util.PluploadUtil;

import net.sf.json.JSONObject;

@Controller
public class uploadAction {
	public static final String FileDir = "uploadfile/";

	/**上传处理方法*/
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public void upload(Plupload plupload,HttpServletRequest request, HttpServletResponse response) {
		
		//System.out.println(plupload.getChunk() + "===" + plupload.getName() + "---" + plupload.getChunks());
		
		plupload.setRequest(request);
		//文件存储路径
		File dir = new File("D:\\logs");
		
		System.out.println(dir.getPath());
		
		try {
			//上传文件
			PluploadUtil.upload(plupload, dir);
			//判断文件是否上传成功（被分成块的文件是否全部上传完成）
			if (PluploadUtil.isUploadFinish(plupload)) {
				System.out.println(plupload.getName() + "----");
			}
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		return "login.upload";
	}

	public static String uploadPath = "D:\\logs";

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload1", method = RequestMethod.POST)
	public void upload1(HttpServletRequest request, HttpServletResponse response) {
		Integer chunk = null;// 分割块数
		Integer chunks = null;// 总分割数
		String tempFileName = null;// 临时文件吿
		String fileName = "";
		BufferedOutputStream outputStream = null;
		if (ServletFileUpload.isMultipartContent(request)) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024);
				// factory.setRepository(new File(repositoryPath));// 设置临时目录
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");
				// upload.setSizeMax(5 * 1024 * 1024);// 设置附件朿ħ大小，超过这个大小上传会不成势
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {// 是文本域
						if (item.getFieldName().equals("name")) {
							tempFileName = item.getString();
						} else if (item.getFieldName().equals("chunk")) {
							chunk = Integer.parseInt(item.getString());
							// System.out.println("当前文件块：" + (chunk + 1));
						} else if (item.getFieldName().equals("chunks")) {
							chunks = Integer.parseInt(item.getString());
							// System.out.println("文件总分块：" + chunks);
						}
					} else {// 如果是文件类垿
						if (tempFileName != null) {
							String chunkName = item.getName();
							fileName = item.getName();// 真实文件名
							if (chunk != null) {
								chunkName = chunk + "_" + tempFileName;
							}
							File savedFile = new File(uploadPath, chunkName);
							item.write(savedFile);
						}
					}
				}
				if (chunk != null && chunk + 1 == chunks) {
					outputStream = new BufferedOutputStream(new FileOutputStream(new File(uploadPath, fileName)));
					// 遍历文件合并
					for (int i = 0; i < chunks; i++) {
						File tempFile = new File(uploadPath, i + "_" + tempFileName);
						System.out.println("tempFileName:" + tempFileName);
						byte[] bytes = FileUtils.readFileToByteArray(tempFile);
						outputStream.write(bytes);
						outputStream.flush();
						tempFile.delete();
					}
					outputStream.flush();
				}
				// System.out.println("newFileName:"+newFileName);
				Map<String, Object> m = new HashMap<String, Object>();
				System.out.println("newFileName:" + fileName);
				m.put("status", true);
				m.put("fileUrl", uploadPath + "/");
				response.getWriter().write(JSONObject.fromObject(m).toString());
			} catch (FileUploadException e) {
				e.printStackTrace();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("status", false);
				try {
					response.getWriter().write(JSONObject.fromObject(m).toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("status", false);
				try {
					response.getWriter().write(JSONObject.fromObject(m).toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} finally {
				try {
					if (outputStream != null)
						outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

