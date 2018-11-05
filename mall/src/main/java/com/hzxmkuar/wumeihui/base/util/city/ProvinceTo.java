package com.hzxmkuar.wumeihui.base.util.city;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by xzz on 2018/8/23.
 */
@Data
public class ProvinceTo implements Serializable{
    private String provinceName;
    private List<CityTo>citys;
}
