package logic;

import exceptions.InvalidMoveException;
import exceptions.RobotPlayerNotWorkingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by Mateusz on 08.12.2016.
 * Project Bricks
 */
public class Robot {
    private Process robotProc;
    private BufferedReader reader;
    private PrintWriter writer;

    public Robot(String source) throws IOException, RobotPlayerNotWorkingException{
        robotProc = Runtime.getRuntime().exec(source);
        reader = new BufferedReader(new InputStreamReader(robotProc.getInputStream()));
        writer = new PrintWriter(robotProc.getOutputStream(),true);
        writer.println("Ping");
        if (!reader.readLine().equals("Pong")) {
            throw new RobotPlayerNotWorkingException("Answer to Ping wasn't Pong");
        }
    }
    public int[] makeMove(int previousx1,int previousy1,int previousx2,int previousy2) throws InvalidMoveException{
        int[] move = new int[4];
        writer.println(move[0]+ " " + move[1]+ " " + move[2]+ " " + move[3]);
        String nextMove;
        try {
            nextMove = reader.readLine();
            String splittedValues[] = nextMove.split(" ");
            move[0] = Integer.parseInt(splittedValues[0]);
            move[1] = Integer.parseInt(splittedValues[1]);
            move[2] = Integer.parseInt(splittedValues[2]);
            move[3] = Integer.parseInt(splittedValues[3]);
        }
        catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new InvalidMoveException("Ruch wykonany przez komputer nie jest poprawny");
        }

        return move;
    }
    public int[] makeMove() throws InvalidMoveException{
        int[] move = new int[4];
        writer.println("Zaczynaj");
        String nextMove;
        try {
            nextMove = reader.readLine();
            String splittedValues[] = nextMove.split(" ");
            move[0] = Integer.parseInt(splittedValues[0]);
            move[1] = Integer.parseInt(splittedValues[1]);
            move[2] = Integer.parseInt(splittedValues[2]);
            move[3] = Integer.parseInt(splittedValues[3]);
        }
        catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new InvalidMoveException("Ruch wykonany przez komputer nie jest poprawny");
        }

        return move;
    }
    public void killRobot(){
        robotProc.destroy();
        reader = null;
        writer = null;
    }

    static boolean hasBegun = false;
}
