package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.message.IdsParam;
import hzxmkuar.com.applibrary.domain.message.SystemMessageParam;
import hzxmkuar.com.applibrary.domain.message.SystemMessageTo;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/11.
 */

public interface MessageApi {

    /**
     *获取系统消息
     */
    @POST("Api/Message/msgList")
    Observable<MessageTo<SystemMessageTo>>getSystemMessageList(@Body SystemMessageParam param);

    /**
     *删除系统消息
     */
    @POST("Api/Message/delData")
    Observable<MessageTo>deleteMessage(@Body IdsParam param);
}
