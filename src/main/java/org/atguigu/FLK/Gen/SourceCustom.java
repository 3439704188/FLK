package org.atguigu.FLK.Gen;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.atguigu.FLK.POJO.Event;

public class SourceCustom {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final ClickEventSimulator clickEventSimulator = new ClickEventSimulator();
        // 有了自定义的source function，调用addSource方法
        DataStreamSource<Event> stream = env.addSource(clickEventSimulator);
        stream.print("SourceCustom");
        Thread thread = new Thread(() -> {
            try {
                env.execute();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        Thread.sleep(5000L);
        clickEventSimulator.cancel();
    }
}