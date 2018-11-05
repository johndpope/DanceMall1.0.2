package com.hzxmkuar.wumeihui.base.banner;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.hzxmkuar.wumeihui.R;
import com.hzxmkuar.wumeihui.base.util.GlideCircleTransform;
import com.hzxmkuar.wumeihui.personal.order.MerchantListActivity;

import java.util.List;

import hzxmkuar.com.applibrary.domain.main.MainBannerTo;


/**
 * Created by xzz on 2017/6/25.
 **/

public class BannerMenuView implements Holder<List<MainBannerTo.IndexMenuBean>> {

    private GridLayout gridLayout;

    @Override
    public View createView(Context context) {
        gridLayout = new GridLayout(context);

       gridLayout.setColumnCount(4);
        return gridLayout;
    }

    @Override
    public void UpdateUI(Context context, int position, List<MainBannerTo.IndexMenuBean> menuList ) {
        for (int i = 0; i < menuList.size(); i++) {
            MainBannerTo.IndexMenuBean menu = menuList.get(i);
            View mView = View.inflate(context, R.layout.menu_layout, null);
            ((TextView) mView.findViewById(R.id.menu_name)).setText(menu.getService_name());
            Glide.with(context).load(menu.getService_img()).into((ImageView) mView.findViewById(R.id.menu_image));

            gridLayout.addView(mView);
            mView.setOnClickListener(view -> {
                Intent intent = new Intent(context, MerchantListActivity.class);
                intent.putExtra("CateId", menu.getId());
               context. startActivity(intent);

            });
        }
    }
}
