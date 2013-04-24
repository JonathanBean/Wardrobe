package com.wardrobe;

import com.wardrobe.R;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

/**
 * ͼƬ����ͼ
 * 
 * @author Jonathan Guan
 * 
 */
public class GalleyView extends LinearLayout {

	private GridView gridview;
	private Button btnCamera;
	private GalleyGridAdapter mGridAdapter;

	public GalleyView(Context context) {
		super(context);

		LinearLayout.LayoutParams mLayout = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		this.setLayoutParams(mLayout);
		// this.setBackgroundColor(android.graphics.Color.GRAY);
		this.setOrientation(LinearLayout.VERTICAL);

		// GridView��������
		LinearLayout.LayoutParams mGridParams = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		// gridview = new GridView(context);
		gridview = (GridView) findViewById(R.id.gridview);

		gridview.setId(1);
		gridview.setColumnWidth(75);
		gridview.setNumColumns(4);
		gridview.setHorizontalSpacing(4);
		gridview.setVerticalSpacing(2);
		mGridAdapter = new GalleyGridAdapter(context);
		gridview.setAdapter(mGridAdapter);
		// ����Gallery�ı���
		gridview.setBackgroundResource(R.drawable.bg0);
		// ��GridView��ӵ��ò�����
		addView(gridview, mGridParams);

		// ���尴ť����������ͷ����
		btnCamera = new Button(context);
		btnCamera.setId(2);
		btnCamera.setPadding(8, 6, 6, 10);
		btnCamera.setTextSize(26);
		btnCamera.setTextColor(Color.WHITE);
		btnCamera.setText("<<");
		btnCamera.setBackgroundColor(Color.BLACK);
		// ���ļ�����ӵ�������
		LinearLayout.LayoutParams mBtnParams = new LinearLayout.LayoutParams(
				100, 80);
		addView(btnCamera, mBtnParams);

	}

	public GridView getGridview() {
		return gridview;
	}

	public void setGridview(GridView gridview) {
		this.gridview = gridview;
	}

	public Button getBtnCamera() {
		return btnCamera;
	}

	public void setBtnCamera(Button btnCamera) {
		this.btnCamera = btnCamera;
	}

}
