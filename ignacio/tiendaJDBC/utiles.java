package tienda;

import java.util.Scanner;

public final class utiles {

	public final static String leerString(String mensaje, Scanner sc) {
		System.out.println(mensaje);
		return sc.nextLine();

	}


	public final static int leerNum(String mensaje, Scanner sc) {
			int num = 0;
			System.out.println(mensaje);
			while(true) {
			try {
				num = Integer.parseInt(sc.nextLine());
			
			return num;
			}
			
			 catch(NumberFormatException e) {System.err.println("No es un numero");}
			}
	}
	public final static int leerNumLimites(String mensaje, Scanner sc, int min, int max) {
		int num = 0;
		System.out.println(mensaje);
		while(true) {
		try {
			
			num = Integer.parseInt(sc.nextLine());
		if(num < min || num > max) {throw new IllegalArgumentException();}
		else {
		return num;}
		}
		catch(IllegalArgumentException e) {System.err.println("No esta en los limites o no es un numero");}
		}
}
	public final static float leerDouLimites(String mensaje, Scanner sc, int min, int max) {
		float num = 0;
		System.out.println(mensaje);
		while(true) {
		try {
			
			num = Float.parseFloat(sc.nextLine());
		if(num < min || num > max) {throw new IllegalArgumentException();}
		else {
		return num;}
		}
		catch(IllegalArgumentException e) {System.err.println("No esta en los limites");}
		}
}
}
