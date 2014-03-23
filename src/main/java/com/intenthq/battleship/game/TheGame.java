package com.intenthq.battleship.game;

import com.intenthq.battleship.game.entities.*;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dobriy on 19/03/14.
 */
public class TheGame {
    @NotNull
    private GridCell upperRightCell;
    private Map<GridCell, Ship> ships;

    public TheGame(@NotNull GridCell upperRightCell) {
        startNewGame(upperRightCell);
    }

    public void addNewShip(@NotNull Ship ship) {
        if (!isInTheMap(ship.getCurrentGridCell())) {
            throw new IllegalArgumentException("Grid cell is out of the map");
        }
        if (ships.containsKey(ship.getCurrentGridCell())) {
            throw new IllegalArgumentException("Couldn't put ship on the " + ship.getCurrentGridCell() + " another ship is there");
        }
        ships.put(ship.getCurrentGridCell(), ship);
    }



    public void moveShipOn(@NotNull GridCell gridCell, @NotNull List<Move> moves) {
        if (ships.isEmpty()) {
            throw new IllegalStateException("Add ships first");
        }
        if (!isInTheMap(gridCell)) {
            throw new IllegalArgumentException("Grid cell is out of the map");
        }
        if (!ships.containsKey(gridCell)) {
            throw new IllegalArgumentException("Could not find ship in cel " + gridCell);
        }
        if(ships.get(gridCell).isHit()) {
            throw new IllegalStateException("You cannot moveShipOn the ship that has been hit");
        }
        // Creating the copy of the ship object, so we wouldn't damage original object if there is an error
        Ship newShip = new Ship(ships.get(gridCell));
        GridCell newGridCell;
        FacingDirection newFacingDirection;
        for (Move move : moves) {
            switch (move.getMoveType()) {
                case MOVE:
                    newGridCell = makeStep(newShip);
                    newShip.setCurrentGridCell(newGridCell);
                    break;
                case ROTATIONS:
                    newFacingDirection = rotate(newShip, move);
                    newShip.setFacingDirection(newFacingDirection);

                    break;
            }

        }
        if (!newShip.getCurrentGridCell().equals(gridCell) && ships.containsKey(newShip.getCurrentGridCell())) {
            throw new IllegalArgumentException("Couldn't moveShipOn ship on the " + newShip.getCurrentGridCell() + " another ship is there");
        }
        // removing old  object and adding new one
        ships.remove(gridCell);
        ships.put(newShip.getCurrentGridCell(), newShip);
    }

    private FacingDirection rotate(Ship ship, Move move) {
        FacingDirection newFacingDirection;
        if (move.getDirection().equals(Direction.L)) {
            newFacingDirection = ship.getFacingDirection().onTheLeft();
        } else {
            newFacingDirection = ship.getFacingDirection().onTheRight();
        }
        return newFacingDirection;
    }


    private GridCell makeStep(@NotNull Ship ship) {
        GridCell newGridCell;
        switch (ship.getFacingDirection()) {
            case E:
                newGridCell = ship.getCurrentGridCell().nextStepOnEast();
                break;
            case W:
                newGridCell = ship.getCurrentGridCell().nextStepOnWest();
                break;
            case N:
                newGridCell = ship.getCurrentGridCell().nextStepOnNorth();
                break;
            case S:
                newGridCell = ship.getCurrentGridCell().nextStepOnSouth();
                break;
            default:
                throw new IllegalStateException("Unknown enum " + ship.getFacingDirection());
        }
        //if new ship hit the wall we just ignore the moveShipOn

        if (newGridCell.getX() > upperRightCell.getX() || newGridCell.getX() < 0) {
            newGridCell.setX(upperRightCell.getX());
        }
        if (newGridCell.getY() > upperRightCell.getY() || newGridCell.getY() < 0)
            newGridCell.setY(upperRightCell.getY());

        return newGridCell;
    }

    public void hit(@NotNull GridCell gridCell) {
        if (ships.isEmpty()) {
            throw new IllegalStateException("Add ships first");
        }
        if (!isInTheMap(gridCell)) {
            throw new IllegalArgumentException("Grid cell is out of the map");
        }
        if (ships.containsKey(gridCell)) {
            ships.get(gridCell).setHit(true);
        }
    }


    private boolean isInTheMap(@NotNull GridCell gridCell) {
        return gridCell.getX() <= this.upperRightCell.getX() && gridCell.getY() <= this.upperRightCell.getY();
    }

    public Collection<Ship> getResult() {
        return ships.values();
    }

    public void startNewGame(@NotNull GridCell upperRightCell) {
        this.upperRightCell = upperRightCell;
        ships = new HashMap<>();
    }

    public GridCell getUpperRightCell() {
        return upperRightCell;
    }
}
