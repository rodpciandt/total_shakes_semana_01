package demo;

import ingredientes.*;
import pedido.Cardapio;
import pedido.Cliente;
import pedido.ItemPedido;
import pedido.Pedido;
import produto.Shake;
import produto.TipoTamanho;

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

        System.out.print("Informe seu nome: \n>> ");
        String name = input.nextLine();
        System.out.print("Informe seu email: \n>> ");
        String email = input.nextLine();

        Cliente client = new Cliente(1, name, email);
        System.out.printf("Olá, %s. Esse é o cardapio de hoje: ", name);


        populateMenu();

        boolean run = true;

        while(run) {
            showMenu();

            try {
                System.out.print("Qual base você deseja?\n>> ");
                String baseDesejada = input.nextLine();

                Base base = new Base(TipoBase.valueOf(baseDesejada));

                System.out.print("Qual fruta você deseja?\n>> ");
                String frutaDesejada = input.nextLine();

                Fruta fruta = new Fruta(TipoFruta.valueOf(frutaDesejada));

                System.out.print("Qual topping você deseja?\n>> ");
                String toppingDesejado = input.nextLine();

                Topping topping = new Topping(TipoTopping.valueOf(toppingDesejado));

//                System.out.println("Deseja colocar algum adicional?");
//                String resp = input.nextLine();
//
//                System.out.println("Quais (separar os valores por virgula)?");
//                List<String> adicionaisString = Arrays.asList(input.nextLine().split(","));

                System.out.println("Qual o tamanho (P, M, G)? ");
                TipoTamanho tamanho = TipoTamanho.valueOf(input.nextLine());

                Shake shake = new Shake(base, fruta, topping, tamanho);

                System.out.println(shake);

                ItemPedido itemPedido = new ItemPedido(shake,1 );
                Pedido pedido = new Pedido(1, new ArrayList<>(List.of(itemPedido)),  client);
                System.out.println("Pedido enviado: " + pedido);

                client.serializarCliente();

                run = false;

            } catch (IllegalArgumentException ex) {
                System.out.println("Ingrediente invalido.");
            }


        }




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
        System.out.println("====================================");
    }


}
