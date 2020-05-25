## On Java 8

### 第九章 多态 Polymorphism

- 多态消除了类型之间的耦合

举个例子来具体说明：

- 有一个叫做Instrument的基类，代表了乐器总类

  - ```java
    class Instrument {
      public void play(){
        System.out.println("Instrument.play()");
      }
    }
    ```

- 然后我们建一个管类乐器的类Wind，继承于Instrument

  - ```java
  class Wind extends Instrument {
      @Override
      public void play(){
        System.out.println("Wind.play()");
      }
    }
    ```

- 现在我们建立一个Music类，其方法```tune()```接受一个Instrument引用，同时也接受任何派生自Instrument的类引用：

  - ```java
    public class Music {
      public static void tune(Instrument i){
        i.play();
      }
      public static void main(String[] args){
        Wind flute = new Wind();
        tune(flute);	//Output: Wind.play()
      }
    }
    ```

##### 问题来了，现在我们不止有Wind一种子乐器类，还有Brass，Stringed乐器类，Music类在引用时该如何处理呢？

```java
class Stringed extends Instrument {
  @Override
  public void play(){
    System.out.println("Stringed.play()");
  }
}
class Brass extends Instrument {
  @Override
  public void play(){
    System.out.println("Brass.play()");
  }
}
```

**第一种方案：overload**

- 这样行得通，但是问题在于必须为添加的每个新Instrument类编写tune方法

```java
    public static void tune(Wind i) {
        i.play();
    }
    public static void tune(Stringed i) {
        i.play();
    }
    public static void tune(Brass i) {
        i.play();
    }
```

**第二种方案：多态（后期绑定）**

```java
  public static void tune(Instrument i){
    i.play();
  }
```

- 那么，只是接受instrument这个类的引用，编译器是如何知道到底是Wind，Stringed还是Brass呢？
- 这里就涉及到**绑定**了

---

### 方法调用绑定

- 将一个方法调用和一个方法主体关联起来称作***绑定***

- 若绑定发在程序运行前，为**前期绑定**，否则为**后期绑定/动态绑定**
- Java中除了```static```和```final```方法外，其他所有方法都是后期绑定

---

**陷阱一：“重写”私有方法**

- private方法可以当作是final的，因此对于派生类来说是隐蔽的。
- 所以只有非private方法才能被重写



**陷阱二：属性与静态方法**

- 只有普通的方法调用可以是多态的，如果你直接访问一个属性，该访问会在编译时解析。
- 如果一个方法是static的，那么它的行为就不具有多态性

---

**构造器的调用顺序**

- 在派生类的构造过程中总会调用基类的构造器。初始化会自动按即成层次结构上移，因此每个基类的构造器都会被调用到。

**继承与清理**

- 通常情况下，清理问题可以交给gc处理。如果存在清理问题，那么必须用心的为新类创建一个```dispose()```方法。override时记得要调用基类的方法。

- 销毁的顺序应该与初始化的顺序相反。

**构造器内部多态方法的行为**

- ```java
  class Glyph {
      void draw(){
          System.out.println("Glyph.draw()");
      }
      Glyph(){
          System.out.println("Glyph() before draw()");
          draw();
          System.out.println("Glyph() after draw()");
      }
  }
  
  class RoundGlyph extends Glyph {
      private int radius = 1;
      RoundGlyph(int r) {
          radius = r;
          System.out.println("RoundGlyph.RoundGlyph(), radius = " + radius);
      }
    
      @Override
      void draw() {
          System.out.println("RoundGlyph.draw(), radius = " + radius);
      }
  }
  
  public class PolyConstructors {
      public static void main(String[] args) {
          RoundGlyph g = new RoundGlyph(5);
      }
  }
  ```

- 输出为：

- ```java
  Glyph() before draw()
  RoundGlyph.draw(), radius = 0	
    // draw() 调用的是派生类重写的
    // 0 是因为在所有事情发生前，分配给对象的存储空间会被初始化为0
  Glyph() after draw()
  RoundGlyph.RoundGlyph(), radius = 5	
  ```

- 编写构造器需注意：

  - 做尽量少的事情
  - 可能的话，尽量不要调用类中的任何方法
  - 在基类的构造器之中能安全调用的只有基类的final方法(private) 因为不能被重写

