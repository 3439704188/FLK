package org.atguigu.FLK;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;


public class ChainedWC {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment
                .getExecutionEnvironment()
                .readTextFile("input/word_00.txt")
                .flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (s, collector) -> {
                            String[] s1 = s.split(" ");
                            for (String s2 : s1) {
                                collector.collect(Tuple2.of(s2, 1));
                            }
                        }
                )
                .returns(Types.TUPLE(Types.STRING, Types.INT))
                .groupBy(0)
                .sum(1).print();
    }
}
