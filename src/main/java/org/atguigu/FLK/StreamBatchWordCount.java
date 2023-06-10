package org.atguigu.FLK;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class StreamBatchWordCount {
    public static void main(String[] args) throws Exception {
        // 1. 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置并行度
        env.setParallelism(1) ;

        //设置执行模式
        /*
            RuntimeExecutionMode:
              STREAMING: 默认的， 流处理。
              BATCH:  批处理， 需要显示的指定。
              AUTOMATIC: 自动选择。会按照数据源来自动判断，如果数据源是有界的，使用批处理， 如果数据源是无界的，使用流处理.
         */
        //env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        //env.setRuntimeMode(RuntimeExecutionMode.BATCH);
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);


        // 2.读取数据
        // DataStreamSource => DataStream
        DataStreamSource<String> ds = env.readTextFile("input/word_00.txt");

        // 3.转换处理
        // 3.1 切分数据， 处理成(word, 1)
        SingleOutputStreamOperator<Tuple2<String, Integer>> flatMapDs = ds.flatMap(
                new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String line, Collector<Tuple2<String, Integer>> out) throws Exception {
                        String[] words = line.split(" ");
                        for (String word : words) {
                            out.collect(Tuple2.of(word, 1));
                        }

                    }
                }
        );
        // 3.2 按照单词分组
        KeyedStream<Tuple2<String, Integer>, String> keyByDs = flatMapDs.keyBy(
                new KeySelector<Tuple2<String, Integer>, String>() {
                    @Override
                    public String getKey(Tuple2<String, Integer> value) throws Exception {
                        return value.f0; // 使用tuple中的第一个元素作为key
                    }
                }
        );
        // 3.3 汇总
        // 使用Tuple中的第二个元素进行汇总
        SingleOutputStreamOperator<Tuple2<String, Integer>> sumDs = keyByDs.sum(1);

        // 4. 输出结果
        sumDs.print();

        // 5. 启动执行
        env.execute();
    }
}
