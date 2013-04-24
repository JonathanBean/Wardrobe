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
 * ������
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
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);//����ȫ��
		setContentView(R.layout.activity_main);
		InitCameraView();
	}

	@Override
	protected void onPause() {
		Log.d(TAG, "onPause");
		super.onPause();
		mCamera.lock();
		releaseCamera(); // ����ͣ�¼��������ͷ�����ͷ
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
	 * ��ʼ������view
	 */
	private void InitCameraView() {
		// ����Cameraʵ��
		mCamera = getCameraInstance();

		// ����Preview view��������Ϊactivity�е�����
		mPreview = new CameraPreview(this, mCamera);
		mPreview.setPadding(30, 30, 30, 30);

		// ��Capture��ť�м���listener
		Button captureButton = (Button) findViewById(R.id.btn_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ������ͷ��ȡͼƬ
				mCamera.takePicture(null, null, mPicture);
			}
		});

		// ��Galley��ť�м���listener
		Button galleyButton = (Button) findViewById(R.id.btn_galley);
		galleyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
				// ��ת��Galley����
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

	/** ����豸�Ƿ��ṩ����ͷ */
	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// ����ͷ����
			return true;
		} else {
			// ����ͷ������
			return false;
		}
	}

	/** ��ȫ��ȡCamera����ʵ���ķ��� */
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			// ��ͼ��ȡCameraʵ��
			if (versionCode <= 10) {
				c = Camera.open(); // [<v2.3]
			} else {
				c = Camera.open(CameraInfo.CAMERA_FACING_BACK); // [>=v2.3]
			}

		} catch (Exception e) {
			// ����ͷ�����ã�����ռ�û򲻴��ڣ�
			Log.d(TAG, "����ͷ�����ã�����ռ�û򲻴��ڣ�");
		}
		return c; // �������򷵻�null
	}

	/* ����ͼ�� */
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
	 * Ϊ����ͼƬ����Ƶ�����ļ�Uri
	 */
	private static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * Ϊ����ͼƬ����Ƶ����File
	 */
	private static File getOutputMediaFile(int type) {
		// ��ȫ�������ʹ��ǰӦ�ü��SD���Ƿ���װ��
		String envState = Environment.getExternalStorageState();
		if (envState == Environment.MEDIA_REMOVED) {
			Log.e(TAG, "û�в���SD�����޷�������Ƭ!");
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
		// �������ͼƬ��Ӧ�ó���ж�غ󻹴��ڡ����ܱ�����Ӧ�ó�����
		// ��˱���λ�������

		// ��������ڵĻ����򴴽��洢Ŀ¼
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG, "failed to create directory");
				return null;
			}
		}

		// ����ý���ļ���
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		Log.d(TAG, "getOutputMediaFile!Save Picture");
		return mediaFile;
	}

	/**
	 * �ͷ�����ͷ
	 */
	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release(); // Ϊ����Ӧ���ͷ�����ͷ
			mCamera = null;
			Log.d(TAG, "releaseCamera!");
		}
	}

	/**
	 * ���ص�ǰ����汾
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
