package pedido;

import ingredientes.Ingrediente;

import java.util.TreeMap;

public class Cardapio {
    private final TreeMap<Ingrediente, Double> precos;

    public Cardapio() {
        this.precos = new TreeMap<>();
    }

    public TreeMap<Ingrediente, Double> getPrecos() {
        return this.precos;
    }

    public void adicionarIngrediente(Ingrediente ingrediente, Double preco) {
        checaSePrecoEValido(preco);
        this.precos.put(ingrediente, preco);

    }

    public boolean atualizarIngrediente(Ingrediente ingrediente, Double preco) {
        checaSeIngredienteExiste(ingrediente);
        checaSePrecoEValido(preco);

        this.precos.put(ingrediente, preco);
        return true;
    }

    public boolean removerIngrediente(Ingrediente ingrediente) {
        checaSeIngredienteExiste(ingrediente);
        precos.remove(ingrediente);
        return true;
    }

    public Double buscarPreco(Ingrediente ingrediente) {
        checaSeIngredienteExiste(ingrediente);
        return precos.get(ingrediente);
    }

    private void checaSeIngredienteExiste(Ingrediente ingrediente) {
        if (!precos.containsKey(ingrediente))
            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");

    }

    private void checaSePrecoEValido(Double preco) {
        if (preco <= 0)
            throw new IllegalArgumentException("Preco invalido.");
    }


    @Override
    public String toString() {
        return this.precos.toString();
    }

}
