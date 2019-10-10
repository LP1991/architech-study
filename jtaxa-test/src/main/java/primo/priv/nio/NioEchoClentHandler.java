/********************** 版权声明 *************************
 * 文件名: NioEchoClentHandler.java
 * 包名: priv.primo.primo.priv.nio
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/5/18
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioEchoClentHandler implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;

    private ExecutorService service ;

    private volatile boolean stop;

    public NioEchoClentHandler(String ip, int port) {
        this.host = ip ==null?"127.0.0.1":ip;
        this.port = port;
        this.service = Executors.newSingleThreadScheduledExecutor();

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress(host,port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handlerInput(key);
                    } catch (Exception e) {
                        if (key !=null) {
                            key.cancel();
                            if (key.channel() !=null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
//        多路复用器关闭，所有注册的channel和pipe都会关闭。
        if (selector !=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (service !=null){
            service.shutdown();
        }
    }

    private void handlerInput(SelectionKey key) throws Exception{
        if (key.isValid()) {
            SocketChannel channel = (SocketChannel)key.channel();
            if (key.isConnectable()) {
                if (channel.finishConnect()) {
                    System.out.println("连接到服务器");


                    ByteBuffer buff = ByteBuffer.allocate(1024);

                    System.out.println("请输入，消息Quit 退出");

                    service.submit(()->{
                        while (true){
                            buff.clear();
                            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            String msg = null;
                            try {
                                msg = bufferedReader.readLine();

                                if (msg.equals("Quit")){
                                    System.out.println("关闭客户端");
                                    key.cancel();
                                    this.stop = true;
                                    break;

                                }

                                buff.put(msg.getBytes());
                                buff.flip();
                                channel.write(buff);
                                System.out.println("请输入，消息Quit 退出");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
            socketChannel.register(selector, SelectionKey.OP_READ);
        }else {
            System.exit(1);
        }

        if (key.isReadable()){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(buffer);
            if (read > 0){
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String body = new String(bytes, "UTF-8");
                System.out.println(body);

                if (body.equals("Quit")){
                    this.stop = true;
                }
            }else if (read < 0){
                key.cancel();
                socketChannel.close();
            }else {

            }
        }

        if (key.isWritable()){
            System.out.println("the key is writable");
        }
    }
}
