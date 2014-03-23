package com.intenthq.battleship.game;

import com.intenthq.battleship.game.entities.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dobriy on 20/03/14.
 */

@Controller
public class InputProcessor implements IInputProcessor {

    public static final String EMPTY_RESULT = "Game is complete, but result is empty";
    private Pattern insideBracketsPattern = Pattern.compile("\\((?<X>\\d+), (?<Y>\\d+)\\)");
    private Pattern shipPattern = Pattern.compile("\\((?<X>\\d+), (?<Y>\\d+), (?<FacingDirection>[NWSE])\\)");
    private Pattern movePattern = Pattern.compile("\\((?<X>\\d+), (?<Y>\\d+)\\) (?<Moves>[LRM]+)");

    @Override
    public String processInput(String input) {
        input = input.trim();
        String msg = "";

        String[] lines = input.split("\\r?\\n");
        TheGame game = null;
        try {
            if (lines.length > 0) {
                GridCell upperRightCell = getCell(lines[0]);
                game = new TheGame(upperRightCell);
                if (lines.length > 1) {
                    List<Ship> ships = getShips(lines[1]);
                    for (Ship ship : ships) {
                        game.addNewShip(ship);
                    }
                    for (int i = 2; i < lines.length; i++) {
                        Matcher matcher = movePattern.matcher(lines[i]);
                        List<Move> moves = null;
                        GridCell gridCell;
                        if (matcher.find()) {
                            moves = getMoves(matcher.group("Moves"));
                            gridCell = getGridCell(matcher);
                        } else {
                            gridCell = getCell(lines[i]);
                        }
                        if (moves != null) {
                            game.moveShipOn(gridCell, moves);
                        } else
                            game.hit(gridCell);
                    }
                }

            }
        } catch (Exception ex) {
            msg += "There was an error: " + ex.getMessage() + ". ";
            if (game != null) {
                msg += "\n";
            }
        }
        msg += getResultString(game);
        if (msg.isEmpty())
            return EMPTY_RESULT;
        return msg;
    }

    private List<Move> getMoves(String next) {
        next = next.trim();
        List<Move> moves = new ArrayList<>();
        for (char c : next.toCharArray()) {
            switch (c) {
                case 'M':
                    moves.add(new Move());
                    break;
                case 'L':
                    moves.add(new Move(Direction.L));
                    break;
                case 'R':
                    moves.add(new Move(Direction.R));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown moveShipOn: " + c);
            }

        }
        return moves;
    }

    private List<Ship> getShips(String line) {
        try {
            Matcher matcher = shipPattern.matcher(line);
            List<Ship> result = new ArrayList<>();
            while (matcher.find()) {
                GridCell gridCell = getGridCell(matcher);
                String facingDirectionString = matcher.group("FacingDirection");
                FacingDirection facingDirection = FacingDirection.S.valueOf(facingDirectionString);

                Ship ship = new Ship(gridCell, facingDirection);
                result.add(ship);
            }
            return result;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Line [" + line + "] is in wrong format. ", ex);
        }
    }

    private GridCell getGridCell(Matcher matcher) {
        String x = matcher.group("X");
        String y = matcher.group("Y");
        return new GridCell(Integer.valueOf(x), Integer.valueOf(y));
    }


    private String getResultString(TheGame game) {
        if (game != null) {
            Collection<Ship> result = game.getResult();
            StringBuilder resultString = new StringBuilder();
            for (Ship ship : result) {
                resultString.append(ship.toString() + "\n");
            }
            if (resultString.length() > 0)
                return resultString.substring(0, resultString.length() - 1).toString();
        }

        return "";
    }

    private GridCell getCell(String line) {
        Matcher matcher = insideBracketsPattern.matcher(line);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Line [" + line + "] is in wrong format for grid cell");
        }
        GridCell gridCell = getGridCell(matcher);
        return gridCell;
    }
}
