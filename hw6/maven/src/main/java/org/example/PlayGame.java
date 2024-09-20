package org.example;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
public class PlayGame {
    private static final int TOTAL_GAMES = 1000;
    private static final int NUMBER_OF_DOORS = 3;
    private static int winsWithSwitch;
    private static int winsWithoutSwitch;

    private GameResult playGame(boolean switchChoice) {
        Random rand = new Random();

        int carPosition = rand.nextInt(NUMBER_OF_DOORS);
        int initialChoice = rand.nextInt(NUMBER_OF_DOORS);

        int doorToOpen;
        do {
            doorToOpen = rand.nextInt(NUMBER_OF_DOORS);
        } while (doorToOpen == carPosition || doorToOpen == initialChoice);

        int finalChoice = switchChoice ? NUMBER_OF_DOORS - initialChoice - doorToOpen : initialChoice;
        boolean win = finalChoice == carPosition;

        return new GameResult(win, switchChoice);
    }
    public void startGame() {

        winsWithSwitch = 0;
        winsWithoutSwitch = 0;

        DescriptiveStatistics switchStats = new DescriptiveStatistics();
        DescriptiveStatistics noSwitchStats = new DescriptiveStatistics();


        Map<Integer, String> results = new HashMap<>();


        for (int i = 0; i < TOTAL_GAMES; i++) {


            GameResult resultSwitch = playGame(true);
            GameResult resultNoSwitch = playGame(false);


            if (resultSwitch.isWin()) {
                winsWithSwitch++;
                results.put(i, "Результат при смене двери = " + resultSwitch.isWin());
                switchStats.addValue(1);
            } else {
                switchStats.addValue(0);
            }

            if (resultNoSwitch.isWin()) {
                winsWithoutSwitch++;
                results.put(i, "Результат без смены двери = " + resultNoSwitch.isWin());
                noSwitchStats.addValue(1);
            } else {
                noSwitchStats.addValue(0);
            }
        }
        System.out.println("Общее количество попыток: " + TOTAL_GAMES);
        System.out.println("Количество выигрышей, если меняем первоначальный выбор двери: " + winsWithSwitch);
        System.out.println("Количество выигрышей, если оставляем выбранную сначала дверь: " + winsWithoutSwitch);

        System.out.println("Результаты игры: ");
        for (Map.Entry<Integer, String> entry : results.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
