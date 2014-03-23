package com.intenthq.battleship.game.entities;

/**
 * Created by dobriy on 19/03/14.
 */
public enum FacingDirection {
    N, S, E, W;



    public FacingDirection onTheRight() {

        switch (this) {
            case E:
                return S;
            case W:
                return N;
            case N:
                return E;
            case S:
                return W;
            default:
                return null;
        }

    }

    public FacingDirection onTheLeft() {

        switch (this) {
            case E:
                return N;
            case W:
                return S;
            case N:
                return W;
            case S:
                return E;
            default:
                return null;
        }

    }
}
