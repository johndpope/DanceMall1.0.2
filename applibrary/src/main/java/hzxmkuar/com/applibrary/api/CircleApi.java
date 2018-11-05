package hzxmkuar.com.applibrary.api;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.domain.BaseParam;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.circle.AddPostParam;
import hzxmkuar.com.applibrary.domain.circle.CircleBannerTo;
import hzxmkuar.com.applibrary.domain.circle.CircleParam;
import hzxmkuar.com.applibrary.domain.circle.CircleTo;
import hzxmkuar.com.applibrary.domain.circle.CollectionTo;
import hzxmkuar.com.applibrary.domain.circle.CommentParam;
import hzxmkuar.com.applibrary.domain.circle.DaysParam;
import hzxmkuar.com.applibrary.domain.circle.PostDetailTo;
import hzxmkuar.com.applibrary.domain.circle.TopicTo;
import hzxmkuar.com.applibrary.domain.order.OrderSettleInfoTo;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/16.
 */

public interface CircleApi {
    /**
     *获取话题列表
     */
    @POST("Api/Topic/topic_list")
    Observable<MessageTo<TopicTo>> getTopicList(@Body BaseParam param);

    /**
     *发帖
     */
    @POST("Api/Topic/add_post")
    Observable<MessageTo> addPost(@Body AddPostParam param);

    /**
     *帖子列表
     */
    @POST("Api/Topic/post_list")
    Observable<MessageTo<CircleTo>> getPostList(@Body CircleParam param);

    /**
     *帖子详情
     */
    @POST("Api/Topic/post_detail")
    Observable<MessageTo<PostDetailTo>> getPostDetail(@Body IdParam param);

    /**
     *发布评论
     */
    @POST("Api/Topic/post_addcomment")
    Observable<MessageTo> comment(@Body CommentParam param);

    /**
     *点赞
     */
    @POST("Api/Topic/post_dolike")
    Observable<MessageTo> praise(@Body IdParam param);

    /**
     *
     */
    @POST("Api/Topic/index_top_menu")
    Observable<MessageTo<CircleBannerTo>> getBanner(@Body BaseParam param);

    /**
     * 我发布的帖子
     */

    @POST("Api/Topic/my_published_list")
    Observable<MessageTo<CircleTo>> getMyPost(@Body BaseParam param);

    /**
     * 我收藏的帖子
     */

    @POST("Api/Topic/my_collected_list")
    Observable<MessageTo<CircleTo>> getMyCollection(@Body BaseParam param);

    /**
     * 我最近看的帖子
     */

    @POST("Api/Topic/my_browse_list")
    Observable<MessageTo<CircleTo>> getMyLook(@Body BaseParam param);

    /**
     *收藏帖子
     */
    @POST("Api/Topic/post_docollect")
    Observable<MessageTo<CollectionTo>> collectPost(@Body IdParam param);

    /**
     *生成置顶帖子订单
     */
    @POST("Api/Topic/create_posttop_order2")
    Observable<MessageTo<OrderSettleInfoTo>> topOrder(@Body DaysParam param);
}
