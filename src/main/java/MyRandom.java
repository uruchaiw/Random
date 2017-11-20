import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyRandom {
    private double min = 85;
    private double max = 95;

    private int countLow = 0;
    private int countHigh = 0;

    private double bet;
    private int rollWin;
    private int countJACKPOTS = 0;

    private int attempt = 0;
    private double betThenLoss;
    double balance;

    private boolean flag = false;
    private int type;

    private List<Number> list = new ArrayList();

    public static void main (String[] arg) {
        MyRandom random = new MyRandom();
        random.init();
    }

    private void init() {
        list.clear();
        for (int i = 0; i < 100; i++) {

            attempt = 0;
            while (attempt >= 0) {
                attempt++;
                //rollOnMargingeil();
                combinedTactik();
            }
        }

        int pro = 0;
        for (Number aList : list) {
            pro += (int) aList;
        }
        pro /= list.size();
        System.out.println("Среднее число: " + pro);
        //combinedTactik();
        //day();
    }

    private void day() {
        int day = 0;
        double balance = 10838;

        while (balance < 100000){
            day++;
            balance += (balance * 25) /100;
            System.out.println(balance +"\t" + day);
        }
    }

    private void combinedTactik() {
        balance = 1.0;
        int tick = 0;
        bet = (balance / 1000000 ) * 2;
        double startBet = bet;
        double percent;
        int curentRoll;


        betThenLoss = 0;

        while(balance >= bet){
            tick++;
            double currentBet = 0;

            //getRandom percent for Bet
            percent = (Math.random() * (max - min) + min);

            //Up bet If lose
            if (!flag) {
                upBetIfLose(balance, startBet, percent, currentBet);
            }

            //Set Random at "Low" or "High" on bet
            curentRoll = dise();

            if (flag){
                balance = rollX2(balance, curentRoll);
            } else {
                balance = randomRoll(balance, percent, curentRoll);
            }

            //cheakRoll(curentRoll, startBet);

            //Exit When balance more
            if(balance >= 4) {
                list.add(attempt);
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

    private void upBetIfLose(double balance, double startBet, double percent, double currentBet) {
        if (betThenLoss < 0) {
            bet = getBetThenLoss(startBet, percent, currentBet, balance, betThenLoss);
        } else {
            bet = startBet;
            betThenLoss = 0;
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
            bet /= 4;
            if (bet <= startBet) {
                bet = startBet;
            }
        }
        return bet;
    }

    //Random Swith on 85-89% to win
    private double randomRoll(double balance, double percent, int curentRoll) {

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
        return balance;
    }

    private double rollX2(double balance, int curentRoll) {

        if (type == 4950) {
            if (curentRoll > 5051) {
                balance += bet;
                betThenLoss += bet;
            } else {
                balance -=  bet;
                betThenLoss -= bet;
            }
        } else {
            if (curentRoll < 4949) {
                balance += bet;
                betThenLoss += bet;
            } else {
                balance -=  bet;
                betThenLoss -= bet;
            }
        }

        return balance;
    }

    private void cheakRoll(int randomValue, double startBet) {
        double chance;
        flag = false;
        type = 0;

        if(randomValue < 4950) {
            countHigh = 0;
            countLow++;
            if (countLow > 25) {
                flag = true;
                type = 4950;
                setBetOnChanceX2(startBet);
            }
        } if (randomValue > 5050) {
            countLow = 0;
            countHigh++;
            if (countHigh > 25) {
                flag = true;
                type = 5050;
                setBetOnChanceX2(startBet);
                //chance = (1 - (Math.pow(0.525, countHigh))) * 100;
                //System.out.printf("%3s %5d %10s %1d %4.10f%1s %4s\n", "Number = ", randomValue," SP Dise = ", countHigh, chance, "%",  "BettHigh" );
            }
        }
    }

    private void setBetOnChanceX2(double startBet) {
        if(betThenLoss >= 0) {
            bet = bet * 30;
            while (bet > balance) {
                bet /= 2;
                if (bet <= startBet) {
                    bet = startBet;
                }
            }
        } else {
            bet = bet * 2;
            while (bet > balance) {
                bet /= 1.1;
                if (bet <= startBet) {
                    if (balance < startBet) {
                        return;
                    }
                    bet = startBet;
                }
            }
        }
    }


    //Old Function
    private void rollOnMargingeil() {
        balance = 750;
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

    int fib(int i) {
        if (i == 1) return 1;
        if (i == 2) return 1;
        return fib(i - 1) + fib(i - 2);
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