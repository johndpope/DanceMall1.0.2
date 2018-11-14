package hzxmkuar.com.applibrary.domain.main;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/10/9.
 */
@Data
public class MerchantParam extends BaseParam{
    private String keyword="";
    private int page;
    private int service_cate;
    private int sort_type=1;
    private int business_type;
    private int pos_city;
    private String ratings_range="";
    private String onum_range="";
    private String area_ids="";
    private String sids="";
}
