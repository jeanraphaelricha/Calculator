package com.jrricha.calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.text.MessageFormat;	// pour formater les System.out.println
import java.time.Duration;	// pour mesurer la durée totale des tests
import java.time.Instant;	// pour mesurer la durée totale des tests
import java.util.Set;		// pour l'ensemble des chiffres d'un entier

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CalculatorTest {
    private Calculator calculatorEnTest;
    private static Instant startedAt;	// variable de classe

    @BeforeEach
    public void setUp() {		// montage

        calculatorEnTest = new Calculator();
    }

    @AfterEach
    public void tearDown() {	// démontage
        calculatorEnTest = null;
    }

    @BeforeAll
    static public void suiteSetUp() {	// montage avant tous les tests
        System.out.println("Tic chrono avant tous les tests");
        startedAt = Instant.now();
    }

    @AfterAll
    static public void suiteTearDown() {	// démontage après tous les tests
        System.out.println("Tic chrono apres tous les tests");
        Instant endedAt = Instant.now();
        long duree = Duration.between(startedAt, endedAt).toMillis();
        System.out.println("Duree des tests : " + duree + " millisecondes");
        System.out.println(MessageFormat.format("Duree des tests : {0} ms", duree));
    }

    @Test
    @DisplayName("1 + 2 = 3")
    void add_la_somme_de_2_entiers_devrait_etre_un_entier() {
        // GIVEN
        int un = 1;
        int deux = 2;

        // WHEN
        int somme = calculatorEnTest.add(un, deux);

        // THEN
        //assertEquals(3, calculator.add(1, 2), "1 + 2 should equal 3"); // JUnit
        assertThat(somme).isEqualTo(3);	// assertJ
    }

    @DisplayName("Somme de 2 int " )
    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
            "0,    1,   1",
            "1,    2,   3",
            "49,  51, 100",
            "1,  100, 101",
            "2147483646,    1,   2147483647"
    })
/* le cas  "2147483647,    1,   2147483648"
   correspondant à un dépassement de capacité du type int doit être géré par le déclenchement d'une exception
   --> à approfondir
   NE PAS ajouter @Test quand il s'agit d'un Parametrized test
*/
    void add_la_somme_de_2_entiers_devrait_etre_un_entier_parametre(int first, int second, int expectedResult) {
        // GIVEN

        // WHEN
        int somme = calculatorEnTest.add(first, second);
        // THEN
        //assertEquals(expectedResult, calculatorEnTest.add(first, second),
        //		() -> first + " + " + second + " should equal " + expectedResult);	// JUnit

        assertThat(somme).isEqualTo(expectedResult);	// assertJ
    }
    @Test
    @DisplayName ("Produit de 2 int")
    void multiply_le_produit_de_2_entiers_devrait_etre_un_entier() {
        // GIVEN
        int un = 1;
        int deux = 2;
        // WHEN
        int produit = calculatorEnTest.multiply(un, deux);
        // THEN
        //assertEquals(produit, calculatorEnTest.multiply(un, deux),
        //			() -> un + " + " + deux + " should equal " + produit);	// JUnit

        assertThat(produit).isEqualTo(produit);	// assertJ
    }
    @Timeout(1)	// en secondes
    @Test
    public void longCalcul_devrait_durer_moins_d_1_seconde () {
        // GIVEN

        // WHEN
        calculatorEnTest.longCalcul(900); 	// en millisecondes

        // THEN
        // pas d'assertion
    }
    @Test
    public void digitsSet_devrait_retourner_les_chiffres_d_un_entier_positif() {
        //GIVEN
        int entierPositif = 97689;

        //WHEN
        Set<Integer> ensembleChiffres = calculatorEnTest.ensembleChiffres(entierPositif);

        //THEN
        assertThat(ensembleChiffres).containsExactlyInAnyOrder(6, 7, 8, 9);
    }

    @Test
    public void digitsSet_devrait_retourner_les_chiffres_d_un_entier_negatif() {
        //GIVEN
        int entierNegatif = -1;

        //WHEN
        Set<Integer> ensembleChiffres = calculatorEnTest.ensembleChiffres(entierNegatif);

        //THEN
        assertThat(ensembleChiffres).containsExactlyInAnyOrder(1);
    }
    @Test
    public void digitsSet_devrait_retourner_le_chiffre_0_d_un_entier_nul() {
        //GIVEN
        int entierNul = 0000;

        //WHEN
        Set<Integer> ensembleChiffres = calculatorEnTest.ensembleChiffres(entierNul);

        //THEN
        assertThat(ensembleChiffres).containsExactlyInAnyOrder(0);
    }
}