package supermarket;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SupermarketQueue {

    private final SupermarketCheckOut[] supermarketCheckOuts;
    private final boolean avalibleCheckOut[];
    private final DateTimeFormatter dateTimeFormatter
            = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Semaphore semaphore;
    private final Lock locker = new ReentrantLock();

    public SupermarketQueue(int numberOfCheckOut) {
        supermarketCheckOuts = new SupermarketCheckOut[numberOfCheckOut];
        avalibleCheckOut = new boolean[numberOfCheckOut];
        semaphore = new Semaphore(numberOfCheckOut, true);
        updateSupermarketCheckOuts();
        updateAvalibreCheckOut();
    }

    private void updateSupermarketCheckOuts() {
        for (int i = 0; i < supermarketCheckOuts.length; i++) {
            supermarketCheckOuts[i] = new SupermarketCheckOut();
        }
    }

    private void updateAvalibreCheckOut() {
        Arrays.fill(avalibleCheckOut, true);
    }

    public void goToQueue() throws InterruptedException {
        int selectCheckOut;
        System.out.printf("%s -> %s se ha puesto en la cola única\n",
                LocalTime.now().format(dateTimeFormatter),
                Thread.currentThread().getName());
        try {
            semaphore.acquire();
            selectCheckOut = selectCheckOut();
            if (selectCheckOut != -1) {
                supermarketCheckOuts[selectCheckOut].checkOut();
                releaseCheckOut(selectCheckOut);
            } else {
                System.out.printf("%s -> %s no tiene disponible ninguna caja, algo raro.\n",
                        LocalTime.now().format(dateTimeFormatter),
                        Thread.currentThread().getName());
            }
        } finally {
            semaphore.release();
        }
    }

    private int selectCheckOut() throws InterruptedException {
        try {
            locker.lockInterruptibly();
            for (int i = 0; i < avalibleCheckOut.length; i++) {
                if (avalibleCheckOut[i]) {
                    avalibleCheckOut[i] = false;
                    return i;
                }
            }
            return -1;
        } finally {
            locker.unlock();
        }
    }

    private void releaseCheckOut(int selectCheckOut) {
        avalibleCheckOut[selectCheckOut] = true;
    }
}
