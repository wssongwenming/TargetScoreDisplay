package com.huasun.core.util.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huasun.core.R;


/**
 * Created by shikun on 18-6-18.
 */

public class PromptDialog extends Dialog {
    private Context context;
    private String msg;
    private String confirmButtonText;
    private String cacelButtonText;
    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        public void doConfirm();
    }

    public PromptDialog(Context context, String msg, String confirmButtonText) {
        super(context);
        this.context = context;
        this.msg = msg;
        this.confirmButtonText = confirmButtonText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_prompt_dialog, null);
        setContentView(view);

        TextView tvMsg = (TextView) view.findViewById(R.id.msg);
        TextView tvConfirm = (TextView) view.findViewById(R.id.confirm);

        tvMsg.setText(msg);
        tvConfirm.setText(confirmButtonText);

        tvConfirm.setOnClickListener(new PromptDialog.clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(PromptDialog.ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            if (id == R.id.confirm) {
                clickListenerInterface.doConfirm();

            }
        }

    };

}
