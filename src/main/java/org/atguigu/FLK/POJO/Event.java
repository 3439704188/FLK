package org.atguigu.FLK.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WEIYUNHUI
 * @date 2023/6/13 10:40
 *
 * 封装用户点击事件(点击数据)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String user ; // 用户
    private String url ; //点击的url
    private Long ts ; // 点击事件发生的时间
}
