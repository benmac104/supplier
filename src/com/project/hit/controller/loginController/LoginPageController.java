package com.project.hit.controller.loginController;

import com.project.hit.model.AuthenticationSystem;
import com.project.hit.model.InvalidCredentialsException;
import com.project.hit.view.loginPage.LoginView;

public class LoginPageController implements LoginController {
	
	
	private LoginView view;
	private AuthenticationSystem authSystem;
	
	/**
	 * The controller constructor.
	 * @param view - The view of the Login page.
	 * @param model - the model usage of the Login page.
	 */
	public LoginPageController(LoginView view, AuthenticationSystem model) {
		this.view = view;
		this.authSystem = model;
	}
	
	@Override
	public void authenticate(String username, char[] password) throws InvalidCredentialsException {
		if (authSystem.authenticate(username, password)) {
			view.close();
			openMainMenu();
		}
		
		//Option B
		/* User user = authSystem.athenticate(username, password);
		 * view.close();
		 * openMainMenu(user);
		 * */
	}
	
	/**
	 * Opens the main menu dialog.
	 */
	private void openMainMenu() {
		//TODO: open the main window;
	}
	

}
