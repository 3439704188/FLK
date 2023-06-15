package org.atguigu.FLK.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WEIYUNHUI
 * @date 2023/6/14 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppEvent {
    private String orderId ;
    private String eventType ;
    private Long ts ;
}
