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

##### Java内存模型 - 同步八种操作
###### 1、lock(锁定) : 作用于主内存的变量， 把一个变量标示为一条线程独占状态
###### 2、unlock(解锁) : 作用于主内存的变量， 把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定
###### 3、read(读取) : 作用于主内存的变量，把一个变量值从主内存传输到线程的工作内存中，以便随后的load动作使用
###### 4、load(载入) : 作用于工作内存的变量，它把read操作从主内存中得到的变量值放入到工作内存的变量副本中
###### 5、use(使用) : 作用于工作内存的变量，把工作内存中的一个变量值传递给执行引擎
###### 6、assign(赋值) : 作用于工作内存的变量，它把一个从执行引擎接收到的值赋值给工作内存的变量
###### 7、store(存储) : 作用于工作内存的变量，把工作内存中的一个变量的值传送到主内存中，以便随后额write操作
###### 8、write(写入) : 作用于主内存的变量， 它把store操作从工作内存中一个变量的值传送到主内存的变量中

##### Java内存模型 - 同步规则
###### 1、如果要把一个变量从主内存中赋值到工作内存， 就需要按顺序的执行read和load操作， 如果把变量从工作内存中同步回主内存中，就要按顺序地执行store和write操作。但Java内存模型只要求上述操作必须按顺序执行，而没有保证必须是连续执行
###### 2、不允许read和load、store和write操作之一单独出现
###### 3、不允许一个线程丢弃它的最近assign的操作， 即变量在工作内存中改变了之后必须同步到主内存中
###### 4、不允许一个线程无原因地 (没有发生过任何assign操作) 把数据从工作内存同步回主内存中
###### 5、一个新的变量只能在主内存中诞生，不允许在工作内存中直接使用一个未被初始化 (load 或 assign ) 的变量，即就是对一个变量实施use和store操作之前，必须先执行过了一个assign和load'操作
###### 6、一个变量在同一时刻只允许一条线程对其进行lock操作，但lock操作可以被同一条线程重复执行多次， 多次执行lock后， 只有执行相同次数的unlock操作，变量才会被解锁。lock和unlock必须成对出现
###### 7、如果对一个变量执行lock操作，将会清空工作内存中此变量的值，在执行引擎使用这个变量前需要重新执行load或assign操作初始化变量的值
###### 8、如果一个变量事先没有被lock操作锁定，则不允许对它执行unlock操作；也不允许去unlock一个呗其他线程锁定的变量
###### 9、对一个变量执行unlock操作之前，必须先把此变量同步到主内存中(执行store和write操作) 
### 并发的优势与风险
##### 优势:
###### 速度：
    系统可以同时处理多个请求，响应更快；
    复杂的操作可以分成多个进程同时进行
###### 设计：
    程序设计在某些情况下更简单，也可以有更多的选择
###### 资源引用
    CPU能够在等待IO时做一些其他的事情
    
##### 风险：
###### 安全性:
    多个线程共享数据时可能会产生与期望不相符的结果
###### 活跃性:
    某个操作无法继续进行下去时，就会发生活跃性问题，比如死锁，饥饿等问题
###### 性能:
    线程过多是会使得：CPU频繁切换，调度时间增多；同步机制；消耗过多内存
    
### 并发模拟
##### 1、Postman ： HTTP请求模拟工具
##### 2、Apache Bench (AB) ： Apache 附带的工具， 测试网站性能
    ApacheBench 是 Apache 服务器自带的一个web压力测试工具，简称ab。ab又是一个命令行工具，
    对发起负载的本机要求很低，根据ab命令可以创建很多的并发访问线程，模拟多个访问者同时对
    某一URL地址进行访问，因此可以用来测试目标服务器的负载压力。总的来说ab工具小巧简单，
    上手学习较快，可以提供需要的基本性能指标，但是没有图形化结果，不能监控。
###### AB 参数配置 https://blog.csdn.net/water_tone/article/details/79003059
    ab -n 1000 -c 50 http://localhost:8080/test 
    -n 本次测试总是为多少个
    -c 本次测试的并发数为多少个 
###### 测试结果讲解
    Concurrency Level:      50                                                          // 测试的并发量为50
    Time taken for tests:   0.287 seconds                                               // 测试用的时间
    Complete requests:      1000                                                        // 完成的请求数
    Failed requests:        0                                                           // 失败的请求数
    Total transferred:      136000 bytes                                                // 所有请求的响应数据的长度总和，包括每个Http响应数据的头信息和正文数据的长度，
                                                                                           这里不包括Http请求数据的长度，仅仅展示web服务器流向用户应用层数据的长度
    HTML transferred:       4000 bytes                                                  // 所有请求的响应数据中正文数据的长度总和(总长度-头信息)
    Requests per second:    3481.89 [#/sec] (mean)                                      // 吞吐率 与并发数相关 (Complete requests) / (Time token for tests)
    Time per request:       14.360 [ms] (mean)                                          // 用户平均请求等待时间
    Time per request:       0.287 [ms] (mean, across all concurrent requests)           // 服务器平均请求等待时间
    Transfer rate:          462.44 [Kbytes/sec] received                                // 请求在单位时间内从服务器获取的数据长度  (Total transferred) / (Time taken for tests)
    
    Connection Times (ms)
                  min  mean[+/-sd] median   max
    Connect:        0    0   0.4      0       1
    Processing:     2   14   1.8     14      17
    Waiting:        1    8   3.7      8      16
    Total:          2   14   1.8     14      17
    
    Percentage of the requests served within a certain time (ms)
      50%     14
      66%     14
      75%     15
      80%     15
      90%     15
      95%     16
      98%     16
      99%     16
     100%     17 (longest request)
##### 3、JMeter ： Apache组中开发的压力测试工具
###### 比AB更强大， 用的最多的
##### 4、代码 ： Semaphore、CountDownLatch等 对并发的模拟 
###### CountDownLatch类 向下减的 可以阻塞线程， 当线程满足一定的条件才可以向下执行
    countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
    是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，
    当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
###### Semaphore 信号量  也可以阻塞线程

### 线程安全性
##### 定义：当多个线程访问某个类时，不管运行时环境采用何种调用方式或者这些进程将如何交替执行，并且在主调代码中不需要任何额外的同步或协同，这个类都能表现出正确的行为，那么就称这个类是线程安全的
##### 线程安全性主要体现在三个方面: 
###### 1、原子性: 提供了互斥访问，同一时刻只能有一个线程来对他进行操作
###### 2、可见性: 一个线程对主内存的修改可以及时的被其他线程观察到
###### 3、有序行: 一个线程观察其他线程中的指令执行顺序，由于指令重排序的存在，该观察结果一般杂乱无序

##### 原子性 - Atomic包
###### AtomicXXX : CAS、 Unsafe.compareAndSwapInt
###### CAS原理实现
    public final int getAndAddInt(Object var1, long var2, int var4) {
            int var5;
            do {
                // 从底层取到当前对象变量的值
                var5 = this.getIntVolatile(var1, var2);
                // compareAndSwapInt 判断当前var1对象对应的值var2是否与主存取出的var5的值相等，
                // 如果相等就更新主存当前对象对应的值var5为var5 + var4，并跳出循环 
                // 如果不相等就更新当前线程缓存中（工作内存）主存对象值的拷贝值var2为var5，然后再循环判断
            } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
    
            return var5;
        }
    
###### ActomicLong、 LongAdder(jdk1.8)
###### LongAdder 优点：
    因为CAS底层实现是在一个while死循环内，不断的尝试修改目标值，直到修改成功，在竞争不激烈的时候，它修改成功的概率很高，如果竞争激烈额话，它修改失败的概率就很高，这个时候性能就会受到影响
    对于普通类型的long、double类型，JVM允许将64位的读操作或者写操作，拆成俩个32位操作
###### LongAdder原理实现：
    是将热点数据分离，比如可以将AtomicLong内部核心数据value，分离成一个数组，每个线程访问时，通过Hash等算法，映射到其中一个数字进行计数，最终的计数结果则为这个数组的求和累加，
    其中热点数据value会被分离成多个单元的shell，每个shell独自维护内部的值，当前对象的值由所有shell累计合成，这样就进行了有效的分离并提高了并行度，
    这样一来LongAdder就在AtomicLong的基础上将单点的压力分担在各个节点上，
    在低并发的时候通过对底层base的更新达到和AtomicLong一样的效果，
    在高并发的时候通过分担节点来提高性能
    
    缺点: 
        在统计的时候，如果有并发更新，统计的结果会有一些误差
        
###### AtomicReference、AtomicReferenceFieldUpdater 
###### AtomicStampReference : CAS 的 ABA 问题
    什么是CAS的ABA问题？
        ABA问题是指在CAS操作的时候其他线程将变量的值A改成了B，然后有改成了A，
        本线程在CAS操作的时候发现当前底层变量的值和工作内存中的值相等，进而进行了更新操作，
        这个时候实际上值已经被其他线程修改过， 这与CAS本身的设计思想是不相符的    
    ABA的解决思路：
        每次变量更新的时候将变量的版本号加1，1A -> 2B -> 3A 
###### AtomicStameReference源码
    public boolean weakCompareAndSet(V   expectedReference,
                                         V   newReference,
                                         // 期望的版本号
                                         int expectedStamp,
                                         // 新的版本号
                                         int newStamp) {
            return compareAndSet(expectedReference, newReference,
                                 expectedStamp, newStamp);
        }
        
###### AtomicLongArray
    和 AtomicLong方法基本类似， 只是会多加了一个索引值参数
###### 实例 AtomicBoolean 中的compareAndSet(boolean expect, boolean update) 

##### 原子性 - 锁
    原子性提供了互斥访问，同一时刻只能有一个线程来对他进行操作
    能保证统一时刻只有一个线程来对它进行操作的处理原子性，还有锁
###### synchronized : 依赖JVM
    它是Java的关键字，主要是依赖JVM去实现锁，因此在这个关键字作用对象的作用范围内，都是统一时刻只能有一个线程进行操作的
###### Lock : (代码层面的锁) 依赖特殊的CPU指令， 代码实现， ReentrantLock
###### 原子性 - synchronized
    1、修饰代码块 : 大括号括起来的代码，作用于调用的对象
    2、修饰方法 : 整个方法，作用于调用的对象
    3、修饰静态方法 : 整个静态方法， 作用于所有对象
    4、修饰类 : 括号括起来的部分，作用于所有对象  
###### 子类在继承父类的时候，继承到的方法是不包含synchronized关键字的
    因为synchronized是方法声明的一部分， 如果子类也想要synchronized需要显示的声明
###### 原子性 - 对比
     synchronized : 不可中断锁， 适合竞争不激烈，可读性好
     Lock : 可中断锁，多样化同步，竞争激烈是能维持常态
     Atomic : 竞争激烈是能维持常态， 比Lock性能好； 只能同步一个值
##### 可见性
###### 导致共享变量在线程间不可见的原因
    1、线程交叉执行
    2、重排序结合成成交叉执行
    3、共享变量跟新好的值没有在工作内存与主内存间及时更新
    
###### 可见性 - synchronized
    JVM关于Synchronized的俩条规定
        1、线程解锁前，必须把共享变量的最新值刷新到主内存
        2、线程加锁时、将清空工作内存中共享变量的值，从而使用共享变量是需要从主内存中重新读取罪行的值(注意， 加锁与解锁是同一把锁)
###### 可见性 - volatile  (不具有原子性)
    通过加入内存屏障和禁止重排序优化来实现
        1、对volatile变量写操作时，会在写操作后加入一条store屏障指，将本地内存中的共享变量值刷新到主内存
        2、对volatile变量读操作时，会在读操作前加入一条load屏障指令，从主内存中读取共享变量
###### 可见性 - volatile使用  (适合作为状态标记量， double-check单例模式中)
###### 1、对变量的写操作不依赖当前值
###### 2、该变量没有包含对其他变量不必要的式子中 
        volatile boolean inited = fales;
        
        // 线程 1:
        context = loadContext();
        inited = true;
        
        // 线程 2:
        while( !inited ){
        sleep();
        }
        doSomethingWithConfig(context); 
        
##### 有序性
###### 1、Java内存模型中，允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多项城并发执行的正确性
###### 2、可以通过 volatile、synchronized、Lock来保证指令的有序性
###### 有序性 - happens-before原则
###### 1、程序次序规则 : 一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后边的操作
    程序看起来执行顺序是按照代码顺序执行的，但是虚拟机可能会对程序执行指令重排序来进行优化，
    虽然执行了重排序，但是程序执行的结合和按代码顺序执行的结果是一样的
    它只会对不存在数据依赖性的程序执行指令重排序
###### 2、锁定原则 : 一个unLock操作先行发生于后面对同一个锁的lock操作
    无论是单线程操作还是多线程操作，同一个锁如果处于被锁定状态，那么必须先对锁进行释放操作，
###### 3、volatile变量规则 : 对一个变量的写操作先行发生于后面对这个变量的读操作
    如果一个线程先去写一个变量，然后一个线程去读取，那么写操作会先行发生于读操作
###### 4、传递规则 : 如果操作A先行发生于操作B，而操作B有先行发生于操作C，则可以得到操作A先行发生于操作C
###### 5、线程启动规则 : Thread对象的start()方法先行发生于此线程的每一个动作
###### 6、线程中断规则 : 对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生
###### 7、线程终结规则 : 线程中所有的操作都先行发生于线程的终止检测， 我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行
###### 8、对象检测原则 : 一个对象的初始化完成先行发生于他的finalize()方法的开始


### 发布对象
##### 发布对象 ：是一个对象能够被当前范围之外的代码所使用
##### 对象溢出 ：一种错误的发布， 当一个对象还没有构造完成是，就使他被其他线程所见
#### 安全发布对象
##### 1、在静态初始化函数中初始化一个对象引用
##### 2、将对象的引用保存到volatile类型域或者AtomicReference对象中
##### 3、将对象的应用保存到某个正确构造对象的final类型域中
##### 4、将对象的应用保存到一个由锁保护的域中 

### 不可变对象 (安全发布对象)
#### 不可变对象满足的条件 (参照String类)
##### 1、对象创建以后其状态就不能修改
##### 2、对象所有的域都是final类型
##### 3、对象是正确创建的 (在对象创建期间， this引用没有溢出) 
#### final关键字 ： 类、方法、变量
##### 修饰类 ：不能被继承 (Integer Long String ...)
    final 类中的所有成员方法，都会被隐式的被final修饰
##### 修饰方法： 1、锁定方法不被继承类修改； 2、效率
##### 修饰变量：
    基本数据类型变量：数值一旦初始化之后就不能再修改了
        传入方法的基本类型变量如果被final修饰，那么它也是不能被修改的
    引用类型变量：则在对其初始化之后则不能让它再指向另外一个对象
#### Collections.unmodifiableXXX: Collection、List、Set、Map...
##### java中的不允许修改的工具类， 只需要将不想被修改的Collection、List、Set、Map... 作为参数放入到这个方法中，就能得到
##### Guava: Google的Guava包中新增的ImmutableXXX : Collection、List、Set、Map...

#### 线程封闭
    把对象封装到一个线程里，只有一个线程能看到这个对象
##### 1、Ad-hoc线程封闭 ：程序控制实现，最糟糕，忽略
    完全靠实现者控制的线程封闭
##### 2、堆栈封闭 ：局部变量，无并发问题
    局部变量，多个线程访问同一个局部变量的时候，每个线程会将局部变量拷贝到自己的工作内存中
##### 3、ThreadLocal 线程封闭 ： 特别好的封闭方法
    数据库连接对应JDBC的Connection对象，
    Connection在实现时并没有对线程安全做处理， Java规范中也没有要求Connection是线程安全的
    实际上在服务器应用程序中，线程从连接池获取一个Connection对象，使用完成以后再将Connection放回到连接池中
    由于大多数请求都是由单线程采用同步的方式来处理的，并且在Connection返回之前，连接池不会将它分配给其他线程
    因此这种连接管理模式在处理请求时隐含的将Connection对象封闭在线程里边，也就实现了线程安全
    
#### 线程不安全类与写法
##### StringBuilder -> StringBuffer
##### SimpleDateFormat -> JodaTime
##### ArrayList、 HashSet、 HashMap 等 Collections
##### 先检查再执行 ： if(condition(a)) { handle(a); }

#### 线程安全 - 同步容器
##### 同步容器不一定是线程安全的
##### ArrayList -> Vector, Stack
    Vector：
        Vector实现了List接口，实际上就是一个数组，Vector中的方法都是被synchronized修饰的
    Stack ：
        继承于Vector类， 也是用synchronized修饰的，就是数据结构中的栈(先进后出)
##### HashMap -> HashTable(key, value不能为null)
    HashTable内部进行了同步代码
##### Collections.synchronizedXXX(List、Set、Map)
    Collections 是一个工具类， 提供了大量的方法，包括同步容器的创建
##### 针对集合的删除操作：
    在遍历的时候不要进行删除操作，对要删除的数据进行标记， 等待遍历结束以后进行统一的删除处理
#### 线程安全 - 并发容器 J.U.C （Java.Util.Concurrent）
##### ArrayList -> CopyOnWriteArrayList
    CopyOnWriteArrayList ：写操作的时候复制，
        当有新元素添加到CopyOnWriteList的时候，它从原有的数组里边拷贝一份，然后在新的数组上进行拷贝操作，写完之后再将原来的数组指向新的数组
        整个add操作都是在锁的基础上操作的
        缺点：
            1、进行写操作的时候需要copy数组， 比较消耗内存
            2、不能用于实时读的场景，更适合读多写少的场景
###### CopyOnWriteArrayList的设计思想是：
    1、读写分离，让读和写分开
    2、最终一致性
    3、使用时另外开辟空间， 通过这种方式来解决并发冲突
    4、读的时候不需要加锁， 写的时候需要加锁，防止多个线程复制出多个副本
##### HashSet、TreeSet -> CopyOnWriteArraySet、ConcurrentSkipListSet
    CopyOnWriteArraySet、ConcurrentSkipListSet （不允许有null）
        单独的add操作是线程安全的
        如果使用containsAll()、removeAll()、retainAll()的时候是需要加锁的，
        因为它只能保证每一次add是原子操作的，不能保证多个add的时候不会被其他线程打断
        
##### HashMap、TreeMap -> ConcurrentHashMap、ConcurrentSkipListMap
    ConcurrentHashMap、ConcurrentSkipListMap (不允许有null值)
    ConcurrentHash是HashMap的多线程版本，针对读操作做了很多优化，具有很高的高并发性能
    ConcurrentSkipListMap是TreeMap的多线程版本，内部是使用SkipList这种跳表的结构来实现的
        4个线程 1.6w数据量的情况下，ConcurrentHashMap的存取速度是ConcurrentSkipListMap的4倍
    在非多线程的情况下尽量使用HashMap和TreeMap        
    在并发性相对较低的并行程序，也可以使用Collections类
        synchronizedSortedMap()， 也能提高程序的效率
###### ConcurrentSkipListMap有几个是ConcurrentHashMap不能比的有点
    1、ConcurrentSkipListMap的key是有序的
    2、ConcurrentSkipListMap支持更高的并发，它存取时间和线程数几乎是没有关系的，
        也就是说在数据量一定的情况下，线程数越多ConcurrentSkipListMap的性能越好
### 安全共享对象策略 - 总结
#### 1、线程限制 ： 一个被线程限制的对象，由线程独占， 并且只能被占有它的线程修改       
#### 2、共享只读 ： 一个共享只读的对象，在没有额外同步的情况下，可以被多个线程并发访问，但是任何线程都不能修改它
#### 3、线程安全对象 ： 一个线程安全的对象或者容器，在内部通过同步机制来保证线程安全，所以其他线程无需额外的同步就可以通过公共接口随意访问它
#### 4、被守护对象 ： 被守护对象只能通过获取特定的锁来访问

### J.U.C 之 AQS
#### AbstractQueuedSynchronizer - AQS
    java5之后引入了J.U.C，大大提高了Java并发的性能，AQS可以说是J.U.C的核心
    提供了Fist IN First OUT 
    底层使用的是双向列表
##### 1、使用Node实现FIFO队列，可以用于构建锁或者其他同步装置的基础框架
##### 2、利用了一个int类型表示状态
##### 3、使用方法是继承
##### 4、子类通过继承并通过实现他的方法管理其状态{ acquire 和 release }的方法操纵状态
##### 5、可以同时实现排他锁和共享锁模式 (独占、共享)
###### 在一个使用者的角度，AQS的功能主要分为两类：独占功能和共享功能
    AQS所有的子类中，要么实现并使用了他独占功能的API， 要么使用了共享锁的功能，不会使用两套API
##### AQS具体实现的大致思路：
###### 1、首先AQS内部维护了一个CLH队列来管理锁，线程会首先尝试获取锁，如果失败，将当前线程以及等待等信息包成一个node节点，加入到同步队列SyncQueue里
###### 2、接着会不断尝试的获取锁，条件是当前节点为Head的直接后继才会尝试获取锁，如果失败就会阻塞自己，直到自己被唤醒
###### 3、当持有锁的线程释放锁的时候会唤醒队列中的后继线程 


#### AQS 同步组件
##### CountDownLatch
    它是一个闭锁，通过一个计数来保证线程是否需要一直阻塞
###### CountDownLatch是一个同步辅助类，可以阻塞当前线程，用了一个给定的计数器进行初始化，该计数器的操作是原子操作(同时只有一个线程可以操作该计数器)，调用awaiting()的线程会一直处于阻塞状态，直到其他线程调用countDown()方法使当前计数器变成0，每次调用countDown()的时候,计数器的值会减1，当计数器的值减到0的时候，所有因调用await()而处于等待状态的线程继续执行，这里的计数器不能被重置
###### 使用场景：
    在某些业务场景中，程序执行需要等到某个业务场景完成后，才能继续执行后续操作，典型的应用比如：
        并行计算：将一个大的任务拆分成多个子任务，并行执行，等所有子任务都结束后在统计父任务的结果
    案例代码中并发模拟的时候用的就是CountDownLatch，保证所有的线程都执行完之后才进行统计求和的结果
##### Semaphore （信号量）
    他能控制同一时间并发线程的数目
###### 使用场景：
    常用于仅能提供有限访问资源， 比如项目中的数据库，数据库的连接数的限制
##### CyclicBarrier
###### 是一个同步辅助类，允许一组线程相互等待，直到到达某个公共的屏障点(CommonBarrierPront),通过它可以完成多个线程之间相互等待，只有当每个线程准备就绪后才能继续往下执行后续的操作，和CountDownLatch类似，都是通过计数器来实现的，
    区别:
        CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法进行循环使用
        CountDownLatch用于实现一个或多个线程等待其他线程完成某项操作之后继续执行（描述的是一个或多个线程等待其他线程的关系），
            而CyclicBarrier主要是实现了多个线程相互等待，直到所有线程都满足了条件之后才能继续执行后续的操作（描述的是各个线程内部相互等待的关系）
###### 所以CyclicBarrier能处理更复杂的业务场景
##### ReentrantLock 与 锁
###### Java主要分两类锁： 一种是Synchronized修饰的锁， 另外一种就是J.U.C里边提供的锁
###### ReentrantLock就是 J.U.C 里边提供的锁 核心也是 lock 和 unlock
###### 特点
    1、可重入锁
        再进入锁，和Synchronized类似，都是同一个线程进入一次锁的计数器，就自增1，所以要等锁的计数器下降为0时才能释放锁
    2、锁的实现
        synchronized是依赖JVM实现的， ReentrantLock是JDK实现的
        区别：
            前者相当于操作系统来控制实现，不容易查看源码
            后者相当于用户用程序控制实现，可以查看源码
    3、性能的区别：
        Synchronized 和 ReentrantLock的性能现在是差不多的，在两者都可以用的情况下官方更推荐Synchronize，因为写法更简单
        其实Synchronized的优化感觉就是借鉴了ReentrantLock的CAS技术，都是在用户态就把加锁问题解决，避免进入内核态的线程阻塞
    4、功能区别：
        1）、便利性：Synchronized更方便使用，由编译器去保证锁的加锁和释放的，而ReentreantLock是由程序手动加锁和释放的
        2）、锁的细粒度和灵活度：ReentrantLock会优于Synchronize
###### 公平锁：先等待的线程先获得锁
###### ReentrantLock独有的功能
    1、可指定是公平锁还是非公平锁，而Synchronized只能是非公平锁
    2、提供了一个Condition类，可以分组唤醒需要唤醒的线程
    3、提供能够中断等待锁的线程的机制， lock.lockInterruptibly()
###### ReentrantReadWriteLock， 内部包含了读写锁的概念，是一种悲观锁的体现
###### StampedLock 对吞吐量有巨大的提升
    StampedLock 它控制锁有三种模式
        写、读、乐观读
    一个StampedLock状态是由版本、模式俩个部分组成，锁获取方法返回是一个数字作为票据(Stamp)，它用相应的锁状态来表示和控制锁访问
        0 表示没有写锁被首先访问，在读锁上分为 悲观锁 和 乐观锁
    所谓乐观读，就是读的操作很多，写的操作很少，我们可以乐观的认为写入与读取发生的几率很少，因此不悲观的使用完全锁定
    
##### 线程选择总结：
###### 1、当只有少量的线程的时候，Synchronized是一个很少的通用的锁实现
###### 2、线程不少，但是线程增长的趋势我们是可以预估的， 这个时候ReentrantLock是一个很好的实现
##### Condition

##### FutureTask
