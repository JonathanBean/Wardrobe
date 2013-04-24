package com.wardrobe;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** ����������ͷԤ���� */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "CameraPreview";
	private SurfaceHolder mHolder;
	private Camera mCamera;

	public CameraPreview(Context context, Camera camera) {
		super(context);		
		this.mCamera = camera;		

		// ��װһ��SurfaceHolder.Callback��
		// �������������ٵײ�surfaceʱ�ܹ����֪ͨ��
		mHolder = getHolder();
		mHolder.addCallback(this);
		// �ѹ��ڵ����ã����汾����3.0��Android����Ҫ
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// surface�ѱ����������ڰ�Ԥ�������λ��֪ͨ����ͷ
		try {
			mCamera.setPreviewDisplay(holder);
			//mCamera.startPreview();
		} catch (IOException e) {
			mCamera.release();
			mCamera = null;
			Log.d(TAG, "Error setting camera preview: " + e.getMessage());
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// �մ��롣ע����activity���ͷ�����ͷԤ������
		if (mCamera != null) {
			//mCamera.lock();
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			mCamera.release(); // Ϊ����Ӧ���ͷ�����ͷ
			mCamera = null;
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// ϵͳ���Զ����ø÷���
		// ���Ԥ���޷����Ļ���ת��ע��˴����¼�
		// ȷ�������Ż�����ʱֹͣԤ��

		if (mHolder.getSurface() == null) {
			// Ԥ��surface������
			Log.d(TAG, "CameraPreview.surfaceChanged():Ԥ��surface������!");
			return;
		}

		// ����ʱֹͣԤ��
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ���ԣ���ͼֹͣ�����ڵ�Ԥ��
		}

		// �ڴ˽������š���ת��������֯��ʽ
		try{
	        Camera.Parameters parameters = mCamera.getParameters();
	        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
	        Camera.Size previewSize = previewSizes.get(0);
	        parameters.setPreviewSize(previewSize.width, previewSize.height);
	        List<Camera.Size> picSizes = parameters.getSupportedPictureSizes();
	        Camera.Size picSize = picSizes.get(0);
	        parameters.setPictureSize(picSize.width, picSize.height);
	        mCamera.setParameters(parameters);
		} catch (Exception e) {
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}

		// ���µ���������Ԥ��
		try {
			//mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (Exception e) {
			Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
	}
	
	@Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}