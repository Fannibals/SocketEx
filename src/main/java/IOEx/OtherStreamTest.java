package IOEx;

import org.junit.Test;

import java.io.*;

public class OtherStreamTest {

    /**
     * Standard IO
     * - System.in : standard input stream, input from keyboard in default
     * - System.out: standard output stream, output to console
     */


    /**
     * 小练习：从键盘输入字符串，要求将读取到的整行字符串转成大写输出，
     *        然后继续进行输入操作，知道用户输入'e' / 'exit'
     *
     * 方式一：Scanner
     * 方式二：System.in --> transfer stream --> BufferedReader's readline()
     */

    public void test1(){
        // 1. standard input
        InputStream is = System.in;
        BufferedReader br = null;
        try {
            // 2. transfer stream
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            while(true){
                System.out.println("Please input: ");
                String input = br.readLine();
                if ("e".equalsIgnoreCase(input) ||
                    "exit".equalsIgnoreCase(input)){
                    break;
                }
                String upCase = input.toUpperCase();
                System.out.println(upCase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        OtherStreamTest streamTest = new OtherStreamTest();
//        streamTest.test1();
        PrintStream pw = System.out;
        pw.println(555);
    }

}
