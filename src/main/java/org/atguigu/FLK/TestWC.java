package org.atguigu.FLK;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.operators.UnsortedGrouping;
import org.apache.flink.api.java.tuple.Tuple2;

public class TestWC {
    public static void main(String[] args) throws Exception {
        //创环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        //读文件
        DataSource<String> input = environment.readTextFile("input/word_00.txt");
        //切单词
        FlatMapOperator<String, Tuple2<String, Integer>> returns = input.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (s, collector) -> {
                    String[] s1 = s.split(" ");
                    for (String s2 : s1) {
                        collector.collect(Tuple2.of(s2,1));
                    }
                }
        ).returns(Types.TUPLE(Types.STRING, Types.INT));
        //分组
        UnsortedGrouping<Tuple2<String, Integer>> tuple2UnsortedGrouping = returns.groupBy(0);
        //聚合
        AggregateOperator<Tuple2<String, Integer>> sum = tuple2UnsortedGrouping.sum(1);
        //打印
        sum.print();
    }
}
