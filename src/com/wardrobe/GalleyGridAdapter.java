package com.wardrobe;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GalleyGridAdapter extends BaseAdapter {

	private Context mContext;
	private String galleyPath = "/mnt/sdcard/Pictures/Wardrobe/";
	// TODO ����ļ�̫�࣬����������
	private File[] allFiles = new File(galleyPath).listFiles();

	public GalleyGridAdapter(Context ctx) {
		mContext = ctx;
	}

	@Override
	// ��ȡͼƬ�ĸ���
	public int getCount() {
		// TODO Auto-generated method stub
		return allFiles.length;
	}

	@Override
	// ��ȡͼƬ�ڿ��е�λ��
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	// ��ȡͼƬID
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			// ��ImageView������Դ
			imageView = new ImageView(mContext);
			// ���ò��� ͼƬ120��120��ʾ
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			// ������ʾ��������
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		// ��ȡsd������Ƭչʾ����
		File imgfile = allFiles[position];
		if (imgfile != null) {
			Log.d(this.getClass().getName() + "|step2",
					imgfile.getAbsolutePath());
			// String img =
			// "/mnt/sdcard/Pictures/Wardrobe/IMG_20130108_031555.jpg";
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;
			Bitmap bm = BitmapFactory.decodeFile(imgfile.getAbsolutePath(),
					options);
			imageView.setImageBitmap(bm);
		}
		return imageView;
	}

	public String getFilePath(int position) {
		File imgfile = allFiles[position];
		if (imgfile != null) {
			return imgfile.getAbsolutePath();
		} else {
			return null;
		}
	}
}
