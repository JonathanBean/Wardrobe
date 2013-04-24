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
	// TODO 如果文件太多，可能有问题
	private File[] allFiles = new File(galleyPath).listFiles();

	public GalleyGridAdapter(Context ctx) {
		mContext = ctx;
	}

	@Override
	// 获取图片的个数
	public int getCount() {
		// TODO Auto-generated method stub
		return allFiles.length;
	}

	@Override
	// 获取图片在库中的位置
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	// 获取图片ID
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			// 给ImageView设置资源
			imageView = new ImageView(mContext);
			// 设置布局 图片120×120显示
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			// 设置显示比例类型
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		// 获取sd卡中照片展示出来
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
