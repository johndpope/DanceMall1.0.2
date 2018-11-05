
package com.hzxmkuar.wumeihui.base;

import lombok.Data;

/**
 * Created by xzz on 2018/5/3.
 */

@Data
public class Event<T> {

    private String type;
    private T data;

    public Event(String type){
        this.type=type;
    }
    public Event(String type, T data){
        this.type=type;
        this.data=data;
    }

}
