package pedido;

import ingredientes.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CardapioTest {

    public static final String PRECO_INVALIDO = "Preco invalido.";
    public static final String INGREDIENTE_NAO_EXISTE_NO_CARDAPIO = "Ingrediente nao existe no cardapio.";
    Cardapio cardapio;

    @BeforeAll
    void setup(){
        cardapio = new Cardapio();
    }

    @Test
    void test_adicionar_ingredientes_properly(){
        int contator = 0;
        Base base = new Base(TipoBase.IORGUTE);
        Fruta fruta = new Fruta(TipoFruta.MORANGO);
        Topping topping = new Topping(TipoTopping.MEL);

        cardapio.adicionarIngrediente(base, 1.0);
        cardapio.adicionarIngrediente(fruta, 5.0);
        cardapio.adicionarIngrediente(topping, 10.0);

        assertEquals(3, cardapio.getPrecos().size());

        for (Map.Entry<Ingrediente,Double> pair : cardapio.getPrecos().entrySet()) {
            if (contator == 0) {
                assertEquals(new Base(TipoBase.IORGUTE), pair.getKey());
                assertEquals(1.0, pair.getValue());
            }
            if (contator == 1) {
                assertEquals(new Topping(TipoTopping.MEL), pair.getKey());
                assertEquals(10.0, pair.getValue());
            }
            if (contator == 2) {
                assertEquals(new Fruta(TipoFruta.MORANGO), pair.getKey());
                assertEquals(5.0, pair.getValue());
            }
            contator += 1;
        }
    }

    @Test
    void test_adicionar_ingredientes_exception_precoInvalido(){
        Base base = new Base(TipoBase.IORGUTE);
        Fruta fruta = new Fruta(TipoFruta.MORANGO);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cardapio.adicionarIngrediente(base, -9.0));
        assertEquals(PRECO_INVALIDO,ex.getMessage());


        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> cardapio.adicionarIngrediente(fruta, 0.0));
        assertEquals(PRECO_INVALIDO,ex1.getMessage());
    }

    @Test
    void test_atualizar_ingredientes_properly(){
        Base base = new Base(TipoBase.IORGUTE);
        Fruta fruta = new Fruta(TipoFruta.MORANGO);
        Topping topping = new Topping(TipoTopping.MEL);

        cardapio.adicionarIngrediente(base, 1.0);
        cardapio.adicionarIngrediente(fruta, 5.0);
        cardapio.adicionarIngrediente(topping, 10.0);

        cardapio.atualizarIngrediente(new Base(TipoBase.IORGUTE), 9.0);

        assertEquals(3, cardapio.getPrecos().size());
        assertEquals(9.0, cardapio.getPrecos().get(new Base(TipoBase.IORGUTE)));
        assertEquals(5.0, cardapio.getPrecos().get(new Fruta(TipoFruta.MORANGO)));
        assertEquals(10.0, cardapio.getPrecos().get(new Topping(TipoTopping.MEL)));
    }

    @Test
    void test_atualizar_ingredientes_exception_precoInvalido(){
        Base base = new Base(TipoBase.IORGUTE);
        Fruta fruta = new Fruta(TipoFruta.MORANGO);

        cardapio.adicionarIngrediente(base, 9.0);
        cardapio.adicionarIngrediente(fruta, 10.0);


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cardapio.atualizarIngrediente(new Base(TipoBase.IORGUTE), -9.0));
        assertEquals(PRECO_INVALIDO, ex.getMessage());

        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class,
                () -> cardapio.atualizarIngrediente(new Fruta(TipoFruta.MORANGO), 0.0));
        assertEquals(PRECO_INVALIDO, ex1.getMessage());

    }

    @Test
    void test_atualizar_ingredientes_exception_ingredienteInexistente(){
        Base base = new Base(TipoBase.IORGUTE);
        Fruta fruta = new Fruta(TipoFruta.MORANGO);

        cardapio.adicionarIngrediente(base, 9.0);
        cardapio.adicionarIngrediente(fruta, 10.0);
        cardapio.adicionarIngrediente(fruta, 10.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cardapio.atualizarIngrediente(new Topping(TipoTopping.MEL), 19.0));

        assertEquals(INGREDIENTE_NAO_EXISTE_NO_CARDAPIO, ex.getMessage());
    }

    @Test
    void test_remover_ingredientes_properly(){
        int contator = 0;
        Base base = new Base(TipoBase.IORGUTE);
        Fruta fruta = new Fruta(TipoFruta.MORANGO);
        Topping topping = new Topping(TipoTopping.MEL);

        cardapio.adicionarIngrediente(base, 1.0);
        cardapio.adicionarIngrediente(fruta, 5.0);
        cardapio.adicionarIngrediente(topping, 10.0);

        cardapio.removerIngrediente(new Base(TipoBase.IORGUTE));

        assertEquals(2, cardapio.getPrecos().size());

        for (Map.Entry<Ingrediente,Double> pair : cardapio.getPrecos().entrySet()) {
            if (contator == 0) {
                assertEquals(new Topping(TipoTopping.MEL), pair.getKey());
                assertEquals(10.0, pair.getValue());
            }
            if (contator == 1) {
                assertEquals(new Fruta(TipoFruta.MORANGO), pair.getKey());
                assertEquals(5.0, pair.getValue());
            }
            contator += 1;
        }
    }

    @Test
    void test_remover_ingredientes_exception_ingredienteInexistente(){
        Base base = new Base(TipoBase.IORGUTE);

        cardapio.adicionarIngrediente(base, 1.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cardapio.removerIngrediente(new Topping(TipoTopping.MEL)));

        assertEquals(INGREDIENTE_NAO_EXISTE_NO_CARDAPIO, ex.getMessage());

    }

    @Test
    void test_buscar_ingrediente_properly(){
        Base base = new Base(TipoBase.IORGUTE);
        cardapio.adicionarIngrediente(base, 1.0);

        assertEquals(1.0, cardapio.buscarPreco(new Base(TipoBase.IORGUTE)));
    }

    @Test
    void test_buscar_ingrediente_exception_ingredienteInexistente(){
        Base base = new Base(TipoBase.IORGUTE);

        cardapio.adicionarIngrediente(base, 1.0);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cardapio.buscarPreco(new Base(TipoBase.SORVETE)));
        assertEquals(INGREDIENTE_NAO_EXISTE_NO_CARDAPIO, ex.getMessage());

    }

}