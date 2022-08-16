package armazem;

import ingredientes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArmazemTest {


    private Armazem armazem;

    @BeforeEach
    public void beforeEach() {
        armazem = new Armazem();
    }


    @Test
    @DisplayName("When creating new Ingredient, quantity should be zero.")
    public void whenCreatingNewIngredientQuantityShouldBeZero() {
        // quando cadastrar um novo ingrediente, a quantidade deve ser zero.
        Ingrediente ingrediente = new Base(TipoBase.Iorgute);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);

        Integer qtd = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(0, qtd);
    }

    @Test
    @DisplayName("When creating a ingredient that already exists, should throw IllegalArgumentException")
    public void whenCreatingIngredientThatExistsShouldThrowException() {
        Ingrediente ingrediente = new Base(TipoBase.Iorgute);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.cadastrarIngredienteEmEstoque(ingrediente));
        assertEquals(ex.getMessage(), "Ingrediente ja cadastrado");
    }

    @Test
    @DisplayName("When deleting ingredient, he should not be on stock")
    public void whenDeletingIngredientHeShouldNotBeOnStock() {
        Ingrediente ingrediente = new Base(TipoBase.Iorgute);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
        armazem.descadastrarIngredienteEmEstoque(ingrediente);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));

        assertEquals(ex.getMessage(), "Ingrediente nao encontrado");
    }

    @Test
    public void whenDeletingIngredientThatDoesNotExistsShouldThrowException() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> armazem.descadastrarIngredienteEmEstoque(new Fruta(TipoFruta.Banana)));

        assertEquals(exception.getMessage(), "Ingrediente nao encontrado");
    }

}
