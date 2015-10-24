package com.consultinggroup.main;

import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.consultinggroup.classtime.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ScheduleView extends View implements OnTouchListener {

	private Paint mPaint; // 画笔,包含了画几何图形、文本等的样式和颜色信息
	private int startX = 0;//画布的原点X（所有的画图操作，都是基于这个原点的，touch中只要修改这个值）
	private int startY = 0;//画布的原点Y（所有的画图操作，都是基于这个原点的，touch中只要修改这个值）
	private static  int sidewidte = 75;//上面bar的高度
	private static  int leftwidth=90;//左边bar的宽度
	private static int eachBoxH = 120;//每个格子的高度
	private static int eachBoxW = 120;//每个格子的宽度，后面根据屏幕对它做了均分
	private int focusX = -1;//当前手指焦点的位置坐标
	private int focusY = -1;//当前手指焦点的位置坐标
	private static int classTotal = 13;//左边栏总格子数
	private static int dayTotal = 7;//顶部栏总共格子数
	private String[] weekdays;//星期
	private String[] time;//每节课的时间
	private boolean isMove = false; // 判断是否移动
	// 监听器
	private OnItemClassClickListener onItemClassClickListener;
	// 数据
	private List<ClassInfo> classList;
	// 颜色
	public static final int contentBg = Color.argb(255, 255, 255, 255);
	public static final int barBg = Color.argb(255, 225, 225, 225);
	public static final int bayText = Color.argb(255, 150, 150, 150);
	public static final int barBgHrLine = Color.argb(255, 150, 150, 150);
	public static final int classBorder = Color.argb(180, 150, 150, 150);
	public static final int markerBorder = Color.argb(100, 150, 150, 150);
	//预设格子背景颜色数组
	public static final int[] classBgColors = { Color.argb(200, 71, 154, 199),
			Color.argb(200, 230, 91, 62), Color.argb(200, 50, 178, 93),
			Color.argb(200, 255, 225, 0), Color.argb(200, 102, 204, 204),
			Color.argb(200, 51, 102, 153), Color.argb(200, 102, 153, 204)

	};
	
	public ScheduleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		weekdays = context.getResources().getStringArray(R.array.weekdays);
		time=context.getResources().getStringArray(R.array.time);
		mPaint = new Paint();
		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		leftwidth=getWidth()/8;
		sidewidte=getHeight()/14;
		eachBoxW = (getWidth() - leftwidth) / 5;
		eachBoxH=(getHeight()-sidewidte)/10;
		printMarker(canvas);
		printContent(canvas);
		printTopBar(canvas);
		printLeftBar(canvas);
		
	}


	/** 
	 * @param canvas 画出表格
	 */
	private void printMarker(Canvas canvas) {
		mPaint.setColor(markerBorder);
//		for(int i=0;i<dayTotal;i++){
//			mPaint.setStyle(Style.STROKE);
//			canvas.drawRect(startX+sidewidte+eachBoxW*(i+1)-1,
//					startY+sidewidte,
//					startX+sidewidte+eachBoxW*(i+1),
//					startY+sidewidte+eachBoxH*classTotal
//					,mPaint);
//		}
		for(int i=0;i<classTotal;i++){
			mPaint.setStyle(Style.STROKE);
			canvas.drawRect(startX,
					startY+sidewidte+eachBoxH*(i+1)-1,
					startX+leftwidth+eachBoxW*dayTotal,
					startY+sidewidte+eachBoxH*(i+1),mPaint);
		}		
		}

	
	private void printContent(Canvas canvas) {
		if (classList != null && classList.size() > 0) {
			mPaint.setTextSize(40);
			System.out.println(getHeight()+"height");
			System.out.println(getWidth());
			mPaint.setTextAlign(Paint.Align.LEFT);
			ClassInfo classInfo;
			for (int i = 0; i < classList.size(); i++) {
				classInfo = classList.get(i);
				//写class的信息位置
				int fromX = startX + leftwidth + eachBoxW
						* (classInfo.getDay() - 1);
				int fromY = startY + sidewidte + eachBoxH
						* (classInfo.getBeginTime() - 1);
				int toX = startX + leftwidth + eachBoxW
						* classInfo.getDay();
				int toY = startY
						+ sidewidte
						+ eachBoxH
						* (classInfo.getBeginTime()
								+ classInfo.getClassLen() - 1);
				classInfo.setPoint(fromX, fromY, toX, toY);
				mPaint.setStyle(Style.FILL);
				mPaint.setColor(classBgColors[i % classBgColors.length]);
				canvas.drawRect(fromX, fromY, toX-1, toY-1, mPaint);
				mPaint.setColor(Color.WHITE);
				String className = classInfo.getLessonName() + "@"+classInfo.getAreaName()+","
						+ classInfo.getClassRoom();
				Rect textRect1 = new Rect();
				mPaint.getTextBounds(className, 0, className.length(),
						textRect1);
				float th = textRect1.bottom - textRect1.top;
				float tw = textRect1.right - textRect1.left;
				int row = (int) (tw/(eachBoxW*8/10))+1;
				int length = className.length()/row;
				if(length>=5){
					length=4;
					row+=1;
				}
				for (int j = 0; j < row-1; j++) {
					canvas.drawText(className, length * j, length * (j + 1),
							fromX+eachBoxW/10, fromY + eachBoxH/10 + th * (j + 1), mPaint);
				}
				canvas.drawText(className, length * (row - 1),
						className.length(), fromX +eachBoxW/10, fromY + eachBoxH/10 + th * row,
						mPaint);
			}
		}
	}

	
	private void printLeftBar(Canvas canvas) {
		//设置左边栏背景色
		mPaint.setColor(barBg);
		mPaint.setStyle(Style.FILL);
		//画出左边栏背景
		canvas.drawRect(0, startY + sidewidte, leftwidth, sidewidte + startY
				+ eachBoxH * classTotal, mPaint);
		//设置左边文字大小
		mPaint.setTextSize(40);
		//设置左边栏分割线以及文字颜色
		mPaint.setColor(barBgHrLine);
		
		//canvas.drawRect(0, startY + sidewidte + eachBoxH - 1, leftwidth, startY
		//		+ eachBoxH + sidewidte, mPaint);
		//文字存放区域
		Rect textRect1 = new Rect();
		
		mPaint.getTextBounds(time[0],0,time[0].length(),textRect1);
		//文字的高度
		int th=textRect1.bottom-textRect1.top;
		//时间区间的宽度
		int timeW=textRect1.right-textRect1.left;
		//时间区间距所在格上边线的距离
		int timeH=(eachBoxH-th*2-10)/2;
		//时间区间距所在格左边线的距离
		int timeL=(leftwidth-timeW)/2;
		//节次的宽度（一位数字）
		mPaint.getTextBounds("1", 0, 1, textRect1);
		int tw1 = textRect1.right - textRect1.left;
		
		//节次的宽度（两位数字）
		mPaint.getTextBounds("10", 0, 2, textRect1);
		int tw2 = textRect1.right - textRect1.left;
		
		for (int i = 1; i < classTotal+1; i++) {
			//画出每个格子的上边线
			canvas.drawRect(startX, startY + sidewidte + eachBoxH * (i-1) - 1,
					leftwidth, startY + eachBoxH * (i-1) + sidewidte, mPaint);
			canvas.drawText(time[i-1],timeL-3,startY+sidewidte+eachBoxH*(i-1)+timeH+th/2,mPaint);
			//写出节次的位置
			int tw = tw1 * 2 + (tw2 - tw1) * (i / 10);
			mPaint.setColor(Color.BLACK);
			canvas.drawText(i + "", leftwidth / 2 - tw / 2, startY + sidewidte
					+ eachBoxH * (i - 1) +th+20+timeH + th / 2, mPaint);
			mPaint.setColor(barBgHrLine);
		}
		mPaint.setColor(barBg);
		canvas.drawRect(0, 0, leftwidth, sidewidte, mPaint);
		//设置左上角下边线
		mPaint.setColor(barBgHrLine);
		canvas.drawRect(0,sidewidte-1,leftwidth,sidewidte,mPaint);
		//设置左上角右边线
		mPaint.setColor(Color.BLACK);
		canvas.drawRect(0+leftwidth-1,0,leftwidth,sidewidte,mPaint);
		
		
	}

	/**
	 * 
	 * @param canvas
	 */
	@SuppressLint("SimpleDateFormat")
	private void printTopBar(Canvas canvas) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Date current=new Date();
		List<Date> days=dateToWeek(current);
		
		mPaint.setColor(barBg);//设置星期的背景色
		mPaint.setStyle(Style.FILL);//填充星期
		canvas.drawRect(startX + leftwidth, 0, leftwidth + startX + eachBoxW
				* dayTotal, sidewidte, mPaint);//画出星期的bar
		mPaint.setColor(barBgHrLine);//分割线颜色
		//画出星期下的分割线
		canvas.drawRect(startX, sidewidte-1,startX+eachBoxW*dayTotal+leftwidth,sidewidte,mPaint);
		//设置文字以及垂直分割线的颜色
		mPaint.setColor(Color.BLACK);
		//设置文字大小
		mPaint.setTextSize(40);
		//画出文字
		Rect textBounds = new Rect();
		mPaint.getTextBounds(weekdays[0], 0, weekdays[0].length(), textBounds);
		//获取文字的高度
		int textHeight = textBounds.bottom - textBounds.top;
		//获取文字的宽度
		int textWidth = textBounds.right - textBounds.left;
		try {
			
		mPaint.getTextBounds(sdf.format(days.get(0)).toString(),0,sdf.format(days.get(0)).toString().length(),textBounds);
		
		int timeWidth=textBounds.right-textBounds.left;
		//画出星期文字以及每个文字后的分隔线
		for (int i = 0; i < dayTotal; i++) {
			canvas.drawRect(startX + leftwidth + eachBoxW * i - 1, 0, startX
					+ eachBoxW * i + leftwidth, sidewidte, mPaint);
			canvas.drawText(weekdays[i], startX + leftwidth + eachBoxW
					* i + eachBoxW / 2 - textWidth / 2, sidewidte / 2
					- textHeight/2+15, mPaint);
			mPaint.setColor(barBgHrLine);
			canvas.drawText(sdf.format(days.get(i)),startX + leftwidth + eachBoxW
					* i + (eachBoxW - timeWidth)/ 2,sidewidte / 2
					+textHeight/2+25, mPaint);
			mPaint.setColor(Color.BLACK);
		}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			focusX = (int) event.getX();
			focusY = (int) event.getY();
			isMove = false;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			int dx = (int) (event.getX() - focusX);
			int dy = (int) (event.getY() - focusY);
			if (!isMove && Math.abs(dx) < 5 && Math.abs(dy) < 5) {
				isMove = false;
				return false;
			}
			isMove = true;
			if (startX + dx < 0
					&& startX + dx + eachBoxW * dayTotal + leftwidth >= getWidth()) {
				startX += dx;
			}
			if (startY + dy < 0
					&& startY + dy + eachBoxH * classTotal + sidewidte >= getHeight()) {
				startY += dy;
			}
			focusX = (int) event.getX();
			focusY = (int) event.getY();
			invalidate();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (!isMove) {
				int focusX = (int) event.getX();
				int focusY = (int) event.getY();
				for (int i = 0; i < classList.size(); i++) {
					ClassInfo classInfo = classList.get(i);
					if (focusX > classInfo.getFromX()
							&& focusX < classInfo.getToX()
							&& focusY > classInfo.getFromY()
							&& focusY < classInfo.getToY()) {
						if (onItemClassClickListener != null) {
							onItemClassClickListener.onClick(classInfo);
						}
						break;
					}
				}
			}
		}
		return true;
	}

	public interface OnItemClassClickListener {
		public void onClick(ClassInfo classInfo);
	}

	public OnItemClassClickListener getOnItemClassClickListener() {
		return onItemClassClickListener;
	}

	public void setOnItemClassClickListener(
			OnItemClassClickListener onItemClassClickListener) {
		this.onItemClassClickListener = onItemClassClickListener;
	}

	public List<ClassInfo> getClassList() {
		return classList;
	}

	public void setClassList(List<ClassInfo> classList) {
		this.classList = classList;
		invalidate();
	}

	//获取当前周的日期
	public List<Date> dateToWeek(Date mdate) {
        int b =Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a-1, fdate);
        }
        return list;
    }
}
