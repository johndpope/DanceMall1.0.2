package com.hzxmkuar.wumeihui.circle.presenter;

import com.hzxmkuar.wumeihui.base.BaseActivity;
import com.hzxmkuar.wumeihui.base.BasePresenter;
import com.hzxmkuar.wumeihui.base.MyObserver;
import com.hzxmkuar.wumeihui.circle.PostActivity;
import com.hzxmkuar.wumeihui.circle.PostDetailActivity;

import hzxmkuar.com.applibrary.IdParam;
import hzxmkuar.com.applibrary.api.ApiClient;
import hzxmkuar.com.applibrary.api.CircleApi;
import hzxmkuar.com.applibrary.domain.MessageTo;
import hzxmkuar.com.applibrary.domain.circle.CollectionTo;
import hzxmkuar.com.applibrary.domain.circle.CommentParam;
import hzxmkuar.com.applibrary.domain.circle.PostDetailTo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/9/19.
 */

public class PostDetailPresenter extends BasePresenter {

    public PostDetailPresenter(BaseActivity activity){
        initContext(activity);
        getPostDetail();
    }

    public void getPostDetail(){
        IdParam param=new IdParam();
        param.setId(activity.getIntent().getIntExtra("PostId",0));
        param.setHash(getHashString(IdParam.class,param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).getPostDetail(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<PostDetailTo>>(this) {
                    @Override
                    public void onNext(MessageTo<PostDetailTo> msg) {
                        if (msg.getCode()==0){
                         getDataSuccess(msg.getData());
                        }
                    }
                }
        );
    }

    public void addComment(String content,int commentId){
        CommentParam param=new CommentParam();
        param.setId(activity.getIntent().getIntExtra("PostId",0));
        param.setComment_id(commentId);
        param.setContent(content);
        param.setHash(getHashString(CommentParam.class,param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).comment(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        if (msg.getCode()==0) {
                            getPostDetail();
                            ((PostDetailActivity)activity).commentSuccess();
                        }
                    }
                }
        );
    }

    public void praise(){
        IdParam  param=new IdParam();
        param.setId(activity.getIntent().getIntExtra("PostId",0));
        param.setHash(getHashString(IdParam.class,param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).praise(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo>(this) {
                    @Override
                    public void onNext(MessageTo msg) {
                        showMessage("点赞成功");
                        ((PostDetailActivity)activity).praiseSuccess();
                    }
                }
        );
    }

    public void collectPost(){
        IdParam param=new IdParam();
        param.setId(activity.getIntent().getIntExtra("PostId",0));
        param.setHash(getHashString(IdParam.class,param));
        showLoadingDialog();
        ApiClient.create(CircleApi.class).collectPost(param).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(
                new MyObserver<MessageTo<CollectionTo>>(this) {
                    @Override
                    public void onNext(MessageTo<CollectionTo> msg) {
                        if (msg.getCode()==0){
                            ((PostDetailActivity)activity).setCollection(msg.getData());

                        }
                    }
                }
        );

    }

}
