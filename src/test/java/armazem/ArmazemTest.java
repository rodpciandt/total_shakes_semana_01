package armazem;

import ingredientes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArmazemTest {


    private Armazem armazem;
    private final String INGREDIENTE_JA_CADASTRADO = "Ingrediente ja cadastrado";
    private final String INGREDIENTE_NAO_ENCONTRADO = "Ingrediente nao encontrado";
    private final String QUANTIDADE_INVALIDA = "Quantidade invalida";

    @BeforeEach
    public void beforeEach() {
        armazem = new Armazem();
    }


    @Test
    @DisplayName("[CadastrarIngrediente]: quando um novo ingrediente for cadastrado, quantidade deveria ser 0")
    public void cadastrarIngrediente_quantidadeDeveSerZero() {
        Ingrediente ingrediente = new Base(TipoBase.IORGUTE);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);

        Integer qtd = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(0, qtd);
    }

    @Test
    @DisplayName("[CadastrarIngrediente]: quando cadastrar um ingrediente que ja existe, deve jogar exception")
    public void cadastrarIngrediente_exceptionQuandoJaExiste() {
        Ingrediente ingrediente = new Base(TipoBase.IORGUTE);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.cadastrarIngredienteEmEstoque(ingrediente));
        assertEquals(ex.getMessage(), INGREDIENTE_JA_CADASTRADO);
    }

    @Test
    @DisplayName("[DescadastrarIngrediente]: quando remover Ingrediente, ele nao deveria ser encontrado no estoque")
    public void descadastrarIngrediente_naoDeveriaEstarNoEstoqueAposSerRemovido() {
        Ingrediente ingrediente = new Base(TipoBase.IORGUTE);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
        armazem.descadastrarIngredienteEmEstoque(ingrediente);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));

        assertEquals(ex.getMessage(), INGREDIENTE_NAO_ENCONTRADO);
    }

    @Test
    @DisplayName("[DescadastrarIngrediente]: quando nao encontrar ingrediente, deveria jogar exception")
    public void descadastrarIngrediente_exceptionQuandoNaoEncontrarIngrediente() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> armazem.descadastrarIngredienteEmEstoque(new Fruta(TipoFruta.BANANA)));

        assertEquals(exception.getMessage(), INGREDIENTE_NAO_ENCONTRADO);
    }

    @Test
    @DisplayName("[AdicionarQuantidade]: quando adicionar quantidade no  estoque, quantidade deveria ser atualizada")
    public void adicionarQuantidade_quantidadeDeveriaSerAtualizada() {

        Ingrediente ingrediente = new Base(TipoBase.IORGUTE);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 3);
        Integer qtd = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(qtd, 3);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 2);
        qtd = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(qtd, 5);
    }


    @Test
    @DisplayName("[AdicionarQuantidade]: quando adicionar quantidade menor ou igual zero, deveria jogar exception")
    public void adicionarQuantidade_exceptionQuandoAdicionarQtdMenorOuIgualAZero() {
        // parametrizado
        Ingrediente ingrediente = new Base(TipoBase.IORGUTE);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 0));
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, -1));

        assertEquals(ex.getMessage(), QUANTIDADE_INVALIDA);
        assertEquals(ex1.getMessage(), QUANTIDADE_INVALIDA);
    }

    @Test
    @DisplayName("[AdicionarQuantidade]: quando adicionar quantidade a um ingrediente que nao esta no estoque, deveria jogar exception")
    public void adicionarQuantidade_exceptionQuandoIngredienteNaoExiste() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.IORGUTE), 3));
        assertEquals(ex.getMessage(), INGREDIENTE_NAO_ENCONTRADO);
    }

    @Test
    @DisplayName("[DiminuirQuantidade]: quando diminuir quantidade, a quantidade em estoque deveria ser atualizada")
    public void diminuirQuantidade_quantidadeDeveSerAtualizada() {
        Ingrediente ingrediente = new Fruta(TipoFruta.BANANA);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 2);

        Integer qtd = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);

        assertEquals(qtd, 3);
    }

    @Test
    @DisplayName("[DiminuirQuantidade]: deveria remover o ingrediente do estoque, se quantidade a ser diminuida for igual a do estoque.")
    public void diminuirQuantidade_removerSeQuantidadeForIgualAoEstoque() {
        Ingrediente ingrediente = new Fruta(TipoFruta.BANANA);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 5);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));

        assertEquals(ex.getMessage(), INGREDIENTE_NAO_ENCONTRADO);
    }

    @Test
    @DisplayName("[DiminuirQuantidade]: deveria jogar exception quando ingrediente a ser removido nao estiver no estoque")
    public void diminuirQuantidade_exceptionQuandoNaoEncontrarNoEstoque() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.IORGUTE), 5));

        assertEquals(ex.getMessage(), INGREDIENTE_NAO_ENCONTRADO);

    }

    @Test
    @DisplayName("[DiminuirQuantidade]: deveria jogar exception quando quantidade a ser removida for menor ou igual a zero")
    public void diminuirQuantidade_exceptionQuandoQtfForMenorOuIgualAZero() {
        Ingrediente ingrediente = new Fruta(TipoFruta.BANANA);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 0));
        assertEquals(ex.getMessage(), QUANTIDADE_INVALIDA);

        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, -1));
        assertEquals(ex1.getMessage(), QUANTIDADE_INVALIDA);

    }

    @Test
    @DisplayName("[DiminuirQuantidade]: deveria jogar exception quando quantidade a ser removida for maior que a quantidade em estoque")
    public void diminuirQuantidade_exceptionQuandoQtdForMaiorQueNoEstoque() {
        Ingrediente ingrediente = new Fruta(TipoFruta.BANANA);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        assertThrows(IllegalArgumentException.class, () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 8));
    }

    @Test
    @DisplayName("[ConsultarQuantidade]: deveria encontrar um ingrediente que esta no estoque")
    public void consultarQuantidade_deveriaEncontrarIngrediente() {
        Ingrediente ingrediente = new Fruta(TipoFruta.BANANA);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);

        Integer integer = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);

        assertEquals(integer, 0);
    }

    // caso n exista, ex.

    @Test
    @DisplayName("[ConsultarQuantidade]: deveria jogar exception quando ingrediente consultado nao existir")
    public void consultarQuantidade_deveriaJogarExceptionSeNaoEncontrarIngrediente() {
        assertThrows(IllegalArgumentException.class, () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.IORGUTE)));
    }


}
