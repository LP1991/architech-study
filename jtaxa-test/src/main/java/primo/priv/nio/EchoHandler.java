/********************** 版权声明 *************************
 * 文件名: EchoHandler.java
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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoHandler implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private  volatile boolean stop;
    private int num = 0;


    public EchoHandler(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器在端口"+port+"等待客户连接.....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                if (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    try{
                        handleInput(next);
                    }catch (Exception e){
                        if (next != null) {
                            next.cancel();
                            if (next.channel() != null){
                                next.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey next) throws IOException {
        if (next.isValid()){
            if (next.isAcceptable()){
                ServerSocketChannel channel = (ServerSocketChannel)next.channel();
                SocketChannel accept = channel.accept();
                accept.configureBlocking(false);
                SelectionKey register = accept.register(selector, SelectionKey.OP_READ);
                register.attach(num++);
            }

            if (next.isReadable()){
                SocketChannel channel = (SocketChannel)next.channel();
                ByteBuffer bf = ByteBuffer.allocate(1024);
                int read = channel.read(bf);
                if (read > 0) {
                    bf.flip();
                    byte[] bytes = new byte[bf.remaining()];
                    bf.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("来自客户端"+next.attachment()+"的输入："+body);
                    if (body.trim().equals("Quit")) {
                        System.out.println("关闭客户端"+next.attachment()+".....");
                        next.cancel();
                        channel.close();
                    }else {
                        String response = "来自服务器的响应"+body;
                        doWrite(channel,response);
                    }

                }else if (read < 0){
                    next.cancel();
                    channel.close();
                }else {

                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (response !=null && response.trim().length() >0){
            byte[] bytes = response.getBytes();
            ByteBuffer allocate = ByteBuffer.allocate(bytes.length);
            allocate.put(bytes);
            allocate.flip();
            channel.write(allocate);
        }
    }
}
