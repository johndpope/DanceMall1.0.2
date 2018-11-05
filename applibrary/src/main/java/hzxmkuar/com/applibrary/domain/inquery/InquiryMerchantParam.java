package hzxmkuar.com.applibrary.domain.inquery;

import hzxmkuar.com.applibrary.domain.BaseParam;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/11.
 */
@Data
public class InquiryMerchantParam extends BaseParam{

    private int id;
    private int sort_type;
    private int business_type;
    private String ratings_range="";
    private String onum_range="";
    private String area_ids="";
    private int pos_city;

}
