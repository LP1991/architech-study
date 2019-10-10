/********************** 版权声明 *************************
 * 文件名: DisruptorMainTest.java
 * 包名: priv.primo.disruptor
 * 版权:	杭州华量软件  architectstudy
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/8/15
 * 文件版本：V1.0
 *
 *******************************************************/
package priv.primo.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorMainTest {
    public static final EventFactory<Node> EVENT_FACTORY = new EventFactory<Node>()
    {
        public Node newInstance()
        {
            return new Node(1);
        }
    };
    private static  final ExecutorService executor = Executors.newSingleThreadExecutor(DaemonThreadFactory.INSTANCE);
    private static final RingBuffer<Node> ringBuffer = RingBuffer.createMultiProducer(EVENT_FACTORY, 32);
    private static final SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

    {
        ringBuffer.addGatingSequences(new NoOpEventProcessor(ringBuffer).getSequence());
    }

    public static final EventTranslatorTwoArg<String, Integer, String> TRANSLATOR =
            new EventTranslatorTwoArg<String, Integer, String>()
            {
                @Override
                public void translateTo(String event, long sequence, Integer arg0, String arg1)
                {
                    event = event.concat("2222").concat(arg1);
                }
            };

    private static final EventTranslatorOneArg<Node, Integer> transOne = (Node event, long sequence, Integer arg0) ->{
        event.val += arg0;
    };
    private static class ValueEntry{

    }
    public static void main(String[] args) throws InterruptedException, TimeoutException, AlertException {
//        assertEquals(SingleProducerSequencer.INITIAL_CURSOR_VALUE, ringBuffer.getCursor());
        System.out.println(ringBuffer.getCursor());
        String expectedEvent = String.valueOf(2071);
        ringBuffer.publishEvent(transOne,1);

        long sequence = sequenceBarrier.waitFor(0);
        System.out.println("sequence:"+sequence);

        Node event = ringBuffer.get(sequence);
        System.out.println("event:"+event.val);
        System.out.println(ringBuffer.getCursor());

    }

    public void crud(){

        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<String> factory = new EventFactory<String>() {
            @Override
            public String newInstance() {
                return new String("1");
            }
        };
        RingBuffer<String> ringBuffer = RingBuffer.createSingleProducer(factory, 16,new BlockingWaitStrategy());
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        AggregateEventHandler handler = new AggregateEventHandler();
        BatchEventProcessor<String> processor = new BatchEventProcessor<>(ringBuffer,sequenceBarrier,handler);

        ringBuffer.addGatingSequences(processor.getSequence());

        new Thread(processor).run();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (int i = 0; i < 10; i++) {
            bb.putLong(0,i);
            ringBuffer.publishEvent((event, seq, buffer) -> event.concat("111"),bb);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Node {
    int val;

    public Node(int val) {
        this.val = val;
    }
}