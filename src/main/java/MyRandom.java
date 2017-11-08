import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyRandom {
    double min = 85;
    double max = 95;

    private int countLow = 0;
    private int countHigh = 0;

    private double bet;
    private int rollWin;
    private int countJACKPOTS = 0;

    private int attempt = 0;

    List<Number> list = new ArrayList<>();

    public static void main (String[] arg) {
        MyRandom random = new MyRandom();
        random.init();
    }

    private void init() {
        while (attempt >= 0) {
            attempt++;
            //rollOnMargingeil();
            combinedTactik();
        }
        //combinedTactik();

        //day();
    }

    private void day() {
        int day = 0;
        double balance = 115;

        while (balance < 1000000){
            day++;
            balance += (balance * 4) /100;
            System.out.println(balance +"\t" + day);
        }

    }

    private void combinedTactik() {
        double balance = 750;
        int tick = 0;
        bet = 100;
        double startBet = bet;
        double percent;
        double betThenLoss = 0;
        int curentRoll;

        while(balance >= bet){
            tick++;
            double currentBet = 0;

            //getRandom percent for Bet
            percent = (Math.random() * (max - min) + min);

            //Up bet If lose
            if (betThenLoss < 0) {
                bet = getBetThenLoss(startBet, percent, currentBet, balance, betThenLoss);
            } else {
                bet = startBet;
                betThenLoss = 0;
            }

            //Set Random at "Low" or "High" on bet
            curentRoll = dise();
            if (Math.random() < 0.5) {
                if(curentRoll < (10000 * (percent / 100))) {
                    balance  += (bet / (percent / 100) - bet);
                    betThenLoss += (bet / (percent / 100) - bet);
                } else {
                    balance -= bet;
                    betThenLoss -= bet;
                }
            } else {
                if(curentRoll > (10000 - (10000 * (percent / 100)))) {
                    balance  += (bet / (percent / 100) - bet);
                    betThenLoss += (bet / (percent / 100) - bet);
                } else {
                    balance -= bet;
                    betThenLoss -= bet;
                }
            }

            //System.out.println(tick + "\t" + balance);

            //Exit When balance more
            if(balance >= 40000) {
                System.out.println(attempt);
                attempt = -1;
                return;
            }
        }
    }

    private double getBetThenLoss(double startBet, double percent, double currentBet, double balance, double betThenLoss) {
        betThenLoss = Math.abs(betThenLoss);
        for (int i = 1; currentBet < betThenLoss; i++) {
            bet = startBet;
            bet *= i;
            currentBet = (bet / (percent / 100) - bet);
        }
        while (bet > balance){
            bet /= 2;
            if (bet <= startBet) {
                bet = startBet;
            }
        }
        return bet;
    }

    private void rollOnMargingeil() {
        double balance = 750;
        int tick = 0;
        bet = 1;

        while (balance >= bet) {
            tick++;

            if (dise() <= 4749) {
                balance += bet;
                bet = 1;
            } else {
                balance -= bet;
                bet *= 2;
            }
            //System.out.println(tick + "\t" + balance);

            if(balance >= 1000) {
                System.out.println(attempt);
                attempt = -1;
                return;
            }
        }
    }

    private int dise() {
        int randomValue;
        int low = 0;
        int high = 10000;

        Random r = new Random();
        randomValue = r.nextInt(high - low) + low;

        return randomValue;
    }

    int fib(int i) {
        if (i == 1) return 1;
        if (i == 2) return 1;
        return fib(i - 1) + fib(i - 2);
    }

    private void cheakRoll(int randomValue, double balance) {
        double chance;
        double bet = 0;

        if(randomValue < 4950) {
            countHigh = 0;
            countLow++;
            if (countLow > 8) {
                list.add(50.51);
                bet = balance / 8;
                list.add(bet);
            }
        } if (randomValue > 5050) {
            countLow = 0;
            countHigh++;
            if (countHigh > 8) {
                chance = (1 - (Math.pow(0.525, countHigh))) * 100;
                //System.out.printf("%3s %5d %10s %1d %4.10f%1s %4s\n", "Number = ", randomValue," SP Dise = ", countHigh, chance, "%",  "BettHigh" );
            }
        }
        //countJACKPOTS = getCountJACKPOTS(countJACKPOTS, randomValue);
    }

    private int getCountJACKPOTS(int countJACKPOTS, int randomValue) {
        double chance;
        if (randomValue != 8888){
            countJACKPOTS++;
            chance = (1 - (Math.pow(0.9999, countJACKPOTS))) * 100;
            if(chance > 99.99){
                //System.out.println("Chance on win JACKPOT More 99.9% " + chance);
            }
        } else {
            countJACKPOTS = 0;
            //System.out.println("JACKPOT  " + randomValue);
        }
        return countJACKPOTS;
    }
}