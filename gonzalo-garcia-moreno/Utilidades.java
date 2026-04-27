package tarea;

import java.util.Scanner;

public final class Utilidades {
    private Utilidades(){};

    public static int leerEntero(Scanner sc, int min, int max, String mensaje){
        int numero;
        while (true){
            try{
                System.out.print(mensaje);
                numero = Integer.parseInt(sc.nextLine());
                if (numero >= min && numero <= max){
                    return numero;
                }
                System.out.println("Número fuera de rango");
            }catch(NumberFormatException e){
                System.out.println("Número no válido");
            }
        }
    }

    public static String leerCadena(Scanner sc, String mensaje){
        System.out.print(mensaje);
        return sc.nextLine();
    }

    public static double leerDoublePositivo(Scanner sc, String mensaje){
        double numero;
        while (true){
            try{
                System.out.print(mensaje);
                numero = Double.parseDouble(sc.nextLine());
                if (numero > 0){
                    return numero;
                }else{
                    System.out.println("Debe ser un número positivo");
                }
            }catch(NumberFormatException e){
                System.out.println("Número no válido");
            }
        }
    }

public static Double leerDoubleOpcional(Scanner sc, String mensaje) {
    while (true) {
        System.out.print(mensaje);
        String entrada = sc.nextLine();
        
        // Si el usuario pulsa Intro, devolvemos null
        if (entrada.isEmpty()) {
            return null;
        }
        
        try {
            double numero = Double.parseDouble(entrada);
            if (numero > 0) {
                return numero;
            }
            System.out.println("Debe ser un número positivo");
        } catch (NumberFormatException e) {
            System.out.println("Número no válido");
        }
    }
}

    public static long leerLongPositivo(Scanner sc, String mensaje){
        long numero;
        while (true){
            try{
                System.out.print(mensaje);
                numero = Long.parseLong(sc.nextLine());
                if (numero > 0){
                    return numero;
                }else{
                    System.out.println("Debe ser un número positivo");
                }
            }catch(NumberFormatException e){
                System.out.println("Número no válido");
            }
        }
    }
}

