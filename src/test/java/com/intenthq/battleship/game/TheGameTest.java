package com.intenthq.battleship.game;

import com.intenthq.battleship.game.entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by dobriy on 20/03/14.
 */
public class TheGameTest {


    public static final int MAX_X = 5;
    public static final int MAX_Y = 5;

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void itShouldBeAllowedToAddShipAfterStartingTheGame() throws Exception {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.E);
        game.addNewShip(ship);
        Collection<Ship> result = game.getResult();
        assertFalse(result.isEmpty());
        assertThat(result.size(), is(1));
        assertTrue(result.contains(ship));
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldNotBeAllowedToAddMoreThanOneShipOnTheSameCell() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.E);
        game.addNewShip(ship);
        GridCell gridCell2 = new GridCell(1, 2);
        Ship ship2 = new Ship(gridCell2, FacingDirection.E);
        game.addNewShip(ship2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldNotBeAllowedToAddShipOutsideTheGrid() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(game.getUpperRightCell().getX() + 1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.E);
        game.addNewShip(ship);

    }

    @Test
    public void itShouldBeAllowedToMoveShipAfterAddingIt() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.N);
        game.addNewShip(ship);
        List<Move> moves = new ArrayList<>();
        //(1, 2) LMLMLMLMM
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move());
        //(1, 3, N)
        game.moveShipOn(gridCell, moves);

        GridCell expectedGridCell = new GridCell(1, 3);
        FacingDirection expectedFacingDirection = FacingDirection.N;
        Ship expectedShip = new Ship(expectedGridCell, expectedFacingDirection);
        Collection<Ship> result = game.getResult();
        assertFalse(result.isEmpty());
        assertThat(result.size(), is(1));
        assertTrue(result.contains(expectedShip));
    }

    @Test
    public void itShouldBeAllowedToMoveShipAndEndUpAtTheSameSpot() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.N);
        game.addNewShip(ship);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        game.moveShipOn(gridCell, moves);

        GridCell expectedGridCell = new GridCell(1, 2);
        FacingDirection expectedFacingDirection = FacingDirection.N;
        Ship expectedShip = new Ship(expectedGridCell, expectedFacingDirection);
        Collection<Ship> result = game.getResult();
        assertFalse(result.isEmpty());
        assertThat(result.size(), is(1));
        assertTrue(result.contains(expectedShip));
    }

    @Test
    public void itShouldNotBeAllowedToMoveTheShipOutSideTheBoard() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.N);
        game.addNewShip(ship);
        List<Move> moves = new ArrayList<>();
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move());
        moves.add(new Move());
        moves.add(new Move());
        moves.add(new Move());
        moves.add(new Move());
        moves.add(new Move());
        moves.add(new Move());
        game.moveShipOn(gridCell, moves);

        GridCell expectedGridCell = new GridCell(1, MAX_Y);
        FacingDirection expectedFacingDirection = FacingDirection.N;
        Ship expectedShip = new Ship(expectedGridCell, expectedFacingDirection);
        Collection<Ship> result = game.getResult();
        assertFalse(result.isEmpty());
        assertThat(result.size(), is(1));
        assertTrue(result.contains(expectedShip));
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldNotBeAllowedToMoveShipAndEndUpAtTheSameSpotAsAnotherShip() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.N);
        game.addNewShip(ship);
        GridCell gridCell2 = new GridCell(1, 3);
        Ship ship2 = new Ship(gridCell2, FacingDirection.N);
        game.addNewShip(ship2);
        List<Move> moves = new ArrayList<>();
        //(1, 2) LMLMLMLMM
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move());
        //(1, 3, N)
        game.moveShipOn(gridCell, moves);
    }

    @Test
    public void itShouldBeAllowedToHitShip() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.N);
        game.addNewShip(ship);
        GridCell gridCell2 = new GridCell(1, 3);
        Ship ship2 = new Ship(gridCell2, FacingDirection.N);
        game.addNewShip(ship2);
        game.hit(gridCell);
        Collection<Ship> result = game.getResult();
        assertFalse(result.isEmpty());
        assertThat(result.size(), is(2));
        assertTrue(result.contains(ship));
        assertTrue(result.contains(ship2));
        for (Ship resultShip : result) {
            if (resultShip.equals(ship))
                assertThat(resultShip.isHit(), is(true));
            else
                assertThat(resultShip.isHit(), is(false));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldNotBeAllowedToHitShipBeforeWeAddShips() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        game.hit(gridCell);

    }

    @Test
    public void itShouldBeAllowedToTryHitShip() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.N);
        game.addNewShip(ship);
        GridCell gridCell2 = new GridCell(1, 3);
        Ship ship2 = new Ship(gridCell2, FacingDirection.N);
        game.addNewShip(ship2);
        GridCell gridCell3 = new GridCell(2, 2);
        game.hit(gridCell3);
        Collection<Ship> result = game.getResult();
        assertFalse(result.isEmpty());
        assertThat(result.size(), is(2));
        assertTrue(result.contains(ship));
        assertTrue(result.contains(ship2));
        for (Ship resultShip : result) {

            assertThat(resultShip.isHit(), is(false));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void itShouldNotBeAllowedToMoveShipAfterItHasBeenHit() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.N);
        game.addNewShip(ship);
        game.hit(gridCell);

        List<Move> moves = new ArrayList<>();
        //(1, 2) LMLMLMLMM
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move());
        //(1, 3, N)
        game.moveShipOn(gridCell, moves);


    }

    @Test(expected = IllegalStateException.class)
    public void itShouldNotBeAllowedToMoveShipsBeforeWeAddShips() {
        TheGame game = getStandardGame();
        GridCell gridCell = new GridCell(1, 2);
        Ship ship = new Ship(gridCell, FacingDirection.N);
        game.hit(gridCell);

        List<Move> moves = new ArrayList<>();
        //(1, 2) LMLMLMLMM
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move(Direction.L));
        moves.add(new Move());
        moves.add(new Move());
        //(1, 3, N)
        game.moveShipOn(gridCell, moves);


    }


    private TheGame getStandardGame() {
        GridCell gridCell = new GridCell(MAX_X, MAX_Y);
        return new TheGame(gridCell);
    }
}
