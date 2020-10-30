package supermarket;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Customer implements Runnable {

    private final SupermarketQueue supermarketQueue;
    private final DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Customer(SupermarketQueue supermarketQueue) {
        this.supermarketQueue = supermarketQueue;
    }

    @Override
    public void run() {
        System.out.printf("%s -> %s acaba de llegar al supermercado\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3) + 1);
        } catch (InterruptedException e) {
            System.out.printf("%s -> %s Ha sido interrumpido mientras compraba\n",
                    LocalTime.now().format(dateTimeFormatter),
                    Thread.currentThread().getName());
        }

        try {
            supermarketQueue.goToQueue();
        } catch (InterruptedException e) {
            System.out.printf("%s -> %s ha sido interrumpido mientras estaba en la caja\n",
                    LocalTime.now().format(dateTimeFormatter),
                    Thread.currentThread().getName());
        }
    }
}
