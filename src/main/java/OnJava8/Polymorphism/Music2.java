package OnJava8.Polymorphism;

class Stringed extends Instrument{
    @Override
    public void play(Note n) {
        System.out.println("Stringed.play() " + n);
    }
}

class Brass extends Instrument{
    @Override
    public void play(Note n) {
        System.out.println("Brass.play() " + n);
    }

    @Override
    public void pause() {
        System.out.println("Brass.pause()");
    }
    public void stop(){
        System.out.println("Brass.stop()");
    }
}


public class Music2 {
    public static void tune(Instrument i){
        i.play(Note.MIDDLE_C);
    }
    public static void tune(Wind i) {
        i.play(Note.MIDDLE_C);
    }
    public static void tune(Stringed i) {
        i.play(Note.MIDDLE_C);
    }
    public static void tune(Brass i) {
        i.play(Note.MIDDLE_C);
    }

    public static void main(String[] args) {
        Instrument flute = new Wind();
        Instrument violin = new Stringed();
        Instrument frenchHorn = new Brass();

        tune(flute);
        tune(violin);
        tune(frenchHorn);
        violin.pause();
        frenchHorn.pause();
    }
}
