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
public class RobotPlayer {
    private Process robotProc;
    private BufferedReader reader;
    private PrintWriter writer;

    public RobotPlayer(String source, int size) throws IOException, RobotPlayerNotWorkingException {
        robotProc = Runtime.getRuntime().exec(source);
        reader = new BufferedReader(new InputStreamReader(robotProc.getInputStream()));
        writer = new PrintWriter(robotProc.getOutputStream(), true);
        writer.println("Ping");
        if (!reader.readLine().equals("Pong")) {
            throw new RobotPlayerNotWorkingException("Answer to Ping wasn't Pong");
        }
        writer.println(size);
    }

    public int[] makeMove(String message) throws InvalidMoveException {
        int[] move = new int[4];
        writer.println(message);
        String nextMove;
        try {
            for (int i = 0; i <= 10; i++) {     //pętla sprawdza co 100ms czy nie przyszła odpowiedź
                if (i == 10)                    //przekroczony czas na odpowiedź, wyrzuca błąd
                    throw new InvalidMoveException("Ruch wykonany przez komputer nie jest poprawny");
                Thread.sleep(100);
                if (reader.ready())             //jak linia gotowa do odczytu - przerywa pętlę
                    break;
            }
            nextMove = reader.readLine();
            String splittedValues[] = nextMove.split(" ");
            move[0] = Integer.parseInt(splittedValues[0]);
            move[1] = Integer.parseInt(splittedValues[1]);
            move[2] = Integer.parseInt(splittedValues[2]);
            move[3] = Integer.parseInt(splittedValues[3]);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new InvalidMoveException("Ruch wykonany przez komputer nie jest poprawny");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return move;
    }

    public void killRobot() {
        robotProc.destroy();
        reader = null;
        writer = null;
    }

    static boolean hasBegun = false;
}
