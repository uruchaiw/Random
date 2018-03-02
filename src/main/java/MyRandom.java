import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyRandom {
    private double min = 85;
    private double max = 95;

    private int countLow = 0;
    private int countHigh = 0;

    private int CountLowX19 = 0;
    private int CountHighX19 = 0;

    private int CountLowX5 = 0;
    private int CountHighX5 = 0;

    private double bet;
    private int rollWin;
    private int countJACKPOTS = 0;

    private int attempt = 0;
    private double betThenLoss;
    double balance;

    private boolean flagX2 = false;
    private boolean flagX19 = false;
    private int typeX2;
    private  int typeX19;

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
                combinedTactik();
            }
        }
        int pro = 0;
        for (Number aList : list) {
            pro += (int) aList;
        }
        pro /= list.size();
        System.out.println("Среднее число попыток: " + pro);
        //combinedTactik();
        //day();



        /*for (int i = 0; i < 1000000; i++) {
            rollx5();
        }*/
    }

    /**Temp*/
    private void rollx5() {
        int currentRoll = dise();
        //checkRollx19(currentRoll);
        checkRollX5(currentRoll);
    }
    /**Close Temp*/

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
        bet = (balance / 1000000 ) * 1;
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
            if (!flagX2 || !flagX19) {
                upBetIfLose(balance, startBet, percent, currentBet);
            }
            /*if (!flagX19) {

            }*/

            //Set Random at "Low" or "High" on bet
            curentRoll = dise();

            if (flagX2){
                balance = rollX2(balance, curentRoll);
            }
            else if (flagX19) {
                balance = rollX19(balance, curentRoll);
            }else {
                balance = randomRoll(balance, percent, curentRoll);
            }

            checkRoll(curentRoll, startBet);
            checkRollx19(curentRoll, startBet);
            //checkRollX5(curentRoll);

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
    private double randomRoll(double balance, double percent, int currentRoll) {

        if (Math.random() < 0.5) {
            if(currentRoll < (10000 * (percent / 100))) {
                balance  += (bet / (percent / 100) - bet);
                betThenLoss += (bet / (percent / 100) - bet);
            } else {
                balance -= bet;
                betThenLoss -= bet;
            }
        } else {
            if(currentRoll > (10000 - (10000 * (percent / 100)))) {
                balance  += (bet / (percent / 100) - bet);
                betThenLoss += (bet / (percent / 100) - bet);
            } else {
                balance -= bet;
                betThenLoss -= bet;
            }
        }
        return balance;
    }

    private double rollX2(double balance, int currentRoll) {

        if (typeX2 == 4950) {
            if (currentRoll > 5051) {
                balance += bet;
                betThenLoss += bet;
            } else {
                balance -=  bet;
                betThenLoss -= bet;
            }
        } else {
            if (currentRoll < 4949) {
                balance += bet;
                betThenLoss += bet;
            } else {
                balance -=  bet;
                betThenLoss -= bet;
            }
        }
        return balance;
    }

    private double rollX19(double balance, int currentRoll) {

        if(typeX19 == 9500) {
            if (currentRoll > 9501) {
                balance += bet * 19;
                betThenLoss += bet;
            } else {
                balance -=  bet;
                betThenLoss -= bet;
            }
        } else {
            if (currentRoll < 501) {
                balance += bet;
                betThenLoss += bet * 19;
            } else {
                balance -=  bet;
                betThenLoss -= bet;
            }
        }
        return balance;
    }

    //Check Roll on chance 49% to win
    private void checkRoll(int randomValue, double startBet) {

        flagX2 = false;
        typeX2 = 0;

        if(randomValue < 4950) {
            countHigh = 0;
            countLow++;
            if (countLow > 14) {
                flagX2 = true;
                typeX2 = 4950;
                setBetOnChanceX2(startBet);
            }
        }
        else if (randomValue > 5050) {
            countLow = 0;
            countHigh++;
            if (countHigh > 14) {
                flagX2 = true;
                typeX2 = 5050;
                setBetOnChanceX2(startBet);
            }
        }
    }

    private void setBetOnChanceX2(double startBet) {
        if(betThenLoss >= 0) {
            bet = bet * 1.5;
            while (bet > balance) {
                bet /= 2;
                if (bet <= startBet) {
                    bet = startBet;
                }
            }
        } else {
            bet = bet * 5;
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

    //Check Roll on chance 5% to win
    private void checkRollx19(int randomValue, double startBet) {

        flagX19 = false;
        typeX19 = 0;

        if (randomValue > 9500) {
            CountHighX19 = 0;
        } if (randomValue < 500) {
            CountLowX19 = 0;
        } else {
            CountLowX19++;
            CountHighX19++;

            if(CountLowX19 > 270) {
                flagX19 = true;
                typeX19 = 500;
                setBetOnChanceX19(startBet);
            }
            if (CountHighX19 > 270) {
                flagX19 = true;
                typeX19 = 9500;
                setBetOnChanceX19(startBet);
            }
        }
    }

    private void setBetOnChanceX19(double startBet) {
        if(betThenLoss >= 0) {
            bet = bet * 9;
            while (bet > balance) {
                bet /= 2;
                if (bet <= startBet) {
                    bet = startBet;
                }
            }
        } else {
            bet = bet * 5;
            while (bet > balance) {
                bet /= 1.2;
                if (bet <= startBet) {
                    if (balance < startBet) {
                        return;
                    }
                    bet = startBet;
                }
            }
        }
    }

    //Check Roll on chance 95% to win
    private void checkRollX5(int randomValue) {
        double chance;

        if(randomValue > 9500) {
            CountLowX5++;
            if (CountLowX5 > 3) {
                chance = (1 - (Math.pow(0.05, CountLowX5))) * 100;
                System.out.printf("%3s %5d %10s %1d %4.10f%1s %4s\n", "Number = ", randomValue," SP Dise = ", CountLowX5, chance, "%",  "BettLow");
            }

        } else if (randomValue < 500) {
            CountHighX5++;
            if (CountHighX5 > 3) {
                chance = (1 - (Math.pow(0.05, CountHighX5))) * 100;
                System.out.printf("%3s %5d %10s %1d %4.10f%1s %4s\n", "Number = ", randomValue," SP Dise = ", CountHighX5, chance, "%",  "BettLow");
            }
        }
        else {
            CountHighX5 = 0;
            CountLowX5 = 0;
        }
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