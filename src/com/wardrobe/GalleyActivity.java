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
 * ͼƬ��activity
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
						"��ѡ����" + (position + 1) + " ��ͼƬ1", Toast.LENGTH_SHORT)
						.show();
				// ��ȡWindowManager�������渡����
//				final WindowManager wm = (WindowManager) getApplicationContext()
//						.getSystemService(Context.WINDOW_SERVICE);
//				WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
//
//				wmParams.type = 2002;
//				wmParams.format = PixelFormat.TRANSLUCENT;//RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��				
//				/**
//				 * �����flagsҲ�ܹؼ� ����ʵ����wmParams.flags |= FLAG_NOT_FOCUSABLE;
//				 * 40��������wmParams��Ĭ�����ԣ�32��+ FLAG_NOT_FOCUSABLE��8��
//				 */
//				wmParams.flags = 40;
//				// //�����flags���Ե�Ч����ͬ���������� ���������ɴ������������κ��¼�,ͬʱ��Ӱ�������¼���Ӧ��
//				// wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
//				// LayoutParams.FLAG_NOT_FOCUSABLE |
//				// LayoutParams.FLAG_NOT_TOUCHABLE;
//				wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//				wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//				// wmParams.gravity = Gravity.RIGHT| Gravity. CENTER_VERTICAL;
//				// // ���������������Ҳ��м�
//				wmParams.gravity = Gravity.CENTER_VERTICAL;
//
//				// ����Ԥ����
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
//								"�ر�ͼƬԤ����" + v.getId(), Toast.LENGTH_SHORT)
//								.show();
//						//�Ƴ�������
//						if (v.getParent() != null) {
//							wm.removeView(v);
//						}
//
//					}
//				});
//				// ������Ԥ����ǰ��չ��
//				if (iv.getParent() == null) { // ���viewû�б����뵽ĳ��������У������WindowManager��
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
