package com.example.abdelrahmansaleh.mvpnewsapp.dailoge;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.abdelrahmansaleh.mvpnewsapp.R;
import com.example.abdelrahmansaleh.mvpnewsapp.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CustomErrorDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.dialogTvTittle)
    TextView dialogTvTittle;
    @BindView(R.id.dialogTvMessage)
    TextView dialogTvMessage;
    @BindView(R.id.dialogBtnCancel)
    Button dialogBtnCancel;
    private String tittle, message;
    Context context;

    public CustomErrorDialog(Context context, String tittle, String message) {
        super( context );
        this.context = context;
        this.tittle = tittle;
        this.message = message;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView( R.layout.error_dialog );
        ButterKnife.bind( this );
        dialogTvTittle.setText( tittle );
        dialogTvMessage.setText( message );
        dialogBtnCancel.setOnClickListener( this );
        setCancelable( false );
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == dialogBtnCancel.getId()) {
            dismiss();
            ((MainActivity) context).recreate();
        } else {
            setCancelable( false );
        }
    }
}
