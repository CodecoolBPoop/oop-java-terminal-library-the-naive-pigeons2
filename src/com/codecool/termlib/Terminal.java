package com.codecool.termlib;
import java.io.*;

public class Terminal {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";

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

    public static void printGrid(int[][] a)
    {
        System.out.println("            Welcome to the 2048 game!!!\r");
        System.out.println("      Use the wasd keys to slide the table!\r");
        System.out.format("+-----------+-----------+-----------+-----------+\r");
        System.out.format("%n|           |           |           |           |%n\r");
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if (a[i][j] == 2) {
                    System.out.printf("|" + ANSI_YELLOW +  "%6d     " + ANSI_RESET, a[i][j]);
                } else if (a[i][j] == 4) {
                    System.out.printf("|" + ANSI_PURPLE +  "%6d     " + ANSI_RESET, a[i][j]);
                } else if (a[i][j] == 8) {
                    System.out.printf("|" + ANSI_CYAN +  "%6d     " + ANSI_RESET, a[i][j]);
                } else if (a[i][j] == 16) {
                    System.out.printf("|" + ANSI_GREEN +  "%6d     " + ANSI_RESET, a[i][j]);
                }else if (a[i][j] >= 32) {
                    System.out.printf("|" + ANSI_BLUE +  "%6d     " + ANSI_RESET, a[i][j]);
                } else {
                    String point = ".";
                    System.out.printf("|%6s     ", point);
                }
            }
            System.out.printf("|\r");
            System.out.format("%n|           |           |           |           |%n\r");
            System.out.format("+-----------+-----------+-----------+-----------+\r");
            if (i != 3){
                System.out.format("%n|           |           |           |           |%n\r");
            }
        }
        System.out.println("\r");
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
            int[][] a = {
                {0, 0, 0, 0},
                {0, 32, 0, 0},
                {0, 0, 16, 128},
                {0, 0, 0, 0}
            };
            while (true) {
                try {
                    String move = t.getNextMove();
                    if (move == null) {
                        continue;
                    }
                    if (move.equals("quit")) {
                        break; // while
                    } else if (move.equals("left")) {
                        a[1][0]++;
                    } else if (move.equals("right")) {
                        a[1][3]++;
                    } else if (move.equals("up")) {
                        a[0][1]++;
                    } else if (move.equals("down")) {
                        a[3][2]++;
                    }
                    t.clearScreen();
                    printGrid(a);
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

