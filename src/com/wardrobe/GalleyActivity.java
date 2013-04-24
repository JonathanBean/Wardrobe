package com.wardrobe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 图片库activity
 * 
 * @author Jonathan Guan
 * 
 */
public class GalleyActivity extends Activity {

	private GridView mGridView;
	private GalleyView gallery;
	private Button mbtnCamera;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		gallery = new GalleyView(this);
		final int galleryid = gallery.getGridview().getId();
		setContentView(gallery);

		mGridView = (GridView) findViewById(galleryid);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(GalleyActivity.this,
						"你选择了" + (position + 1) + " 号图片1", Toast.LENGTH_SHORT)
						.show();
				// 获取WindowManager，供后面浮动用
//				final WindowManager wm = (WindowManager) getApplicationContext()
//						.getSystemService(Context.WINDOW_SERVICE);
//				WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
//
//				wmParams.type = 2002;
//				wmParams.format = PixelFormat.TRANSLUCENT;//RGBA_8888; // 设置图片格式，效果为背景透明				
//				/**
//				 * 这里的flags也很关键 代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
//				 * 40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
//				 */
//				wmParams.flags = 40;
//				// //下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
//				// wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
//				// LayoutParams.FLAG_NOT_FOCUSABLE |
//				// LayoutParams.FLAG_NOT_TOUCHABLE;
//				wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//				wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//				// wmParams.gravity = Gravity.RIGHT| Gravity. CENTER_VERTICAL;
//				// // 调整悬浮窗口至右侧中间
//				wmParams.gravity = Gravity.CENTER_VERTICAL;
//
//				// 浮动预览框
//				ImageView iv = new ImageView(GalleyActivity.this);
//				
//				iv.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));			
//				iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//				Bitmap bm = BitmapFactory.decodeFile(
//						gallery.getFilePath(position),
//						new BitmapFactory.Options());
//				iv.setImageBitmap(bm);
//				iv.setOnClickListener(new ImageView.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						Toast.makeText(GalleyActivity.this,
//								"关闭图片预览：" + v.getId(), Toast.LENGTH_SHORT)
//								.show();
//						//移除浮动框
//						if (v.getParent() != null) {
//							wm.removeView(v);
//						}
//
//					}
//				});
//				// 将浮动预览框前置展现
//				if (iv.getParent() == null) { // 如果view没有被加入到某个父组件中，则加入WindowManager中
//					wm.addView(iv, wmParams);
//				}			
			}
		});

		mbtnCamera = (Button) findViewById(gallery.getBtnCamera().getId());
		mbtnCamera.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(GalleyActivity.this, MainActivity.class);
				startActivity(intent);
				GalleyActivity.this.finish();
			}
		});
	}
}
