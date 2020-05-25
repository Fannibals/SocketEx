package OnJava8.Polymorphism;

class Useful {
    public void f(){
        System.out.println("Useful.f()");
    }
    public void g(){
        System.out.println("Useful.g()");
    }
}

class MoreUseful extends Useful {
    @Override
    public void f(){
        System.out.println("MoreUseful.f()");
    }

    public void g(){
        System.out.println("MoreUseful.g()");
    }

    public void u(){}
    public void v(){}
    public void w(){}

}
public class RTTI {
    public static void main(String[] args) {
        Useful[] x = {new Useful(), new MoreUseful()};
        x[0].f();
        x[1].f();
        x[0].g();
        x[1].g();
    }
}
