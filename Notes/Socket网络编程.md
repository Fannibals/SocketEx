## Socket网络编程

### Lesson 1. 网络编程

1. **网络模型-OSI**
   + 基础层（Physical、Datalink、Network）
   + 传输层（TCP—UDP、Socket）
   + 高级层（Session、Presentation、Application）
2. **TCP/IP Model**
   + Application Layer :  Telnet,FTP    |    DNS, RIP
   + Transport Layer        :    TCP                    UDP
   + Internet Layer       :                        IP
   + Network Interface Layer :  Ethernet
3. **Socket**
   + IP + Port
   + TCP/IP协议的相关Api的总称；是网络Api的集合实现

### Lesson 2. 必备前置知识 - IO流

1. **Stream流 - 分类**

   + 按照操作**数据单位**：字节流(8 bit)、字符流(16 bit)

     + 字节流：图片、视频 
     + 字符流：文字

   + 按照**流向**：输入流、输出流

   + 按照流的**角色**：节点流、处理流

     + 节点流：程序用于直接操作目标设备所对应的类叫节点流
     + 处理流：程序通过一个间接流类去调用节点流类，以达到更加灵活方便地读写各种类型的数据，这个间接流类就是处理流

     | 抽象基类 | 字节流       | 字符流 |
     | -------- | ------------ | ------ |
     | 输入流   | InputStream  | Reader |
     | 输出流   | OutputStream | Writer |

2. 分类表

<img src="/Users/Ethan/Desktop/SocketEx/Notes/Screen Shot 2020-04-29 at 5.00.54 pm.png" alt="Screen Shot 2020-04-29 at 5.00.54 pm" style="zoom:100%;" />

3. **处理流之一：缓冲流**
   - BufferedInputStream, BufferedOutputStream, BufferedReader, BufferedWriter
   - **Effect:** Increase the I/O speed of the stream
     - Machanism: it provides an internal buffer area
   - bos.**flush()** : **refresh** the buffer area       ---> 刷新缓冲区
     - **flush():** Flushes the output stream and **forces any buffered output** bytes to be written out.

- ![Screen Shot 2020-05-01 at 3.20.15 pm](/Users/Ethan/Desktop/SocketEx/Notes/Screen Shot 2020-05-01 at 3.20.15 pm.png)



4. **处理流之二：转换流**
   - 转换流提供了字节流和字符流之间的转换，属于字符流
     - InputStreamReader: byte --> char
     - OutputStreamWriter: char --> byte

### Lesson 3. UDP 

1. **User Datagram Protocol**

   - 是一种用户数据报协议
   - **面向数据报**的**传输层**协议，正式规范为RFC 768
   - 用户数据协议，**非连接**协议 (不可靠)

2. **为何不可靠？**

   - 一旦把应用程序发给网络层的数据发送出去，就**不保留数据备份**

   - IP数据报的头部**仅加入了<font color=red>复用分用</font>和<font color=red>数据校验</font>**
     - 结构简单、无校验？、速度快、容易丢包、可广播

3. **能做什么？**
   - DNS、TFTP (Trivial File Transfer Protocol)、SNMP
   - 视频、音频 （直播）
4. **UDP包最大长度**
   - 头部有32bits存放source port & dest port
   - 2^16-1 = 65535
   - 自身协议占用：32 + 32 = 64 bits = 8 bytes
   - 实际上因为还有ip头长度，因而最大长度为65507 bytes
     - However the actual limit for the data length, which is imposed by the underlying [IPv4](https://en.wikipedia.org/wiki/IPv4) protocol, is 65,507 bytes (65,535 − 8 byte UDP header − 20 byte [IP header](https://en.wikipedia.org/wiki/IPv4_header)).
5. **伪头部**
   - 通过伪首部的校验，UDP可以确定该[数据报](https://baike.baidu.com/item/数据报/2194617)是不是发给本机的，通过首部协议字段，UDP可以确认有没有误传。
   - 传输时是没有伪首部的，只有在两端checksum时才会填上
   - 全是1则无差错

![Screen Shot 2020-05-06 at 3.19.20 pm](/Users/Ethan/Desktop/SocketEx/Notes/Screen Shot 2020-05-06 at 3.19.20 pm.png)




#### UDP API in Java

1. **DatagramSocket**

   - 用于接收与发送UDP的类

2. **DatagramPacket**

   - 用于处理报文
     - 将byte数组、目标地址、目标端口等数据包装成报文
     - 将报文拆卸成byte数组

   - **DatagramPacket(byte[] buf, int length, SocketAddress address)**
     - SocketAddress = InetAddress + Port



#### 单播、广播、多播

- IP地址类别

- 广播地址

  - 255.255.255.255 为受限广播地址
    - 如你向port 2000 发送，则只会在内网广播
  - C 网广播地址一般为：XXX.XXX.XXX.255 (192.168.1.255)

  