package logic;

import exceptions.InvalidMoveException;
import exceptions.RobotPlayerNotWorkingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringJoiner;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mateusz on 08.12.2016.
 * Project Bricks
 */
public class RobotPlayer {
    private Process robotProc;
    private BufferedReader reader;
    private PrintWriter writer;

    public RobotPlayer(String source, int size) throws IOException, RobotPlayerNotWorkingException {
        this.source = source;
        this.size = size;
        robotProc = Runtime.getRuntime().exec(source);
        reader = new BufferedReader(new InputStreamReader(robotProc.getInputStream()));
        writer = new PrintWriter(robotProc.getOutputStream(), true);
        writer.println("PONG");
        if (!reader.readLine().equals("PONG")) {
            throw new RobotPlayerNotWorkingException("Answer to Ping wasn't Pong");
        }
        writer.println(size);
    }

    public void reset() throws IOException, RobotPlayerNotWorkingException {
        robotProc.destroy();
        robotProc = Runtime.getRuntime().exec(source);
        reader = new BufferedReader(new InputStreamReader(robotProc.getInputStream()));
        writer = new PrintWriter(robotProc.getOutputStream(), true);
        writer.println("PING");
        if (!reader.readLine().equals("PONG")) {
            throw new RobotPlayerNotWorkingException("Answer to Ping wasn't Pong");
        }
        writer.println(size);
    }

    public void reset(int size) throws IOException, RobotPlayerNotWorkingException {
        robotProc.destroy();
        robotProc = Runtime.getRuntime().exec(source);
        reader = new BufferedReader(new InputStreamReader(robotProc.getInputStream()));
        writer = new PrintWriter(robotProc.getOutputStream(), true);
        writer.println("PING");
        if (!reader.readLine().equals("PONG")) {
            throw new RobotPlayerNotWorkingException("Answer to Ping wasn't Pong");
        }
        this.size = size;
        writer.println(this.size);
    }

    public int[] makeMove(String message) throws InvalidMoveException, TimeoutException {
        int[] move = new int[4];
        writer.println(message);
        try {
            for (int i = 0; i <= 100; i++) {     //pętla sprawdza co 10ms czy nie przyszła odpowiedź
                if (i == 100)                    //przekroczony czas na odpowiedź, wyrzuca błąd
                    throw new TimeoutException("Komputer przekroczył czas na wykonanie ruchu");
                Thread.sleep(10);
                if (reader.ready()) {//jak linia gotowa do odczytu - przerywa pętlę
                    break;
                }
            }

            boolean hasNewLine = false;
            char[] buffor = new char[256];
            reader.read(buffor);
            String nextMove="";
            for(int i = 0; i < 255; i++) {
                System.out.print(buffor[i]);
                nextMove+=buffor[i];
            }
            if(nextMove.contains(System.lineSeparator())) {
                String splittedValues[] = nextMove.split("\\s+");
                move[0] = Integer.parseInt(splittedValues[0]) - 1;
                move[1] = Integer.parseInt(splittedValues[1]) - 1;
                move[2] = Integer.parseInt(splittedValues[2]) - 1;
                move[3] = Integer.parseInt(splittedValues[3]) - 1;
            }
            else {
                System.out.println("Wyjątek 1");
                throw new InvalidMoveException("Linia nie kończy się znakiem nowej linii");
            }
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

    private String source;

    private int size;
}