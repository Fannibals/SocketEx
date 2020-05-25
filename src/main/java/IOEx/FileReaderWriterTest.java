package IOEx;

import org.junit.Test;

import java.io.*;

/**
 * 额外注意：不能使用字符流来处理图片等字节数据
 */
public class FileReaderWriterTest {
    public static void main(String[] args) {
        // 1. init a file object
//        File fileToRead = new File("/Users/Ethan/Desktop/SocketEx/README.md");
        File fileToRead = new File("README.md");
        System.out.println(fileToRead.getAbsolutePath());
        FileReader reader = null;
        try{
            // 2. provide a stream for reading
             reader = new FileReader(fileToRead);

            // 3. read the data
            // return The character read, or -1 if the end of the stream has been reached
            int data;
            while(( data = reader.read()) != -1){
                System.out.print((char)data);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            // remember to close the stream
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // upgrade version of read(), using the overload function of read
    @Test
    public void testFileReader1() {
        // 1. create File instance
        File fileToRead = new File("README.md");
        System.out.println(fileToRead.getAbsolutePath());

        FileReader fr = null;
        try {
            // 2. instantiation of FileReader
            fr = new FileReader(fileToRead);

            // 3. operation of reading
            char[] cbuf = new char[5];
            int len;
            while((len = fr.read(cbuf))!= -1) {
                // Method 1
//                for (int i = 0; i < len; i++) {
//                    System.out.print(cbuf[i]);
//                }
//
                // Method 2
                String str = new String(cbuf,0,len);
                System.out.print(str);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                // 4. close the stream
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输出操作：file可以是不存在的，会自动创建
     *
     * file对应的文件如果存在：
     *      if the constructor be used is: FileWriter(file,false) / FileWriter(file), then it will cover original one.
     *      otherwise, ie. FileWriter(file,true), it will append at the end of the original file
     */
    @Test
    public void testFileWriter() {
        // 1. instantiate file object
        File fileToRead = new File("README.txt");

        FileWriter fw = null;
        try {
            // 2. init stream
            fw = new FileWriter(fileToRead,true);

            // 3. write operations
            fw.write("Test");
            fw.write("Test2");
            fw.append("Hello");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void RW_CP_PIC(){
        // 1. init File objects
        File fromFile = new File("Cat03.jpg");
        File toFile = new File("Cat03-cp-rw.jpg");

        FileReader fr = null;
        FileWriter fw = null;
        try {
            // 2. init streams
            fr = new FileReader(fromFile);
            fw = new FileWriter(toFile);

            // 3. data operation
            char[] cbuf = new char[1024];
            int len;
            while((len = fr.read(cbuf))!=-1){
                fw.write(cbuf,0,len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) fr.close();
                if( fw != null) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
