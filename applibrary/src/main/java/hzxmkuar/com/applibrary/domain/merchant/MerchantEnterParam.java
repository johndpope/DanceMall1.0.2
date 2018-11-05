package hzxmkuar.com.applibrary.domain.merchant;

import java.io.Serializable;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/20.
 */
@Data
public class MerchantEnterParam extends BaseParam implements Serializable{
    private int shop_logo;
    private int shop_banner;
    private String aptitude_license;
    private int business_type;
    private String shop_name;
    private String main_service;
    private String case_example;
    private String aptitude_name;
    private String address;
    private String detail_address;

}
