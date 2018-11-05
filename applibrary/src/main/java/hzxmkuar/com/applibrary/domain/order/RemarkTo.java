package hzxmkuar.com.applibrary.domain.order;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2018/10/16.
 */
@Data
public class RemarkTo implements Serializable{
    private InvoiceTo invoice;

    @Data
    public static class InvoiceTo implements Serializable {
        private int type;
        private String invoice_title;
        private String taxpayer_no;

    }

    private String note;
}
