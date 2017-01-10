package logic;

import exceptions.InvalidMoveException;
import exceptions.RobotPlayerNotWorkingException;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mateusz on 08.12.2016.
 * Project Bricks
 */
public class RobotPlayer {
    private Process robotProc;
    private BufferedReader reader;
    private PrintWriter writer;

    public RobotPlayer(String[] tabSource, int size) throws IOException, RobotPlayerNotWorkingException {
        this.tabSource = tabSource;
        this.size = size;
        robotProc = Runtime.getRuntime().exec(this.tabSource);
        reader = new BufferedReader(new InputStreamReader(robotProc.getInputStream()));
        writer = new PrintWriter(robotProc.getOutputStream(), true);
        writer.println("PONG");
        if (!reader.readLine().equals("PONG")) {
            throw new RobotPlayerNotWorkingException("Answer to Ping wasn't Pong");
        }
        writer.println(size);
    }

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
        if(source.equals(""))
            robotProc = Runtime.getRuntime().exec(tabSource);
        else
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
        if(source.equals("")) {
            robotProc = Runtime.getRuntime().exec(tabSource);
        }
        else
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

    public void sendEndingMessages(boolean win) {
        if (win) {
            writer.println("WYGRALES");
        } else {
            writer.println("PRZEGRALES");
        }
    }

    public int[] makeMove(String message) throws InvalidMoveException, TimeoutException {
        int[] move = new int[4];
        writer.println(message);
        try {
            for (int i = 0; i <= 100; i++) {//pętla sprawdza co 10ms czy nie przyszła odpowiedź
                if (i == 100) {                   //przekroczony czas na odpowiedź, wyrzuca błąd
                    throw new TimeoutException("Komputer przekroczył czas na wykonanie ruchu");
                }
                Thread.sleep(10);
                if (reader.ready()) {//jak linia gotowa do odczytu - przerywa pętlę
                    break;
                }
            }

            char[] buffor = new char[256];
            //noinspection ResultOfMethodCallIgnored
            reader.read(buffor);
            String nextMove = "";
            for (int i = 0; i < 255; i++) {
                nextMove += buffor[i];
            }
            if (nextMove.contains(System.lineSeparator())) {
                String splittedValues[] = nextMove.split("\\s+");
                move[0] = Integer.parseInt(splittedValues[0]) - 1;
                move[1] = Integer.parseInt(splittedValues[1]) - 1;
                move[2] = Integer.parseInt(splittedValues[2]) - 1;
                move[3] = Integer.parseInt(splittedValues[3]) - 1;
            } else {
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

    public static void exportLogs(int win1, int win2, String nameFirst, String nameSecond) {
        if (nameFirst.equals(nameSecond))
            return;
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks";
            if (!new File(path + "/logs.txt").exists()) {
                //noinspection ResultOfMethodCallIgnored
                new File(path + "/logs.txt").createNewFile();
                String filename = path + "/logs.txt";
                PrintWriter writer = new PrintWriter(filename);
                writer.println(nameFirst + "," + nameSecond + "=" + win1 + "," + win2);
                writer.println("###Sumarycznie###");
                writer.println(nameFirst + "=" + win1 + "," + win2);
                writer.println(nameSecond + "=" + win2 + "," + win1);
                writer.close();
            } else {
                String pathToFile = System.getProperty("user.home") + "/Documents/Bricks/logs.txt";
                Scanner in = new Scanner(new File(pathToFile));
                ArrayList<String> wyniki = new ArrayList<>();
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.charAt(0) != '#') {
                        wyniki.add(line);
                    } else {
                        break;
                    }
                }
                in.close();
                wyniki.add(nameFirst + "," + nameSecond + "=" + win1 + "," + win2);


                Map<String, Integer> winsMap = new HashMap<>();
                Map<String, Integer> losesMap = new HashMap<>();

                for (String s : wyniki) {
                    try {
                        String[] firstDivision = s.split("=");
                        String[] secondDivisionNames = firstDivision[0].split(",");
                        String[] secondDivisionValues = firstDivision[1].split(",");
                        if (secondDivisionNames.length != 2 || secondDivisionValues.length != 2)
                            continue;
                        winsMap.putIfAbsent(secondDivisionNames[0], 0);
                        winsMap.putIfAbsent(secondDivisionNames[1], 0);
                        winsMap.put(secondDivisionNames[0], winsMap.get(secondDivisionNames[0]) + Integer.parseInt(secondDivisionValues[0]));
                        winsMap.put(secondDivisionNames[1], winsMap.get(secondDivisionNames[1]) + Integer.parseInt(secondDivisionValues[1]));

                        losesMap.putIfAbsent(secondDivisionNames[0], 0);
                        losesMap.putIfAbsent(secondDivisionNames[1], 0);
                        losesMap.put(secondDivisionNames[0], losesMap.get(secondDivisionNames[0]) + Integer.parseInt(secondDivisionValues[1]));
                        losesMap.put(secondDivisionNames[1], losesMap.get(secondDivisionNames[1]) + Integer.parseInt(secondDivisionValues[0]));


                    } catch (IndexOutOfBoundsException | NumberFormatException ignored) {
                    }
                }
                PrintWriter writer = new PrintWriter(pathToFile);
                wyniki.forEach(writer::println);
                writer.println("###Sumarycznie###");
                for (String key : winsMap.keySet()) {
                    writer.println("Komputer: " + key + " Wygranych: " + winsMap.get(key) + " Przegranych: " + losesMap.get(key));
                }
                writer.close();

            }

        } catch (IOException ignored) {
        }

    }

    private String source = "";

    private String tabSource[];

    private int size;
}