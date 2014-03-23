package com.intenthq.battleship.game.entities;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dobriy on 19/03/14.
 */
public class Ship {
    private List<Move> moves;
    @NotNull
    private GridCell currentGridCell;
    @NotNull
    private FacingDirection facingDirection;
    private boolean hit;

    public Ship(GridCell currentGridCell, FacingDirection facingDirection) {
        this.currentGridCell = currentGridCell;
        this.facingDirection = facingDirection;
    }

    public Ship(Ship ship) {
        this.currentGridCell = new GridCell(ship.getCurrentGridCell().getX(), ship.getCurrentGridCell().getY());
        this.facingDirection = ship.getFacingDirection();
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public GridCell getCurrentGridCell() {
        return currentGridCell;
    }

    public void setCurrentGridCell(GridCell currentGridCell) {
        this.currentGridCell = currentGridCell;
    }


    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public FacingDirection getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(FacingDirection facingDirection) {
        this.facingDirection = facingDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (currentGridCell != null ? !currentGridCell.equals(ship.currentGridCell) : ship.currentGridCell != null)
            return false;
        if (facingDirection != ship.facingDirection) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currentGridCell != null ? currentGridCell.hashCode() : 0;
        result = 31 * result + (facingDirection != null ? facingDirection.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + currentGridCell.getX() + ", " + currentGridCell.getY() + ", " + facingDirection + ")" + (hit ? " SUNK" : "");
    }
}
