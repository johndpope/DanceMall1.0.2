package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryTo;
import hzxmkuar.com.applibrary.domain.quote.MyQuoteTo;
import hzxmkuar.com.applibrary.domain.quote.QuoteDetailTo;
import hzxmkuar.com.applibrary.domain.quote.QuoteParam;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/25.
 */

public interface QuoteApi {

    /** 获取我都报价单
     */
    @POST("Api/Offer/mylist")
    Observable<MessageTo<MyQuoteTo>> getMyQuote(@Body MyInquiryParam param);

    /**
     * 报价
     */
    @POST("Api/Offer/offer_submit")
    Observable<MessageTo> quote(@Body QuoteParam param);


    /**
     * 报价单详情
     */
    @POST("Api/Offer/detail")
    Observable<MessageTo<QuoteDetailTo>> quoteDetail(@Body IdParam param);
    /**
     * 报价单详情
     */
    @POST("Api/Offer/abandon_busoffer")
    Observable<MessageTo> cancelQuote(@Body IdParam param);
}
