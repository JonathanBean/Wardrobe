package com.wardrobe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wardrobe.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 主程序
 * 
 * @author Jonathan Guan
 *
 */
public class MainActivity extends Activity {

	private static final String TAG = "WARDROBE";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;

	private Camera mCamera;
	private CameraPreview mPreview;
	private static int versionCode = getSDKVersionNumber();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
		setContentView(R.layout.activity_main);
		InitCameraView();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
		mCamera.lock();
		releaseCamera(); // 在暂停事件中立即释放摄像头
	}

	@Override
	protected void onRestart() {
		Log.d(TAG, "onRestart");
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG, "onResume");
		// TODO Auto-generated method stub
		super.onResume();
		mCamera.startPreview();
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "onStop");
		// TODO Auto-generated method stub
		super.onStop();
	}

	/**
	 * 初始化拍照view
	 */
	private void InitCameraView() {
		// 创建Camera实例
		mCamera = getCameraInstance();

		// 创建Preview view并将其设为activity中的内容
		mPreview = new CameraPreview(this, mCamera);
		mPreview.setPadding(30, 30, 30, 30);

		// 在Capture按钮中加入listener
		Button captureButton = (Button) findViewById(R.id.btn_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 从摄像头获取图片
				mCamera.takePicture(null, null, mPicture);
			}
		});

		// 在Galley按钮中加入listener
		Button galleyButton = (Button) findViewById(R.id.btn_galley);
		galleyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
				// 跳转到Galley界面
				Intent galleyIntent = new Intent();
				galleyIntent.setClass(MainActivity.this, GalleyActivity.class);
				Log.d(TAG, "galley Step1");
				startActivity(galleyIntent);
				Log.d(TAG, "galley Step2");
				MainActivity.this.finish();
				Log.d(TAG, "galley Step3");
				}catch(Exception e){
					Log.d(TAG, "galleyButton.onClick take a E.$"+e.getMessage());
				}
			}
		});
	}

	/** 检查设备是否提供摄像头 */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// 摄像头存在
			return true;
		} else {
			// 摄像头不存在
			return false;
		}
	}

	/** 安全获取Camera对象实例的方法 */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			// 试图获取Camera实例
			if (versionCode <= 10) {
				c = Camera.open(); // [<v2.3]
			} else {
				c = Camera.open(CameraInfo.CAMERA_FACING_BACK); // [>=v2.3]
			}

		} catch (Exception e) {
			// 摄像头不可用（正被占用或不存在）
			Log.d(TAG, "摄像头不可用（正被占用或不存在）");
		}
		return c; // 不可用则返回null
	}

	/* 捕获图像 */
	private PictureCallback mPicture = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {

			File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
			if (pictureFile == null) {
				Log.d(TAG,
						"Error creating media file, check storage permissions: ");
				return;
			}

			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				mCamera.startPreview();
				Log.d(TAG, "onPictureTaken Success,Yeah!");
			} catch (FileNotFoundException e) {
				Log.d(TAG, "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "Error accessing file: " + e.getMessage());
			} catch (Exception e) {
				Log.d(TAG, "PictureCallback Unknown E.:" + e.getMessage());
			}
		}
	};

	/**
	 * 为保存图片或视频创建文件Uri
	 */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * 为保存图片或视频创建File
	 */
	private static File getOutputMediaFile(int type) {
		// 安全起见，在使用前应该检查SD卡是否已装入
		String envState = Environment.getExternalStorageState();
		if (envState == Environment.MEDIA_REMOVED) {
			Log.e(TAG, "没有插入SD卡，无法保存照片!");
			return null;
		}

		String vEnv = Environment.getExternalStorageState();
		if (vEnv.equalsIgnoreCase("removed")) {
			Log.d(TAG, "Environment removed!");
			return null;
		}

		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(),
				"Pictures/Wardrobe");
		// 如果期望图片在应用程序卸载后还存在、且能被其它应用程序共享，
		// 则此保存位置最合适

		// 如果不存在的话，则创建存储目录
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG, "failed to create directory");
				return null;
			}
		}

		// 创建媒体文件名
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		Log.d(TAG, "getOutputMediaFile!Save Picture");
		return mediaFile;
	}

	/**
	 * 释放摄像头
	 */
	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release(); // 为其它应用释放摄像头
			mCamera = null;
			Log.d(TAG, "releaseCamera!");
		}
	}

	/**
	 * 返回当前程序版本
	 */
	public static int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}

}
