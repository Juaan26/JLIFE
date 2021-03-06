package presentation;

import java.util.Scanner;

import dataAccess.Data;
import models.Password;
import models.Session;
import models.Simulation;
import models.User;
import utils.EasyDate;

public class UserAccess {
	
	private static final int MAX_ATTEMPTS = 3;
	private Session session;
	private Simulation simulation;


	public Session getSession() {
		return this.session;
	}
	
	public Simulation getSimulation() {
		return this.simulation;
	}
	
	public void setSimulation(Simulation simulation) {
		this.simulation = simulation; 
	}
	
	// Session
	
	public boolean isLoginOK(Data data) {
		Scanner keyboard = new Scanner(System.in);
		int attempts = MAX_ATTEMPTS;

		do {
			System.out.print("Usuario: ");
			String id = keyboard.nextLine();
			System.out.print("Clave de acceso: ");
			String password = keyboard.nextLine();

			User user = data.findUser(id);
			if (user != null 
					&& user.getPassword().equals(new Password(password))) {
				
				this.session = new Session(user);
				data.createSession(this.session);
				
				this.simulation = new Simulation();
				
				return true;
			} 
			else {
				attempts--;
				System.out.print("Credenciales incorrectas: ");
				System.out.println("Quedan " + attempts + " intentos... ");
			} 
		} while (attempts > 0);
		
		return false;
	}
	

	public void closeSession(Data data) {
		this.session.setEndTime(EasyDate.now());
		data.updateSession(this.session);
		
	}

	public void welcome() {
		System.out.println("Sesión: " + "Iniciada por: " 
				+ 	this.session.getUser().getName() 
				+ " "
				+ this.session.getUser().getSurnames());	
	}

	public void simpleMessage(String text) {
		System.out.println(text);	
	}

	// Simulation
	
	public void showDemo() {
		this.simulation.runDemo();	
	}
	
}
