//Marcos Pallas Perez;
/**
 * @author a023916070a
 * @version 1.0
 */
package examen2eva_programa2;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import personaExamen.*;
public class GestionPersonas {

	/**
	 * Imprime el menu
	 */
	static void imprimirMenu() {
		System.out.println("OPCIONES 1-5");
		System.out.println("1. Introducir una nueva persona");
		System.out.println("2. Eliminar una persona");
		System.out.println("3. Mostrar todas las personas");
		System.out.println("4. Mostar la deuda de una persona");
		System.out.println("5. Salir");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Persona> lista=new ArrayList<>();
		
		String nombre="";
		int edad=0;
		int sueldo=0;
		int opcion=0;
		int posicion=0;
		int deuda=0;
		
		Scanner entrada=new Scanner(System.in);
		/**
		 * Bucle do while el cual imprime un menu
		 */
		do {
			GestionPersonas.imprimirMenu();
			opcion=entrada.nextInt();
			
			/**
			 * switch para que realizar en cada opcion
			 */
			switch(opcion) {
			case 1:
				System.out.println("Dime el nombre de la persona");
				nombre=entrada.next();
				entrada.nextLine();
				
				System.out.println("Dime la edad de la persona");
				edad=entrada.nextInt();
				
				System.out.println("Dime el sueldo de la persona");
				sueldo=entrada.nextInt();
				
				lista.add(new Persona(nombre,edad,sueldo));
				break;
			case 2:
				try {
				System.out.println("Dime la posicion que quieras borrar");
				posicion=entrada.nextInt();
				lista.remove(posicion);
				}catch(IndexOutOfBoundsException ex1) {
					System.out.println("La posicion no existe");
				}
				break;
				
			case 3:
				for(Persona per:lista) {
					System.out.print("Nombre: " + per.getNom() + " Edad: " + per.getEdad() + " Sueldo: " + per.getSueldo() );
					System.out.println();
				}
			break;
			
			case 4:
				try {
				System.out.println("Dime la posicion de una persona para saber su deuda");
				posicion=entrada.nextInt();
					
				deuda=lista.get(posicion).obtenerDeuda();
				if(deuda>=0) {
					System.out.println("La persona " + lista.get(posicion).getNom()+ " no tiene deuda");
				}else {
					System.out.println("La persona " + lista.get(posicion).getNom()+ " tiene deuda");
				}
				
				
				}catch(IndexOutOfBoundsException ex) {
					System.out.println("La posicion no existe");
				}
				break;
				
			case 5:
				System.out.println("Saliendo");
			}
		}while(opcion!=5);
		
	}

}
