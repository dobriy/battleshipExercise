package com.intenthq.battleship.game.entities;

/**
 * Created by dobriy on 19/03/14.
 */
public class GridCell {
    private int x;
    private int y;
    private Ship ship;

    public GridCell(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GridCell gridCell = (GridCell) o;

        if (x != gridCell.x) return false;
        if (y != gridCell.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public GridCell nextStepOnNorth() {
        GridCell newGridCell = new GridCell(x, y+1);

        return newGridCell;

    }

    public GridCell nextStepOnSouth() {
        GridCell newGridCell = new GridCell(x, y-1);
        return newGridCell;
    }

    public GridCell nextStepOnEast() {
        GridCell newGridCell = new GridCell(x+1, y);

        return newGridCell;
    }

    public GridCell nextStepOnWest() {
        GridCell newGridCell = new GridCell(x-1, y);

        return newGridCell;
    }
}
