package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.inquery.AddContactParam;
import hzxmkuar.com.applibrary.domain.inquery.AddInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.ConfirmInquiryPageTo;
import hzxmkuar.com.applibrary.domain.inquery.ConfirmInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.ContactPeopleTo;
import hzxmkuar.com.applibrary.domain.inquery.DeleteContactParam;
import hzxmkuar.com.applibrary.domain.inquery.EditContactParam;
import hzxmkuar.com.applibrary.domain.inquery.InquiryInfoParam;
import hzxmkuar.com.applibrary.domain.inquery.InquiryInfoTo;
import hzxmkuar.com.applibrary.domain.inquery.InquiryListTo;
import hzxmkuar.com.applibrary.domain.inquery.InquiryMerchantParam;
import hzxmkuar.com.applibrary.domain.inquery.InquiryTo;
import hzxmkuar.com.applibrary.domain.inquery.KeyWordParam;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryParam;
import hzxmkuar.com.applibrary.domain.inquery.MyInquiryTo;
import hzxmkuar.com.applibrary.domain.inquery.ServiceDetailTo;
import hzxmkuar.com.applibrary.domain.main.MainMerchantTo;
import hzxmkuar.com.applibrary.main.DemandSearchTo;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/11.
 */

public interface InquiryApi {

    /**
     * 获取服务0列表
     */
    @POST("Api/Serviceproject/getServiceprojectTreeList")
    Observable<MessageTo<InquiryTo>> getInquiryList(@Body BaseParam param);

    /**
     * 获取联系人列表
     */
    @POST("Api/Address/index")
    Observable<MessageTo<ContactPeopleTo>> getcontactList(@Body BaseParam param);

    /**
     * 添加联系人
     */
    @POST("Api/Address/add")
    Observable<MessageTo> addContact(@Body AddContactParam param);

    /**
     * 删除联系人
     */
    @POST("Api/Address/delOne")
    Observable<MessageTo> deleteContact(@Body DeleteContactParam param);

    /**
     * 设置默认联系人
     */
    @POST("Api/Address/setDefault")
    Observable<MessageTo> setDefaultContact(@Body DeleteContactParam param);

    /**
     * 编辑联系人
     */
    @POST("Api/Address/edit")
    Observable<MessageTo> editContact(@Body EditContactParam param); /**
     * 编辑联系人
     */
    @POST("Api/Inquirysheet/demand_confirm")
    Observable<MessageTo> confirmInquiry(@Body ConfirmInquiryParam param);

    /** 需求确认页面展示
     */
    @POST("Api/Inquirysheet/inquiry_confirm")
    Observable<MessageTo<ConfirmInquiryPageTo>> confirmInquiryPage(@Body BaseParam param);

    /** 添加需求
     */
    @POST("Api/Inquirysheet/inquiry")
    Observable<MessageTo> addInquiry(@Body AddInquiryParam param);

    /** 获取我都询价单
     */
    @POST("Api/Inquirysheet/mylist")
    Observable<MessageTo<MyInquiryTo>> getMyInquiry(@Body MyInquiryParam param);

    /**
     * 获取询价单信息
     */
    @POST("Api/Inquirysheet/inquiry_detail")
    Observable<MessageTo<InquiryInfoTo>> getInquiryInfo(@Body InquiryInfoParam param);

    /**
     * 获取询价单商家
     */
    @POST("Api/Inquirysheet/inquiry_detail_bus_list")
    Observable<MessageTo<MainMerchantTo>> getInquiryMerchant(@Body InquiryMerchantParam param);

    /**
     * 获取服务详情
     */
    @POST("Api/Inquirysheet/inquiry_detail_service_list")
    Observable<MessageTo<ServiceDetailTo>> getServiceDetail(@Body IdParam param);
    /**
     * 搜索服务
     */
    @POST("Api/Serviceproject/getSearchList")
    Observable<MessageTo<DemandSearchTo>> getSearchList(@Body KeyWordParam param);

    /**
     * 放弃询价
     */
    @POST("Api/Inquirysheet/abandon_inquiry")
    Observable<MessageTo<DemandSearchTo>> cancelInquiry(@Body IdParam param);
}
