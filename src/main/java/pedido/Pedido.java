package pedido;

import ingredientes.Adicional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pedido {

    private final int id;
    private final ArrayList<ItemPedido> itens;
    private final Cliente cliente;

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
                for (Adicional adicional : item.getShake().getAdicionais()) {
                    precoAdicionais += cardapio.buscarPreco(adicional);
                }
            }

            double precoFinal = ((precoBase * tamanhoMultiplicador) + precoAdicionais) * item.getQuantidade();
            total += precoFinal;
        }
        return total;
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado) {
        List<ItemPedido> existingItens = itens.stream().filter(item -> item.getShake().equals(itemPedidoAdicionado.getShake())).collect(Collectors.toList());

        if (existingItens.isEmpty()) {
            itens.add(itemPedidoAdicionado);
        } else {
            ItemPedido itemPedido = existingItens.get(0);
            itemPedidoAdicionado.setQuantidade(itemPedido.getQuantidade() + itemPedidoAdicionado.getQuantidade() );
            itens.set(itens.indexOf(itemPedido), itemPedidoAdicionado);
        }
    }


    public void removeItemPedido(ItemPedido itemPedidoRemovido) {
        List<ItemPedido> existingItens = itens.stream().filter(item -> item.getShake().equals(itemPedidoRemovido.getShake())).collect(Collectors.toList());

        if (existingItens.isEmpty())
            throw new IllegalArgumentException("Item nao existe no pedido.");


        ItemPedido itemToRemove = existingItens.get(0);
        int index = itens.indexOf(itemToRemove);

        if (itemToRemove.getQuantidade() <= 1) {
            itens.remove(itemToRemove);
        } else {
            itemToRemove.setQuantidade(itemToRemove.getQuantidade() - 1);
            itens.set(index, itemToRemove);
        }
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
