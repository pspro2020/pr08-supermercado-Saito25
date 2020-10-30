import supermarket.Customer;
import supermarket.SupermarketQueue;

public class Main {

    Thread[] customers;
    SupermarketQueue supermarketQueue = new SupermarketQueue(4);

    public Main() throws InterruptedException {
        customers = new Thread[50];
        setupCustomers();
        starShop();
        waitForCustomers();
        closeSupermarket();
    }

    private void setupCustomers() {
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Thread(new Customer(supermarketQueue));
        }
    }

    private void starShop() {
        for (int i = 0; i < customers.length; i++) {
            customers[i].start();
        }
    }

    private void waitForCustomers() throws InterruptedException {
        for (int i = 0; i < customers.length; i++) {
            customers[i].join();
        }
    }

    private void closeSupermarket() {
        System.out.println("¡El supermercado cierra sus puertas por hoy! Los esperamos mañana.");
    }

    public static void main(String[] args) throws InterruptedException {
        new Main();
    }
}
