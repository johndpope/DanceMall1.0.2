package hzxmkuar.com.applibrary.domain.inquery;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/11.
 */
@Data
public class InquiryListTo implements Serializable {
    private String id;
    private String service_img;
    private String service_name;
    private int level;
    private boolean select;
    private List<InquiryListTo> list2;
    private List<InquiryListTo> list3;
    private int selectType;
    private String areaInquiry;
}
