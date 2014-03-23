package com.intenthq.battleship.game.entities;

import javax.validation.constraints.NotNull;

/**
 * Created by dobriy on 19/03/14.
 */
public class Move {

    @NotNull
    private MoveType moveType;
    private Direction direction;

    public Move() {
        this.moveType = MoveType.MOVE;
    }

    public Move(Direction direction) {
        this.moveType = MoveType.ROTATIONS;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }
}
