package meng.springframework.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//new io
public class NioHttpServer {

    public static final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

//    static {
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("当前活跃线程数：" + threadPoolExecutor.getActiveCount());
//            }
//        },0,3000);
//    }

    private static ServerSocketChannel serverSocketChannel;
    private static Selector selector;

    public static void openServer(Integer port) throws Exception {
        //1绑定端口
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//设置为非阻塞
        serverSocketChannel.bind(new InetSocketAddress(port));
//        System.out.println("NIO服务器端口启动" + port);
        System.out.println("NIO server port starts 8081");
        //2如何获取新连接 selector获取操作系统底层的tcp连接 动态
        selector = Selector.open();

        //选择器：根据条件去查询，对应的TCP连接情况。
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            selector.select(1000); //查询，如果没有新连接，就等待。1000毫秒内如果有新连接，继续执行

            Set<SelectionKey> results = selector.selectedKeys();

            Iterator<SelectionKey> iterator = results.iterator();
            //处理查询结果
            while (iterator.hasNext()){
                SelectionKey result = iterator.next();

                //根据不同类型分来处理
                if (result.isAcceptable()){ //代表新连接建立
                    SocketChannel socket = serverSocketChannel.accept(); //nio体现 accpet不阻塞，没有连接则返回null
                    socket.configureBlocking(false);
                    //此处对比bio来说 不直接交给线程池去处理
                    //交给selector查询 监听是否有数据进来
                    socket.register(selector,SelectionKey.OP_READ);
                }else if (result.isReadable()){ //有数据请求的连接
                    SocketChannel channel = (SocketChannel) result.channel();
                    //处理过程中 ，取消在选择器 对应连接它的注册
                    result.cancel();
                    //有数据的请求 交给线程池去处理
                    threadPoolExecutor.submit(() ->{
                        //读取数据方式更换
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        channel.read(byteBuffer);
                        byteBuffer.flip();//读取
                        String request = new String(byteBuffer.array());

                        // TODO 处理请求... 调用servlet，触发springmvc...controller
                        System.out.println(request);

                        //缓冲区
                        Map<String,Object> map = new HashMap<>();
                        map.put("code",200);
                        map.put("msg","success");
//                        map.put("msg","请求成功");
                        String responseContent = "HTTP/1.1 200 OK\r\nContent-Length: "+map.toString().length()+"\r\n\r\n"+map.toString()+"";
                        channel.write(ByteBuffer.wrap(responseContent.getBytes()));

                        //处理完之后，为了能够继续处理 -- 重新注册
                        channel.register(selector,SelectionKey.OP_READ);
                        return null;
                    });
                }
                iterator.remove(); //删除处理过的结果(事件)
            }
            selector.selectNow();
        }
    }
}
