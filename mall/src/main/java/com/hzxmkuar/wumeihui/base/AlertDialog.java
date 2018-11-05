package com.hzxmkuar.wumeihui.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.hzxmkuar.wumeihui.R;


/**
 * Created by xzz on 2017/6/17.
 **/

public  class AlertDialog {

    private static Button cancel;
    private static NiftyDialogBuilder dialogBuilder;

    public static Button show(Context context, String title, String confirmContent, String cancelContent){

        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.setContentView(R.layout.dialog_alert);
        Button confirm=  dialogBuilder.findViewById(R.id.confirm);
        cancel = dialogBuilder.findViewById(R.id.cancel);
        TextView tipText= dialogBuilder.findViewById(R.id.title);
        confirm.setText(confirmContent);
        cancel.setText(cancelContent);
        tipText.setText(title);
        dialogBuilder.show();
        cancel.setOnClickListener(v -> dialogBuilder.dismiss());

        dialogBuilder.findViewById(R.id.parent).setOnClickListener(v -> dialogBuilder.dismiss());

        return confirm;
    }
    public static Button show(Context context, String title){

        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.setContentView(R.layout.dialog_alert);
        Button confirm=  dialogBuilder.findViewById(R.id.confirm);
        cancel = dialogBuilder.findViewById(R.id.cancel);
        TextView tipText= dialogBuilder.findViewById(R.id.title);

        tipText.setText(title);
        dialogBuilder.show();
        cancel.setOnClickListener(v -> dialogBuilder.dismiss());

        dialogBuilder.findViewById(R.id.parent).setOnClickListener(v -> dialogBuilder.dismiss());

        return confirm;
    }

    public static Button show(Context context, String title, View view){

        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.setContentView(R.layout.dialog_alert);
        Button confirm=  dialogBuilder.findViewById(R.id.confirm);
        cancel = dialogBuilder.findViewById(R.id.cancel);
        TextView tipText= dialogBuilder.findViewById(R.id.title);

        tipText.setText(title);
        dialogBuilder.show();
        view.setEnabled(true);
        cancel.setOnClickListener(v -> dialogBuilder.dismiss());

        dialogBuilder.findViewById(R.id.parent).setOnClickListener(v -> dialogBuilder.dismiss());

        return confirm;
    }

    public static Button show(Context context){

        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        dialogBuilder.setContentView(R.layout.dialog_alert);
        Button confirm=  dialogBuilder.findViewById(R.id.confirm);
        cancel = dialogBuilder.findViewById(R.id.cancel);


        dialogBuilder.show();
        cancel.setOnClickListener(v -> dialogBuilder.dismiss());

        dialogBuilder.findViewById(R.id.parent).setOnClickListener(v -> dialogBuilder.dismiss());

        return confirm;
    }
public static Button getCancelClick(){
    return cancel;
}

public static void dismiss(){
    dialogBuilder.dismiss();
}
}
