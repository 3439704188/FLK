package org.atguigu.FLK.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WEIYUNHUI
 * @date 2023/6/10 14:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wordcount {
    private String word ;
    private Integer count ;

    @Override
    public String toString() {
        return "(" +
                "word='" + word + '\'' +
                ", count=" + count +
                ')';
    }
}
