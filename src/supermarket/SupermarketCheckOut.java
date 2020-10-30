package supermarket;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SupermarketCheckOut {

    private final int checkOutNumber;
    private static int currentCheckOutNumber = 0;
    private final DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern("HH:mm:ss");

    public SupermarketCheckOut() {
        checkOutNumber = ++currentCheckOutNumber;
    }

    public void checkOut() throws InterruptedException {
        System.out.printf("%s -> %s está pasando por la caja %d\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName(),
                checkOutNumber);
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4) + 1);
        System.out.printf("%s -> %s ha pasado por la caja %d todos sus artículos y se va del supermercado\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName(),
                checkOutNumber);
    }
}
