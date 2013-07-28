package org.castafiore.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Properties;

import javax.swing.JFrame;

import org.castafiore.swing.sales.options.CanvaGraphic;
import org.openswing.swing.permissions.client.LoginController;
import org.openswing.swing.permissions.client.LoginDialog;
import org.openswing.swing.permissions.java.CryptUtils;

public class CanvaLoginDialog extends LoginDialog {

	public CanvaLoginDialog(JFrame parentFrame, boolean changeLogin,
			LoginController loginController, String title,
			String loginButtonText, char loginButtonMnemonic,
			String exitButtonText, char exitButtonMnemonic,
			String storeAccount, String appId, CryptUtils cipher,
			Properties supportedLanguageIds, String currentLanguageIdentifier,
			String usernameTextLabel, String passwordTextLabel, Dimension size) {
		super(parentFrame, changeLogin, loginController, title, loginButtonText,
				loginButtonMnemonic, exitButtonText, exitButtonMnemonic, storeAccount,
				appId, cipher, supportedLanguageIds, currentLanguageIdentifier,
				usernameTextLabel, passwordTextLabel, size);
		// TODO Auto-generated constructor stub
	}

	public CanvaLoginDialog(JFrame parentFrame, boolean changeLogin,
			LoginController loginController, String title,
			String loginButtonText, char loginButtonMnemonic,
			String exitButtonText, char exitButtonMnemonic,
			String storeAccount, String appId, CryptUtils cipher,
			Properties supportedLanguageIds, String currentLanguageIdentifier,
			String usernameTextLabel, String passwordTextLabel) {
		super(parentFrame, changeLogin, loginController, title, loginButtonText,
				loginButtonMnemonic, exitButtonText, exitButtonMnemonic, storeAccount,
				appId, cipher, supportedLanguageIds, currentLanguageIdentifier,
				usernameTextLabel, passwordTextLabel);
		// TODO Auto-generated constructor stub
	}

	public CanvaLoginDialog(JFrame parentFrame, boolean changeLogin,
			LoginController loginController, String title,
			String loginButtonText, char loginButtonMnemonic,
			String exitButtonText, char exitButtonMnemonic,
			String storeAccount, String appId, CryptUtils cipher,
			Properties supportedLanguageIds, String currentLanguageIdentifier) {
		super(parentFrame, changeLogin, loginController, title, loginButtonText,
				loginButtonMnemonic, exitButtonText, exitButtonMnemonic, storeAccount,
				appId, cipher, supportedLanguageIds, currentLanguageIdentifier);
		// TODO Auto-generated constructor stub
	}

	public CanvaLoginDialog(JFrame parentFrame, boolean changeLogin,
			LoginController loginController, String title,
			String loginButtonText, char loginButtonMnemonic,
			String exitButtonText, char exitButtonMnemonic,
			String storeAccount, String appId, CryptUtils cipher) {
		super(parentFrame, changeLogin, loginController, title, loginButtonText,
				loginButtonMnemonic, exitButtonText, exitButtonMnemonic, storeAccount,
				appId, cipher);
		// TODO Auto-generated constructor stub
	}

	public CanvaLoginDialog(JFrame parentFrame, boolean changeLogin,
			LoginController loginController, String title,
			String loginButtonText, char loginButtonMnemonic,
			String exitButtonText, char exitButtonMnemonic,
			String storeAccount, String appId) {
		super(parentFrame, changeLogin, loginController, title, loginButtonText,
				loginButtonMnemonic, exitButtonText, exitButtonMnemonic, storeAccount,
				appId);
		
	}

	public CanvaLoginDialog(JFrame parentFrame, boolean changeLogin,
			LoginController loginController) {
		super(parentFrame, changeLogin, loginController);
		
	}

	@Override
	public void paint(Graphics g) {
		
		if(g instanceof CanvaGraphic){
			super.paint(g);
		}else{
			super.paint(new CanvaGraphic());
		}
	}

	@Override
	public void paintComponents(Graphics g) {
		if(g instanceof CanvaGraphic){
			super.paintComponents(g);
		}else{
			super.paintComponents(new CanvaGraphic());
		}
	}

	@Override
	public void paintAll(Graphics g) {
		if(g instanceof CanvaGraphic){
			super.paintAll(g);
		}else{
			super.paintAll(new CanvaGraphic());
		}
	}
	
	

}
