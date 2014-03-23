package com.intenthq.battleship.game;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;

/**
 * Created by dobriy on 22/03/2014.
 */
public class InputProcessorTest {

    private static final String SAMPLE_INPUT = "(5, 5)\n" +
            "(1, 2, N) (3, 3, E)\n" +
            "(1, 2) LMLMLMLMM\n" +
            "(2, 3)\n" +
            "(3, 3) MRMMRMRRM\n" +
            "(1, 3)";
    private static final String SAMPLE_OUTPUT = "(1, 3, N) SUNK\n" +
            "(4, 1, E)";

    private IInputProcessor inputProcessor;
    @Before
    public void setUp() throws Exception {

        inputProcessor = new InputProcessor();
    }

    @Test
    public void shouldReturnCorrectResultForTheCorrectInput(){
        String result = inputProcessor.processInput(SAMPLE_INPUT);
        assertThat(result, is(SAMPLE_OUTPUT));
    }

    @Test
    public void shouldReturnInformationStringWhenSizeOfMapSent() {
        String result = inputProcessor.processInput("(5, 5)");
        assertThat(result.isEmpty(), is(false));
    }
    @Test
    public void shouldReturnResultEvenWhenNoHitBeanMade() {
        String result = inputProcessor.processInput("(5, 5)\n" +
        "(1, 2, N) (3, 3, E)\n");
        String expectedResult = "(1, 2, N)\n(3, 3, E)";
        assertThat(result, is(expectedResult));
    }
    @Test
    public void shouldReturnErrorWhenInputInWrongFormat() {
        String result = inputProcessor.processInput("test");
        assertThat(result.toLowerCase().contains("error"), is(true));
    }

   @Test
    public void shouldReturnPartialResultAndErrorWhenStartWithValidInputAndEndWithInvalid() {
       String result = inputProcessor.processInput(SAMPLE_INPUT + "\ntest");
       assertThat(result.toLowerCase().contains("error"), is(true));
       assertThat(result.contains(SAMPLE_OUTPUT), is(true));
   }@Test
    public void shouldNotBeAllowedToMoveSunkedShip() {
       String result = inputProcessor.processInput(SAMPLE_INPUT + "\n(1, 3) LRMRM");
       assertThat(result.toLowerCase().contains("error"), is(true));
       assertThat(result.contains(SAMPLE_OUTPUT), is(true));
   }

}
