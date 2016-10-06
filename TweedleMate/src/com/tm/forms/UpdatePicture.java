package com.tm.forms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.tm.dao.UserDAO;
import com.tm.entities.User;

import eu.medsea.mimeutil.MimeUtil;

@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UpdatePicture extends UpdateForm {

	UserDAO userDao;

	String successMessage = "Nice picture, behold !";
	String labelFieldSucess = "pictureLabel";

	private static final String F_FILE = "picturePath";
	private static final int SIZE_BUFFER = 10240; // 10 ko
	private static final String SAVE_PATH = "D:\\Documents\\Developpement\\git\\Tweedlemate\\TweedleMate\\WebContent"
			+ "\\WEB-INF\\content\\images\\profile";

	private Part filePart;

	public UpdatePicture(UserDAO userDao) {
		this.userDao = userDao;
	}

	/**
	 * Analyze header to get file's name
	 * 
	 * @param part
	 * @return
	 */
	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	@Override
	protected void getAllParameters(ServletRequest request) {
		try {
			filePart = ((HttpServletRequest) request).getPart(F_FILE);
		} catch (ServletException e) {
			addError(F_FILE, "Server configuration error (no multipart ?)");
			e.printStackTrace();
		} catch (IOException e) {
			addError(F_FILE, "Please use the given form to send your profile picture");
			e.printStackTrace();
		}
	}

	@Override
	protected void checkData() {
		String fileName = null;

		try (BufferedInputStream filecontent = new BufferedInputStream(filePart.getInputStream())) {
			// filePart = request.getPart(F_FILE);

			fileName = getFileName(filePart);
			if (fileName == null) {
				addError(F_FILE, "Oups, you should select a file first !");
			}

			// Get the type of the file to make sure it is an image and not
			// something weird hidden
			MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
			Collection<?> mimeTypes = MimeUtil.getMimeTypes(filecontent);
			if (!mimeTypes.toString().startsWith("image")) {
				addError(F_FILE, "The file should be an image");
			}

		} catch (IllegalStateException e) {
			addError(F_FILE, "File is too heavy !");
			e.printStackTrace();
		} catch (IOException e) {
			addError(F_FILE, "Please use the given form to send your profile picture");
			e.printStackTrace();
		}
	}

	@Override
	protected void persist(User connectedUser) {
		// write the picture in the server
		String picturePath = SAVE_PATH + connectedUser.getProfilePictureName();

		try (BufferedInputStream filecontent = new BufferedInputStream(filePart.getInputStream());
				OutputStream out = new FileOutputStream(new File(picturePath));) {

			int read = 0;
			final byte[] bytes = new byte[SIZE_BUFFER];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

		} catch (FileNotFoundException e) {
			addError(F_FILE, "Oups, you should select a file first !");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// write the picturePath in User table
		connectedUser.setPicturePath(picturePath);
		userDao.updatePicturePath(connectedUser);
	}
}
