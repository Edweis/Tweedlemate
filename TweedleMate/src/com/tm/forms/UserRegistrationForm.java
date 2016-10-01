package com.tm.forms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.joda.time.DateTime;

import com.tm.dao.DAOException;
import com.tm.dao.UserDAO;
import com.tm.entities.User;

import eu.medsea.mimeutil.MimeUtil;

public final class UserRegistrationForm {
	private static final String F_FIRSTNAME = "firstName";
	private static final String F_EMAIL = "email";
	private static final String F_PICTUREPATH = "picturePath";
	private static final String F_PASSWORD = "password";
	private static final String F_PASSWORD_VERIF = "passwordVerification";
	private static final String F_INTROUCTIONTEXT = "introdutionText";
	private static final String F_APPOINTMENTPRICE = "appointmentPrice";

	private static final String IMAGE_PATH = "D:\\Documents\\Developpement\\testImages";

	private static final int TAILLE_TAMPON = 10240; // 10ko

	private String resultat;
	private Map<String, String> errors = new HashMap<String, String>();
	private UserDAO userDao;

	public UserRegistrationForm(UserDAO userDao) {
		this.userDao = userDao;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public String getResultat() {
		return resultat;
	}

	public User createUser(HttpServletRequest request) {
		String firstname = getValueParameter(request, F_FIRSTNAME);
		// String familyName = getValueParameter(request, F_FAMILYNAME);
		String email = getValueParameter(request, F_EMAIL);
		String picturePath = getValueParameter(request, F_PICTUREPATH);
		String password = getValueParameter(request, F_PASSWORD);
		String passwordVerif = getValueParameter(request, F_PASSWORD_VERIF);
		String introductionText = getValueParameter(request, F_INTROUCTIONTEXT);
		String appointmentPrice = getValueParameter(request, F_APPOINTMENTPRICE);

		User user = new User();
		checkFirstName(firstname, user);
		// checkFamilyName(familyName, user);
		checkEmail(email, user);
		// checkImage(picturePath, user, request);
		checkPassword(password, passwordVerif, user);
		// checkIntroductionText(introductionText, user);
		// checkAppointmentPrice(appointmentPrice, user);

		user.setDateRegistration(DateTime.now());

		try {
			if (errors.isEmpty()) {
				userDao.create(user);
				resultat = "Registration suceeded.";
			} else {
				resultat = "Registration failed.";
			}
		} catch (DAOException e) {
			addError("unexpected", "Unexpected error occured during user's creation in registration");
			resultat = "Échec de la création du User : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			e.printStackTrace();
		}
		if (errors.isEmpty()) {
			try {
				userDao.create(user);
			} catch (DAOException e) {
				addError("unexpected", e.getMessage());
			}
		}

		return user;
	}

	/*
	 * Checkers
	 */

	private void checkAppointmentPrice(String appointmentPrice, User user) {
		// TODO Auto-generated method stub

	}

	private void checkIntroductionText(String introductionText, User user) {
		// TODO Auto-generated method stub

	}

	private void checkPassword(String password, String passwordVerif, User user) {
		if (password != null) {
			if (password.length() >= 5) {

				if (passwordVerif != null) {
					if (passwordVerif.equals(password)) {
						// Passwords OK
						user.setPassword(password);
					} else {
						addError(F_PASSWORD_VERIF, "Passwords don't match.");
					}
				} else {
					addError(F_PASSWORD_VERIF, "Please insert the password twice.");
				}

			} else {
				addError(F_PASSWORD, "Your password should be at least 5 character long.");
			}
		} else {
			addError(F_PASSWORD, "Please insert a password.");
		}
	}

	private void checkFirstName(String firstname, User user) {
		if (firstname != null) {
			if (firstname.length() < 2) {
				addError(F_FIRSTNAME, "Your first name should contain at least 2 caracters");
			} else {
				user.setFirstName(firstname);
			}
		}
	}

	private void checkEmail(String email, User user) {

		if (email != null && !email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
			addError(F_EMAIL, "Please enter a valide email address");
		} else {
			if (userDao.findFromEmail(email) != null) {
				addError(F_EMAIL,
						"It appears that this email is already register, if you have any problem regarding registration please contact us !");
			} else {
				user.setEmail(email);
			}
		}
		// } else {
		// user.setEmail(email);
		// }
	}

	private void checkImage(String picturePath, User user, HttpServletRequest request) {
		if (picturePath != null) {
			try {
				validationImage(request);
			} catch (FormValidationException e) {
				addError(F_PICTUREPATH, e.getMessage());
			}
		}
	}

	private String validationImage(HttpServletRequest request) throws FormValidationException {
		/*
		 * Récupération du contenu du champ image du formulaire. Il faut ici
		 * utiliser la méthode getPart().
		 */
		String fileName = null;
		InputStream fileContent = null;
		try {
			Part part = request.getPart(F_PICTUREPATH);
			fileName = getFileName(part);

			/*
			 * Si la méthode getNomFichier() a renvoyé quelque chose, il s'agit
			 * donc d'un champ de type fichier (input type="file").
			 */
			if (fileName != null && !fileName.isEmpty()) {
				/*
				 * Antibug pour Internet Explorer, qui transmet pour une raison
				 * mystique le chemin du fichier local à la machine du User...
				 * 
				 * Ex : C:/dossier/sous-dossier/fichier.ext
				 * 
				 * On doit donc faire en sorte de ne sélectionner que le nom et
				 * l'extension du fichier, et de se débarrasser du superflu.
				 */
				fileName = fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);

				/* Récupération du contenu du fichier */
				fileContent = part.getInputStream();

				/* Extraction du type MIME du fichier depuis l'InputStream */
				MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
				Collection<?> mimeTypes = MimeUtil.getMimeTypes(fileContent);

				/*
				 * Si le fichier est bien une image, alors son en-tête MIME
				 * commence par la chaîne "image"
				 */
				if (mimeTypes.toString().startsWith("image")) {
					/* Écriture du fichier sur le disque */
					ecrireFichier(fileContent, fileName, IMAGE_PATH);
				} else {
					throw new FormValidationException("Uploaded file should be an image.");
				}
			}
		} catch (IllegalStateException e) {
			/*
			 * Exception retournée si la taille des données dépasse les limites
			 * définies dans la section <multipart-config> de la déclaration de
			 * notre servlet d'upload dans le fichier web.xml
			 */
			e.printStackTrace();
			throw new FormValidationException("Upload file size should not exceed 1Mo.");
		} catch (IOException e) {
			/*
			 * Exception retournée si une erreur au niveau des répertoires de
			 * stockage survient (répertoire inexistant, droits d'accès
			 * insuffisants, etc.)
			 */
			e.printStackTrace();
			throw new FormValidationException("Server configuration error during file uploading");
		} catch (ServletException e) {
			/*
			 * Exception retournée si la requête n'est pas de type
			 * multipart/form-data.
			 */
			e.printStackTrace();
			throw new FormValidationException(
					"This kind of request is not supported, kindly use the website's form to send this file");
		}

		return fileName;
	}

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void addError(String champ, String message) {
		errors.put(champ, message);
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValueParameter(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

	/*
	 * Méthode utilitaire qui a pour unique but d'analyser l'en-tête
	 * "content-disposition", et de vérifier si le paramètre "filename" y est
	 * présent. Si oui, alors le champ traité est de type File et la méthode
	 * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
	 * la méthode retourne null.
	 */
	private static String getFileName(Part part) {
		/*
		 * Boucle sur chacun des paramètres de l'en-tête "content-disposition".
		 */
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			/* Recherche de l'éventuelle présence du paramètre "filename". */
			if (contentDisposition.trim().startsWith("filename")) {
				/*
				 * Si "filename" est présent, alors renvoi de sa valeur,
				 * c'est-à-dire du nom de fichier sans guillemets.
				 */
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		/* Et pour terminer, si rien n'a été trouvé... */
		return null;
	}

	/*
	 * Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre
	 * sur le disque, dans le répertoire donné et avec le nom donné.
	 */
	private void ecrireFichier(InputStream contenuFichier, String nomFichier, String chemin)
			throws FormValidationException {
		/* Prépare les flux. */
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		try {
			/* Ouvre les flux. */
			entree = new BufferedInputStream(contenuFichier, TAILLE_TAMPON);
			sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

			/*
			 * Lit le fichier reçu et écrit son contenu dans un fichier sur le
			 * disque.
			 */
			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur = 0;
			while ((longueur = entree.read(tampon)) > 0) {
				sortie.write(tampon, 0, longueur);
			}
		} catch (Exception e) {
			throw new FormValidationException("Erreur lors de l'écriture du fichier sur le disque.");
		} finally {
			try {
				sortie.close();
			} catch (IOException ignore) {
			}
			try {
				entree.close();
			} catch (IOException ignore) {
			}
		}
	}
}