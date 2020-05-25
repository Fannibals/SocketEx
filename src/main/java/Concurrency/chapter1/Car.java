package Concurrency.chapter1;


class Engine {
    public void start(){}
    public void stop(){}
}

class Wheel {
    public void inflate(int psi){}
}

public class Car {
    private Engine engine = new Engine();
    private Wheel[] wheel = new Wheel[4];

    public Car() {
        for (int i = 0; i < 4; i++) {
            wheel[i] = new Wheel();
        }
    }

    public static void main(String[] args) {
        Car car = new Car();
        car.wheel[0].inflate(72);
    }
}
