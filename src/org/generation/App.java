package org.generation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
	static Scanner scan = new Scanner(System.in);
	static ArrayList<String> log = new ArrayList<String>();
	public static void main(String[] args) {
		double balance = 10000.00;
		boolean stayIn = true;
		Scanner scan = new Scanner(System.in);
		int option;
		int outCounter = 0;
		
	
		do {
			printMenu();
			option = scan.nextInt();
				switch (option) {
				case 1: {
					outCounter = 0;
					balance = withdrawMoney(balance);
					break;
				}
				case 2:{
					outCounter = 0;
					balance = depositMoney(balance);
					break;
				}
				case 3:{
					outCounter = 0;
					System.out.println("Usted cuenta con " + balance + " de saldo diponible");
					break;
				}
				case 4:{
					outCounter = 0;
					System.out.println("No disponible por el momento, intente más tarde");
					break;
				}
				case 5:{
					outCounter = 0;
					printLog();
					break;
				}
				case 9:{
					System.out.println("Has salido del sistema");
					stayIn = false;
					break;
				}
				default:
					outCounter++;
					if(outCounter == 3) {
						System.out.println("Has agotado tus intentos, el sistema se cerrara");
						stayIn = false;
					}else {
						System.out.println("Opción inválida, por favor, vuelva a intentar");
					}
					
					
					
				}
		} while (stayIn);
		scan.close();
	}
	
	static void printMenu() {
		System.out.println("+-----------------------------------+");
		System.out.println("| 1) Retirar dinero          	    |");
		System.out.println("| 2) Hacer depósitos         	    |");
		System.out.println("| 3) Consultar saldo        	    |");
		System.out.println("| 4) Quejas                         |");
		System.out.println("| 5) Ver últimos movimientos 	    |");
		System.out.println("| 9) Salir del cajero        	    |");
		System.out.println("+-----------------------------------+");
		System.out.println("Elija una opción");
	}
	
	static void printAcountType() {
		System.out.println("+-----------------------------------+");
		System.out.println("| 1) Cuenta de cheques              |");
		System.out.println("| 2) Déposito a tarjeta de credito  |");
		System.out.println("|                                   |");
		System.out.println("|                                   |");
		System.out.println("|                                   |");
		System.out.println("|                                   |");
		System.out.println("+-----------------------------------+");
		System.out.println("Elija una opción");
	}
	
	static double withdrawMoney(double balance) {
		boolean out = false;
		boolean outDonate = false;
		do {
			System.out.printf("Usted tiene %.2f disponibles a retirar \n", balance);
			System.out.println("Ingrese un monto a retirar en multiplo de 50");
			double withdraw = scan.nextDouble();
			if(withdraw%50 == 0 && withdraw >=50) {
				if(withdraw <= 6000) {
					System.out.println("Retiro Exitoso!");
					balance = balance - withdraw;
					addToLog(withdraw, 1);
					out = true;
				}else {
					System.out.println("el monto maximo para retirar es de 6,000");
				}
				
			}else {
				System.out.println("solo puedes retirar multiplos de 50");
			}
			
		} while (!out);
		
		do{
			System.out.println("Desea donar $200 para la graduacón de ch30 y/n");
			String confirm = scan.next().toLowerCase();
			if(confirm.charAt(0) == 'y') {
				balance = balance - 200;
				addToLog(200, 2);
				System.out.println("gracias donaste $200 para la fiesta");
				outDonate = true;
			} else if(confirm.charAt(0) == 'n') {
				System.out.println("no pos gracias por no donar que te vaya bien >:c");
				outDonate = true;
			}else {
				System.out.println("escribe y/n");
			}
		}while(!outDonate);
		
		
		return balance;
		
	}
	static double depositMoney(double balance) {
		boolean out = false;
		int option = 0;
		double deposit = -1;
		int decimals = 2;
		
		do {
			printAcountType();
			option = scan.nextInt();
			if(option == 1) { // cuenta de cheques
				System.out.println("Ingresa la cantidad a depositar en multiplos de 50");
				deposit = scan.nextDouble();
				if(deposit%50 == 0) {
					System.out.println("Deposito Exitoso!");
					addToLog(deposit, 3);
					balance = balance + deposit;
					out = true;
				}else {
					System.out.println("Solo se pueden ingresar multiplos de 50");
				}
				
			}else if(option == 2) { // tarjeta de credito
				System.out.println("Ingresa la cantidad a depositar (Se pueden incluir hasta 2 decimales)");
				deposit = scan.nextDouble();
				if(checkDecimals(deposit, decimals)) {
					System.out.println("Deposito Exitoso!");
					addToLog(deposit, 4);
					balance = balance - deposit;
					out = true;
				} else {
					System.out.println("Ingresa un numero valido");
				}
				
			}else {
				System.out.println("ingresa una opcion valida");
			}
		} while (!out);
		
		
		
		return balance;
	}
	
	static boolean checkDecimals(double number, int decimals) {
		boolean isCorrect = true;
		Double d = number;
		String[] splitter = d.toString().split("\\.");
		if(splitter[1].length() > 2) {
			isCorrect = false;
		}
		return isCorrect;
	}
	
	static void addToLog(double amount, int option) {
		switch (option) {
		case 1: {
			log.add("El " + getDate() + " Retiraste $" + amount + " de tu cuenta!");
			break;
		}
		case 2: {
			log.add("El " + getDate() + " Donaste $" + amount + " a la fiesta de la ch30");
			break;
		}		
		case 3: {
			log.add("El " + getDate() + " Depositaste $" + amount + " a tu cuenta de cheques");
			break;
		}
		case 4:{
			log.add("El " + getDate() + " Depositaste $" + amount  + " a tu tarjeta de credito");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + option);
		}
		
	}
	static void printLog() {
		System.out.println(log.size());
		if(log.size() > 5) {
			for (int i = log.size()-1; i > log.size()-6; i--) {
				System.out.println(log.get(i));
			}
		}else {
			for (int i = log.size()-1; i >= 0; i--) {
				System.out.println(log.get(i));
			}
		}
		
	}
	static String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	
}
