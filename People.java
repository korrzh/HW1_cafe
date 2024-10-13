package HW1;

public class People implements Runnable {

    @Override
    public void run() {
        try {
            System.err.printf("Гості з потоку %s прийшли в кафе і шукають столик \n", Thread.currentThread().getName());
            Thread.sleep(20);
            Main.tables.acquire();

            System.out.printf("Гості з потоку %s сіли за столик і позвали офіціанта \n", Thread.currentThread().getName());
            Thread.sleep(20);
            Main.waiter.acquire();
            
            System.out.printf("Офіціант підійшов до гостей з потоку %s \n", Thread.currentThread().getName());

            Thread.sleep(2000);
            if (Main.isOpen()) {
                System.out.printf("Офіціант прийняв замовлення від гостей з потоку %s \n", Thread.currentThread().getName());
                Thread.sleep(20);
                Main.waiter.release();

                Thread.sleep(4000);

                Main.waiter.acquire();
                System.out.printf("Офіціант приніс замовлення гостям з потоку %s \n", Thread.currentThread().getName());
                Thread.sleep(20);
                Main.waiter.release();

                Thread.sleep(4000);

                System.err.printf("Гості з потоку %s поїли і пішли \n", Thread.currentThread().getName());
            } else {
                System.out.printf("Офіціант повідомив, що кафе закрите гостей з потоку %s \n", Thread.currentThread().getName());
                Thread.sleep(20);
                Main.waiter.release();

                Thread.sleep(4000);

                System.err.printf("Гості з потоку %s пішли \n", Thread.currentThread().getName());
            }

            Thread.sleep(20);
            Main.tables.release();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
