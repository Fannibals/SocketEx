package OnJava8.Polymorphism;

class Instrument {
    public void play (Note n){
        System.out.println("Instrument.play()");
    }
    public void pause(){
        System.out.println("Instrument.pause()");
    }
}
public class Wind extends Instrument{
    @Override
    public void play(Note n) {
        System.out.println("Wind.play() " + n);
    }
}

