package demo;

import ingredientes.*;
import pedido.Cardapio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {


    private Cardapio cardapio = new Cardapio();
    private static List<Base> bases = new ArrayList<>();
    private static List<Fruta> frutas = new ArrayList<>();
    private static List<Topping> toppings = new ArrayList<>();

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Scanner input = new Scanner(System.in);

//        System.out.print("Informe seu nome: \n>> ");
//        String name = input.nextLine();
//        System.out.print("Informe seu email: \n>> ");
//        String email = input.nextLine();

//        Cliente client = new Cliente((int) Math.random(), name, email);
//        System.out.printf("Olá, %s. Esse é o cardapio de hoje: ", name);

        populateMenu();
        showMenu();


    }

    private static void populateMenu() {
        for (TipoBase tipoBase : TipoBase.values()) {
            bases.add(new Base(tipoBase));
        }

        for (TipoFruta tipoFruta : TipoFruta.values()) {
            frutas.add(new Fruta(tipoFruta));
        }

        for(TipoTopping tipoTopping : TipoTopping.values()) {
            toppings.add(new Topping(tipoTopping));
        }
    }

    private static void showMenu() {

        System.out.println("=============== MENU ===============");
        System.out.println("-------------- BASES ---------------");
        bases.forEach(base -> System.out.println("- " + base));
        System.out.println("-------------- FRUTAS --------------");
        frutas.forEach(fruta -> System.out.println("- " + fruta));
        System.out.println("-------------- TOPPING -------------");
        toppings.forEach( topping -> System.out.println("- " + topping));

    }


}
