package pedido;

import ingredientes.Adicional;
import ingredientes.Ingrediente;

import java.util.ArrayList;

public class Pedido {

    private int id;
    private ArrayList<ItemPedido> itens;
    private Cliente cliente;

    public Pedido(int id, ArrayList<ItemPedido> itens, Cliente cliente) {
        this.id = id;
        this.itens = itens;
        this.cliente = cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public int getId() {
        return this.id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public double calcularTotal(Cardapio cardapio) {
        double total = 0;
        for (ItemPedido item : itens) {

            Double precoBase = cardapio.buscarPreco(item.getShake().getBase());
            double tamanhoMultiplicador = item.getShake().getTipoTamanho().multiplicador;
            double precoAdicionais = 0;

            if (item.getShake().getAdicionais() != null && !item.getShake().getAdicionais().isEmpty()) {
                for ( Adicional adicional : item.getShake().getAdicionais()) {
                    precoAdicionais += cardapio.buscarPreco(adicional);
                }
            }

            double precoFinal = ( (precoBase * tamanhoMultiplicador) + precoAdicionais ) * item.getQuantidade();
            total += precoFinal;
        }
        return total;
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado) {

        int oldQtd = 0;
        boolean shouldAdd = true;
        for( ItemPedido item : itens) {

            System.out.println("Exists: " + item.getShake());
            System.out.println("Add: " + itemPedidoAdicionado.getShake());
            if (item.getShake().equals(itemPedidoAdicionado.getShake())) {
                System.out.println("Entrei no if");
                oldQtd = item.getQuantidade();
                itemPedidoAdicionado.setQuantidade(itemPedidoAdicionado.getQuantidade() + oldQtd);
                itens.set( itens.indexOf(item), itemPedidoAdicionado);
                shouldAdd = false;
            }
        }

        System.out.println(shouldAdd);
        if (shouldAdd)
            itens.add(itemPedidoAdicionado);
    }

    public boolean removeItemPedido(ItemPedido itemPedidoRemovido) {
        //substitua o true por uma condição

            if (itens.contains(itemPedidoRemovido)) {
            itens.remove(itemPedidoRemovido);

        } else {
            throw new IllegalArgumentException("Item nao existe no pedido.");
        }
        return false;
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
