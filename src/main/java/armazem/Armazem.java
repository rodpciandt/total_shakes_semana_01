package armazem;

import ingredientes.Ingrediente;

import java.util.TreeMap;

public class Armazem {

    private TreeMap<Ingrediente, Integer> estoque;

    public Armazem() {
        estoque = new TreeMap<>();
    }

    public void cadastrarIngredienteEmEstoque(Ingrediente ingrediente) {

        if (!estoque.containsKey(ingrediente)) {
            this.estoque.put(ingrediente, 0);
        } else {
            throw new IllegalArgumentException("Ingrediente ja cadastrado");
        }
    }

    public Integer consultarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente) {

        if (estoque.containsKey(ingrediente)) {
            return estoque.get(ingrediente);
        } else {
            throw new IllegalArgumentException("Ingrediente nao encontrado");
        }
    }

    public void descadastrarIngredienteEmEstoque(Ingrediente ingrediente) {


        if (estoque.containsKey(ingrediente))
            this.estoque.remove(ingrediente);
        else
            throw new IllegalArgumentException("Ingrediente nao encontrado");
    }
}
