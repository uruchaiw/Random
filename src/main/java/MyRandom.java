import java.util.Random;

public class MyRandom {
    double min = 00.0000;
    double max = 99.9999;
    int low = 0;
    int high = 10000;

    public static void main (String[] arg) {
        MyRandom random = new MyRandom();
        random.init();
    }

    private void init() {
        Random r = new Random();

        double chance;
        int countLow = 0;
        int countHigh = 0;
        int countJACKPOTS = 0;

        System.out.println(fib(3));

        for (int i = 0; i < 1000000000; i++){
            int randomValue = r.nextInt(high-low) + low;

            if(randomValue < 4750) {
                countHigh = 0;
                countLow++;
                if (countLow > 28){
                    chance = (1 - (Math.pow(0.525, countLow))) * 100;
                    //System.out.printf("%3s %5d %10s %1d %4.10f%1s %4s\n", "Number = ", randomValue," SP Dise = ", countLow, chance, "%", "BettLow");
                }
            } if (randomValue > 5250) {
                countLow = 0;
                countHigh++;
                if (countHigh > 28) {
                    chance = (1 - (Math.pow(0.525, countHigh))) * 100;
                    //System.out.printf("%3s %5d %10s %1d %4.10f%1s %4s\n", "Number = ", randomValue," SP Dise = ", countHigh, chance, "%",  "BettHigh" );
                }
            } if (randomValue <= 5250 && randomValue >= 4700) {
                countHigh = 0;
                countLow = 0;
            }
            countJACKPOTS = getCountJACKPOTS(countJACKPOTS, randomValue);
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

    int fib(int i) {
        if (i == 1) return 1;
        if (i == 2) return 1;
        return fib(i - 1) + fib(i - 2);
    }
}
