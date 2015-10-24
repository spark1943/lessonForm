package com.consultinggroup.login;

import com.consultinggroup.classtime.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MyDialog {
	private Context mContext;  
    private Dialog  mDialog;  
  
    public MyDialog(Context context)  
    {  
        mContext = context;  
    }  
  
    public Dialog loadDialog1()  
    {  
        mDialog = new Dialog(mContext, R.style.dialog);  
        LayoutInflater in = LayoutInflater.from(mContext);  
        View viewDialog = in.inflate(R.layout.dialog, null);  
        TextView textView=(TextView) viewDialog.findViewById(R.id.textView);
    	textView.setText("正在修改中");
        viewDialog.setBackgroundColor(0x7f000000);  
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        // 这里可以设置dialog的大小，当然也可以设置dialog title等  
        // LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);  
        // mDialog.setContentView(viewDialog, layoutParams);  
        mDialog.setContentView(viewDialog);  
        mDialog.setCanceledOnTouchOutside(false);  
        mDialog.show();  
        return mDialog;  
    }  
    public Dialog loadDialog2()  
    {  
    	mDialog = new Dialog(mContext, R.style.dialog1);  
    	LayoutInflater in = LayoutInflater.from(mContext);  
    	View viewDialog = in.inflate(R.layout.dialog, null);  
    	TextView textView=(TextView) viewDialog.findViewById(R.id.textView);
    	textView.setText("正在更改头像");
    	viewDialog.setBackgroundColor(0x7f000000);  
    	mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    	// 这里可以设置dialog的大小，当然也可以设置dialog title等  
    	// LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);  
    	// mDialog.setContentView(viewDialog, layoutParams);  
    	mDialog.setContentView(viewDialog);  
    	mDialog.setCanceledOnTouchOutside(false);  
    	mDialog.show();  
    	return mDialog;  
    }  
    public Dialog loadDialog3()  
    {  
    	mDialog = new Dialog(mContext, R.style.dialog1);  
    	LayoutInflater in = LayoutInflater.from(mContext);  
    	View viewDialog = in.inflate(R.layout.dialog, null);  
    	TextView textView=(TextView) viewDialog.findViewById(R.id.textView);
    	textView.setText("亲，正在加载中");
    	viewDialog.setBackgroundColor(0x7f000000);  
    	mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    	// 这里可以设置dialog的大小，当然也可以设置dialog title等  
    	// LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);  
    	// mDialog.setContentView(viewDialog, layoutParams);  
    	mDialog.setContentView(viewDialog);  
    	mDialog.setCanceledOnTouchOutside(true);  
    	mDialog.show();  
    	return mDialog;  
    }  
    public Dialog loadplace()  
    {  
    	mDialog = new Dialog(mContext, R.style.dialog1);  
    	LayoutInflater in = LayoutInflater.from(mContext);  
    	View viewDialog = in.inflate(R.layout.dialog, null);  
    	TextView textView=(TextView) viewDialog.findViewById(R.id.textView);
    	textView.setText("亲，正在定位中");
    	viewDialog.setBackgroundColor(0x7f000000);  
    	mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    	// 这里可以设置dialog的大小，当然也可以设置dialog title等  
    	// LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);  
    	// mDialog.setContentView(viewDialog, layoutParams);  
    	mDialog.setContentView(viewDialog);  
    	mDialog.setCanceledOnTouchOutside(true);  
    	mDialog.show();  
    	return mDialog;  
    }  
    public Dialog loadLesson()  
    {  
    	mDialog = new Dialog(mContext, R.style.dialog1);  
    	LayoutInflater in = LayoutInflater.from(mContext);  
    	View viewDialog = in.inflate(R.layout.dialog, null);  
    	TextView textView=(TextView) viewDialog.findViewById(R.id.textView);
    	textView.setText("正在载入中");
    	viewDialog.setBackgroundColor(0x7f000000);  
    	mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    	// 这里可以设置dialog的大小，当然也可以设置dialog title等  
    	// LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);  
    	// mDialog.setContentView(viewDialog, layoutParams);  
    	mDialog.setContentView(viewDialog);  
    	mDialog.setCanceledOnTouchOutside(true);  
    	mDialog.show();  
    	return mDialog;  
    }  
    public Dialog loadupload()  
    {  
    	mDialog = new Dialog(mContext, R.style.dialog1);  
    	LayoutInflater in = LayoutInflater.from(mContext);  
    	View viewDialog = in.inflate(R.layout.dialog, null);  
    	TextView textView=(TextView) viewDialog.findViewById(R.id.textView);
    	textView.setText("正在发表中");
    	viewDialog.setBackgroundColor(0x7f000000);  
    	mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    	// 这里可以设置dialog的大小，当然也可以设置dialog title等  
    	// LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);  
    	// mDialog.setContentView(viewDialog, layoutParams);  
    	mDialog.setContentView(viewDialog);  
    	mDialog.setCanceledOnTouchOutside(true);  
    	mDialog.show();  
    	return mDialog;  
    }  
    public Dialog loaduploadFile()  
    {  
    	mDialog = new Dialog(mContext, R.style.dialog1);  
    	LayoutInflater in = LayoutInflater.from(mContext);  
    	View viewDialog = in.inflate(R.layout.dialog, null);  
    	TextView textView=(TextView) viewDialog.findViewById(R.id.textView);
    	textView.setText("正在上传");
    	viewDialog.setBackgroundColor(0x7f000000);  
    	mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    	// 这里可以设置dialog的大小，当然也可以设置dialog title等  
    	// LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);  
    	// mDialog.setContentView(viewDialog, layoutParams);  
    	mDialog.setContentView(viewDialog);  
    	mDialog.setCanceledOnTouchOutside(true);  
    	mDialog.show();  
    	return mDialog;  
    }  
  
    public void removeDialog()  
    {  
        mDialog.dismiss();  
    }  
    
}
