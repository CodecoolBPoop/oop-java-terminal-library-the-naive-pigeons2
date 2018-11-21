package com.codecool.termlib;
import java.io.*;

public class Terminal {
    /**
     * The beginning of control sequences.
     */
    // HINT: In \033 the '0' means it's an octal number. And 33 in octal equals 0x1B in hexadecimal.
    // Now you have some info to decode that page where the control codes are explained ;)
    private static final String CONTROL_CODE = "\033[";
    /**
     * Command for whole screen clearing.
     *
     * Might be partitioned if needed.
     */
    private static final String CLEAR = "2J";
    /**
     * Command for moving the cursor.
     */
    private static final String MOVE = "H";
    /**
     * Command for printing style settings.
     *
     * Handles foreground color, background color, and any other
     * styles, for example color brightness, or underlines.
     */
    private static final String STYLE = "m";

    /**
     * Reset printing rules in effect to terminal defaults.
     *
     * Reset the color, background color, and any other style
     * (i.e.: underlined, dim, bright) to the terminal defaults.
     */
    public void resetStyle() {
    }

    /**
     * Clear the whole screen.
     *
     * Might reset cursor position.
     */
    public void clearScreen() {
        String commandString = CONTROL_CODE + CLEAR + CONTROL_CODE + MOVE;
        command(commandString);
    }

    /**
     * Move cursor to the given position.
     *
     * Positions are counted from one.  Cursor position 1,1 is at
     * the top left corner of the screen.
     *
     * @param x Column number.
     * @param y Row number.
     */
    public void moveTo(Integer x, Integer y) {
    }

    /**
     * Set the foreground printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
    }

    /**
     * Set the background printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The background color to set.
     */
    public void setBgColor(Color color) {
    }

    /**
     * Make printed text underlined.
     *
     * On some terminals this might produce slanted text instead of
     * underlined.  Cannot be turned off without turning off colors as
     * well.
     */
    public void setUnderline() {
    }

    /**
     * Move the cursor relatively.
     *
     * Move the cursor amount from its current position in the given
     * direction.
     *
     * @param direction Step the cursor in this direction.
     * @param amount Step the cursor this many times.
     */
    public void moveCursor(Direction direction, Integer amount) {
    }

    /**
     * Set the character diplayed under the current cursor position.
     *
     * The actual cursor position after calling this method is the
     * same as beforehand.  This method is useful for drawing shapes
     * (for example frame borders) with cursor movement.
     *
     * @param c the literal character to set for the current cursor
     * position.
     */
    public void setChar(char c) {
    }

    /**
     * Helper function for sending commands to the terminal.
     *
     * The common parts of different commands shall be assembled here.
     * The actual printing shall be handled from this command.
     *
     * @param commandString The unique part of a command sequence.
     */
    private void command(String commandString) {
        System.out.println(commandString);
    }
    public void setTerminalRawNoEcho() throws IOException, InterruptedException {
        String[] cmd = {"sh", "-c", "stty raw -echo </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();
    }
    public void setTerminalCookedEcho() throws IOException, InterruptedException {
        String[] cmd = {"sh", "-c", "stty cooked echo </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();
    }
    public String getNextMove() throws IOException {
        char nextMove = (char) System.in.read();
        switch (nextMove) {
        case 'a':
            return "left";
        case 's':
            return "down";
        case 'd':
            return "right";
        case 'w':
            return "up";
        case 'q':
            return "quit";
        default:
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            Terminal t = new Terminal();
            t.setTerminalRawNoEcho();
            t.clearScreen();
            while (true) {
                try {
                    String move = t.getNextMove();
                    System.out.println(move);
                    if (move.equals("quit")) {
                        break; // while
                    }
                } catch(IOException e) {
                    System.err.println("IOException");
                }
            }
            t.setTerminalCookedEcho();
        } catch (IOException e) {
            System.err.println("IOException");
        }
        catch (InterruptedException e) {
            System.err.println("InterruptedException");
        }
    }
}