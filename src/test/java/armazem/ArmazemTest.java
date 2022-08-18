package armazem;

import exceptions.IngredienteNaoEncontradoException;
import exceptions.QuantidadeInvalidaException;
import ingredientes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArmazemTest {


    private Armazem armazem;
    private Ingrediente ingrediente;
    private final String INGREDIENTE_NAO_ENCONTRADO = "Ingrediente nao encontrado";
    private final String QUANTIDADE_INVALIDA = "Quantidade invalida";

    @BeforeEach
    public void beforeEach() {
        armazem = new Armazem();
        ingrediente = new Base(TipoBase.IORGUTE);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
    }


    @Test
    @DisplayName("[CadastrarIngrediente]: quando um novo ingrediente for cadastrado, quantidade deveria ser 0")
    public void cadastrarIngrediente_quantidadeDeveSerZero() {
        Integer quantity = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(quantity, 0);
    }

    @Test
    @DisplayName("[CadastrarIngrediente]: quando cadastrar um ingrediente que ja existe, deve jogar exception")
    public void cadastrarIngrediente_exceptionQuandoJaExiste() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.cadastrarIngredienteEmEstoque(ingrediente));
        assertEquals("Ingrediente ja cadastrado", ex.getMessage());
    }

    @Test
    @DisplayName("[DescadastrarIngrediente]: quando remover Ingrediente, ele nao deveria ser encontrado no estoque")
    public void descadastrarIngrediente_naoDeveriaEstarNoEstoqueAposSerRemovido() {
        armazem.descadastrarIngredienteEmEstoque(ingrediente);

        var ex = assertThrows(IngredienteNaoEncontradoException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));

        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    @DisplayName("[DescadastrarIngrediente]: quando nao encontrar ingrediente, deveria jogar exception")
    public void descadastrarIngrediente_exceptionQuandoNaoEncontrarIngrediente() {
        var exception = assertThrows(IngredienteNaoEncontradoException.class,
                () -> armazem.descadastrarIngredienteEmEstoque(new Fruta(TipoFruta.BANANA)));

        assertEquals(INGREDIENTE_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("[AdicionarQuantidade]: quando adicionar quantidade no  estoque, quantidade deveria ser atualizada")
    public void adicionarQuantidade_quantidadeDeveriaSerAtualizada() {
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 3);

        Integer quantity = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(3, quantity);

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 2);
        quantity = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(5, quantity);
    }


    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("[AdicionarQuantidade]: quando adicionar quantidade menor ou igual zero, deveria jogar exception")
    public void adicionarQuantidade_exceptionQuandoAdicionarQtdMenorOuIgualAZero(Integer quantity) {
        var ex = assertThrows(QuantidadeInvalidaException.class,
                () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, quantity));

        assertEquals(QUANTIDADE_INVALIDA, ex.getMessage());
    }

    @Test
    @DisplayName("[AdicionarQuantidade]: quando adicionar quantidade a um ingrediente que nao esta no estoque, deveria jogar exception")
    public void adicionarQuantidade_exceptionQuandoIngredienteNaoExiste() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.LEITE), 3));
        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    @DisplayName("[DiminuirQuantidade]: quando diminuir quantidade, a quantidade em estoque deveria ser atualizada")
    public void diminuirQuantidade_quantidadeDeveSerAtualizada() {

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 2);

        Integer quantity = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);

        assertEquals(3, quantity);
    }

    @Test
    @DisplayName("[DiminuirQuantidade]: deveria remover o ingrediente do estoque, se quantidade a ser diminuida for igual a do estoque.")
    public void diminuirQuantidade_removerSeQuantidadeForIgualAoEstoque() {

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 5);

        var ex = assertThrows(IngredienteNaoEncontradoException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));

        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    @DisplayName("[DiminuirQuantidade]: deveria jogar exception quando ingrediente a ser removido nao estiver no estoque")
    public void diminuirQuantidade_exceptionQuandoNaoEncontrarNoEstoque() {
        var ex = assertThrows(IngredienteNaoEncontradoException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.LEITE), 5));

        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());

    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("[DiminuirQuantidade]: deveria jogar exception quando quantidade a ser removida for menor ou igual a zero")
    public void diminuirQuantidade_exceptionQuandoQtfForMenorOuIgualAZero(Integer quantity) {
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);

        var ex = assertThrows(QuantidadeInvalidaException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, quantity));
        assertEquals(QUANTIDADE_INVALIDA, ex.getMessage());

    }

    @Test
    @DisplayName("[DiminuirQuantidade]: deveria jogar exception quando quantidade a ser removida for maior que a quantidade em estoque")
    public void diminuirQuantidade_exceptionQuandoQtdForMaiorQueNoEstoque() {
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        var ex = assertThrows(QuantidadeInvalidaException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 8));

        assertEquals(QUANTIDADE_INVALIDA, ex.getMessage());
    }

    @Test
    @DisplayName("[ConsultarQuantidade]: deveria encontrar um ingrediente que esta no estoque")
    public void consultarQuantidade_deveriaEncontrarIngrediente() {
        Integer quantity = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);

        assertEquals(0, quantity);
    }

    @Test
    @DisplayName("[ConsultarQuantidade]: deveria jogar exception quando ingrediente consultado nao existir")
    public void consultarQuantidade_deveriaJogarExceptionSeNaoEncontrarIngrediente() {
        var ex = assertThrows(IngredienteNaoEncontradoException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.LEITE)));

        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }


}
