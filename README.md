# java并发编程入门
### 基本概念
#####  并发： 同时拥有俩个或者多个线程，如果线程在单核处理器上运行，多个线程将交替的换入或者换出内存，这些线程是同时 "存在" 的，每个线程都处于执行过程中的某个状态，如果运行在多核处理器上，此时程序中的每个线程都将分配到一个处理器核上，因此可以同时运行
##### 高并发： 高并发( High Concurrency )是互联网分布式系统架构设计中必须考虑的因素之一，它通常是指，通过设计保证系统能够同时并行处理很多请求
##### 并发与高并发 的区别：
###### 1、并发：多个线程操作相同的资源，保证线程安全， 合理使用资源
###### 2、高并发： 服务能同时处理很多请求，提高程序性能 
## 并发编程的基础
### CPU多级缓存
##### 为什么需要CPU cache ： 
    cpu的频率太快了，快到主存跟不上，这样在处理时钟周期内，CPU常常需要等待主存，浪费资源。
    所以cache的出现，是为了缓解CPU和内存之间素的的不匹配问题
    (结构 : cpu -> cache -> memory)
    
##### CPU cache有什么意义 ：
    1、时间局限性：如果某个数据被访问， 那么在不久的将来它很可能被再次访问
    2、空间局限性：如果某个数据被访问， 那么与它相邻的数据很快也可能被访问 
    
##### CPU多级缓存 - 缓存一致性 ( MESI )协议
    用于保证多个CPU cache之间缓存共享数据的一致
    
##### CPU多级缓存 - 乱序执行优化
    处理器为条运算速度而做出的为被代码原有顺序的优化
    
### Java内存模型( java Memory Model, JMM )
##### Java内存模型规范: 
    规定了一个线程如何核实看到由其他线程修改后的共享变量的值，以及在必须是如何同步的访问共享变量。
##### 堆：
###### java中的堆是一个运行时数据区，堆是由垃圾回收来负责的，
    堆的优势是： 可以动态的分配内存大小，生存期也不必事先告诉编译器，因为它是在运行时动态分配内存的，java的垃圾收集器会自动搜索这些不再需要的数据
    堆的缺点是： 由于是在运行时动态分配内存，因此存取速度相对慢一些
##### 栈：
    栈的优势是： 栈的存取速度比堆快一些，仅次于计算机中的寄存器，栈的数据是可以共享的，
    栈的缺点是： 栈的数据大小与生存期必须是确定的，缺乏一些灵活性，
###### 栈中主要存放一些基本类型的变量(boolean、byte、char、short、int、float、double、long) 和对象句柄，
###### java内存模型 
    要求调用栈和本地变量(局部变量)存放在线程栈上，对象存放在堆上。
    方法中的本地变量(局部变量)也是存在线程栈上，即使对象是存在于堆上
    一个对象的成员变量跟随对象一起存在于堆上，（不论这个变量是基本数据类型还是引用数据类型）
    静态成员变量跟随类的定义一起存在于堆上
##### 注意: 
###### 当一个线程访问一个对象， 就可以访问这个对象的成员变量，
###### 当两个线程同时访问一个对象的同一个方法，并且该方法中还调用了该对象的成员变量，那么这俩个线程就会用同时拥有这个对象的成员变量的拷贝，这时候会发生问题

    