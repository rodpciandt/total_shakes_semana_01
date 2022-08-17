package armazem;

import ingredientes.Ingrediente;

import java.util.TreeMap;

public class Armazem {

    private final TreeMap<Ingrediente, Integer> estoque;

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
        checkIfIngredientExists(ingrediente);
        return estoque.get(ingrediente);

    }

    public void descadastrarIngredienteEmEstoque(Ingrediente ingrediente) {
        checkIfIngredientExists(ingrediente);
        this.estoque.remove(ingrediente);
    }

    public void adicionarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, Integer qtd) {
        checkIfQuantityIsvalid(qtd);
        checkIfIngredientExists(ingrediente);

        estoque.put(ingrediente, estoque.get(ingrediente) + qtd);

    }

    public void reduzirQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, Integer qtd) {
        checkIfIngredientExists(ingrediente);
        checkIfQuantityIsvalid(qtd);

        Integer oldQtd = estoque.get(ingrediente);

        if (oldQtd < qtd)
            throw new IllegalArgumentException("Quantidade Invalida");
        else if (oldQtd - qtd == 0)
            estoque.remove(ingrediente);
        else
            estoque.put(ingrediente, estoque.get(ingrediente) - qtd);

    }

    private void checkIfIngredientExists(Ingrediente ingrediente) {
        if (!estoque.containsKey(ingrediente))
            throw new IllegalArgumentException("Ingrediente nao encontrado");
    }

    private void checkIfQuantityIsvalid(Integer qtd) {
        if (qtd <= 0)
            throw new IllegalArgumentException("Quantidade invalida");
    }
}
