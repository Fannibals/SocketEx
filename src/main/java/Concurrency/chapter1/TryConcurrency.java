package Concurrency.chapter1;

public class TryConcurrency {
    public static void main(String[] args) {

        Thread t1 = new Thread("Customer-Thread"){
            @Override
            public void run(){
                for (int i = 0; i < 50; i++) {
                    System.out.println("Task 1=>"+i);
                    try {
                        Thread.sleep(1000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t1.start();

        for (int i = 0; i < 50; i++) {
            System.out.println("Task 2=>"+i);
        }
    }
}
