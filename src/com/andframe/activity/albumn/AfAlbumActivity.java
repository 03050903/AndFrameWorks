package com.andframe.activity.albumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.andframe.activity.AfActivity;
import com.andframe.activity.framework.AfViewable;
import com.andframe.bean.Page;
import com.andframe.exception.AfToastException;
import com.andframe.feature.AfIntent;
import com.andframe.model.Photo;
import com.andframe.thread.AfHandlerTask;
import com.andframe.thread.AfHandlerTimerTask;
import com.andframe.util.UUIDUtil;
import com.andframe.util.java.AfStringUtil;

/**
 * ���������ҳ��
 * @author SCWANG
 *	֧�� 	������Ƭ���
 *		�������
 *		��̬����������
 *
 *	�̳�֮����Ҫʵ��
 *		
	 * 	��ȡ�����ܲ���
	protected abstract int getAlbumLayoutId();
	 * ��ȡ ��ʾ������� TextView
	protected abstract TextView getTextViewName(AfViewable view);
	 * ��ȡ ��ʾ��������͵�ǰ�� TextView
	protected abstract TextView getTextViewSize(AfViewable view);
	 * ��ȡ ��ʾ�����Ƭ��ϸ��Ϣ TextView
	protected abstract TextView getTextViewDetail(AfViewable view);
	 * ��ȡ��Ử�� ViewPager
	protected abstract ViewPager getViewPager(AfViewable view);
	
	
	 * ��д ������� ����ʵ�ּ����������
	public List<Photo> onRequestAlbum(UUID albumID, Page page)
 */
public abstract class AfAlbumActivity extends AfActivity 
		implements OnPageChangeListener, OnTouchListener {

	// ͨ����Ϣ
	public static final String EXTRA_STRING_NAME = "EXTRA_NAME";
	public static final String EXTRA_STRING_DESCRIBE = "EXTRA_DESCRIBE";
	
	// ����һ���Ѵ��ڵ�����б���������������б�
	public static final String EXTRA_PHOTO_LIST = "EXTRA_LIST"; // �б�
	public static final String EXTRA_INT_INDEX = "EXTRA_INDEX";// Ĭ�ϲ鿴
	// ����һ�����ķ��棨�������������ݣ�
	public static final String EXTRA_UUID_ALBUMID = "EXTRA_ALBUMID";
	public static final String EXTRA_HEADURL = "EXTRA_HEADURL";
	// ����һ�ŵ�����ͼƬ��������ظ���ͼ��
	// ͼƬURL�����ڼ��ظ���ͼ��
	public static final String EXTRA_PHOTO_HEAD = "EXTRA_SINGLE_URL";

	public TextView mTvDetail = null;
	public TextView mTvName = null;
	public TextView mTvSize = null;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	private UUID AlbumID = null;
	private String mPhotoName = "�������";
	private String mDescribe = "���Ļ���������Ϣ���������";
	private String mHeadUrl = "";

	private Photo mHeader = null;
	private List<Photo> mltPhoto = new ArrayList<Photo>();

	private AfAlbumPagerAdapter mAdapter = null;

	/**
	 * ������ ���ر��� View
	 * 	��ȡ�����ܲ���
	 * @param layoutInflater 
	 */
	
	protected abstract int getAlbumLayoutId();
	/**
	 * ��ȡ ��ʾ������� TextView
	 * @param afViewable
	 * @return
	 */
	protected abstract TextView getTextViewName(AfViewable view);
	/**
	 * ��ȡ ��ʾ��������͵�ǰ�� TextView
	 * @param afViewable
	 * @return
	 */
	protected abstract TextView getTextViewSize(AfViewable view);
	/**
	 * ��ȡ ��ʾ�����Ƭ��ϸ��Ϣ TextView
	 * @param afViewable
	 * @return
	 */
	protected abstract TextView getTextViewDetail(AfViewable view);
	/**
	 * ��ȡ��Ử�� ViewPager
	 * @param afViewable
	 * @return
	 */
	protected abstract ViewPager getViewPager(AfViewable view);

	@Override
	protected void onCreate(Bundle bundle, AfIntent intent) throws Exception {
		// TODO Auto-generated method stub
		super.onCreate(bundle, intent);

		setContentView(getAlbumLayoutId());
		mViewPager = getViewPager(this);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOnTouchListener(this);

		mTvSize = getTextViewSize(this);
		mTvDetail = getTextViewDetail(this);
		mTvName = getTextViewName(this);

		int index = 0;
		mHeader = intent.get(EXTRA_PHOTO_HEAD, Photo.class);
		AlbumID = intent.get(EXTRA_UUID_ALBUMID, UUIDUtil.Empty,UUID.class);
		mDescribe = intent.getString(EXTRA_STRING_DESCRIBE, "");
		mPhotoName = intent.getString(EXTRA_STRING_NAME, "");
		mHeadUrl = intent.getString(EXTRA_HEADURL, "");
		List<Photo> list = intent.getList(EXTRA_PHOTO_LIST, Photo.class);
		//���������߷���
		if(mHeader != null){
			mltPhoto.add(mHeader);
			if(mDescribe.equals("")){
				mDescribe = mHeader.Describe;
			}
			if(mPhotoName.equals("")){
				mPhotoName = mHeader.Name;
			}
		}else if (!AfStringUtil.isEmpty(mPhotoName) 
				&& !AfStringUtil.isEmpty(mDescribe)
				&& !AfStringUtil.isEmpty(mHeadUrl)
				&& !UUIDUtil.Empty.equals(AlbumID)) {
			mHeader = new Photo(mPhotoName, mHeadUrl, mDescribe);
			mltPhoto.add(mHeader);
		}
		//Ԥ�����������
		if(list != null && list.size() > 0){
			index = intent.getInt(EXTRA_INT_INDEX, 0);
			index = index >= list.size()?0:(index+mltPhoto.size());
			mltPhoto.addAll(list);
		}
		if(mltPhoto.size() == 0 && UUIDUtil.Empty.equals(AlbumID)){
			throw new AfToastException("����б�Ϊ��!");
		}
		if(!AlbumID.equals(UUIDUtil.Empty)){
			postTask(new LoadAlbumTask());
		} 
		
		onPageSelected(0);//�õ����Ԫ�س�ʼ������
		mAdapter = new AfAlbumPagerAdapter(this, mltPhoto);
		mAdapter.setOnTouchListener(this);
		mViewPager.setAdapter(mAdapter);
		mAdapter.setViewPager(mViewPager);
		mViewPager.setCurrentItem(index, false);
	}

	/**
	 * onTouchEvent ʵ��˫������ ��һ�ε����ʱ��
	 */
	private long mLastTouch = 0;
	private float mLastPosX = 0;
	private float mLastPosY = 0;
	private boolean mHandleing= false;

	@Override
	@SuppressLint("HandlerLeak")
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			mLastPosX = event.getRawX();
			mLastPosY = event.getRawY();
			mLastTouch = System.currentTimeMillis();
		} else if (action == MotionEvent.ACTION_MOVE) {
			float PosX = event.getRawX();
			float PosY = event.getRawY();
			float dPosX = PosX - mLastPosX;
			float dPosY = PosY - mLastPosY;
			double distance = Math.sqrt(dPosX*dPosX+dPosY*dPosY);
			if(distance > 20){
				mLastTouch = 0;
			}
		} else if (action == MotionEvent.ACTION_UP) {
			long current = System.currentTimeMillis();
			long dvalue = current - mLastTouch;
			if (dvalue < 200) {
				if(!mHandleing){
					mHandleing = true;
					Timer timer = new Timer();
					timer.schedule(new AfHandlerTimerTask() {
						long last = mLastTouch;
						@Override
						protected boolean onHandleTimer(Message msg) {
							// TODO Auto-generated method stub
							mHandleing = false;
							if(last == mLastTouch){                                                                                                                                                                                                                           
								int index = mViewPager.getCurrentItem();
								onPageClick(index,mAdapter.getItemAt(index));
							}
							return true;
						}
					}, 300);
				}
			}
		}
		return false;
	}

	protected void onPageClick(int index, Photo photo) {
		// TODO Auto-generated method stub
		this.finish();
	}

	private class LoadAlbumTask extends AfHandlerTask{
		
		public List<Photo> mphotos = null;

		@Override
		protected void onWorking(Message tMessage) throws Exception {
			// TODO Auto-generated method stub
			mphotos = onRequestAlbum(AlbumID, new Page(100, mltPhoto.size()));
		}

		@Override
		protected boolean onHandle(Message msg) {
			// TODO Auto-generated method stub
			if(mResult == RESULT_FINISH){
				mAdapter.AddData(mphotos);
				onPageSelected(mViewPager.getCurrentItem());
			}else{
				makeToastShort(makeErrorToast("������ʧ��"));
			}
			return true;
		}

	}
	/**
	 * ��д ������� ����ʵ�ּ����������
	 * @param albumID	���ID
	 * @param page ��ҳ����
	 * @return
	 */
	public List<Photo> onRequestAlbum(UUID albumID, Page page) throws Exception{
		// TODO Auto-generated method stub
		return new ArrayList<Photo>();
	}

	// arg0==1��ʱ���ʾ���ڻ�����
	// arg0==2��ʱ���ʾ��������ˣ�
	// arg0==0��ʱ���ʾʲô��û��������ͣ���ǡ�
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	
	// Ĭʾ��ǰһ��ҳ�滬������һ��ҳ���ʱ������ǰһ��ҳ�滬��ǰ���õİ취��
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// currPage��Ĭʾ�㵱ǰѡ�е�ҳ�棬������������ҳ����ת��ϵ�ʱ�����õġ�
	@Override
	public void onPageSelected(int currPage) {
		// TODO Auto-generated method stub
		if (mltPhoto.size() > currPage) {
			Photo photo = mltPhoto.get(currPage);
			if (photo.Describe != null && photo.Describe.length() > 0) {
				mTvDetail.setText(photo.Describe);
			}else{
				mTvDetail.setText(mDescribe);
			}
			if (photo.Name != null && photo.Name.length() > 0) {
				mTvName.setText(photo.Name);
			}else{
				mTvName.setText(mPhotoName);
			}
			mTvSize.setText((1 + currPage) + "/" + mltPhoto.size());
		}
	}

}
