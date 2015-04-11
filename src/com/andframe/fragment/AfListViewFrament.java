package com.andframe.fragment;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.andframe.activity.framework.AfPageable;
import com.andframe.activity.framework.AfView;
import com.andframe.adapter.AfListAdapter;
import com.andframe.adapter.AfListAdapter.IAfLayoutItem;
import com.andframe.application.AfApplication;
import com.andframe.bean.Page;
import com.andframe.exception.AfException;
import com.andframe.feature.AfBundle;
import com.andframe.layoutbind.AfFrameSelector;
import com.andframe.layoutbind.AfModuleNodata;
import com.andframe.layoutbind.AfModuleProgress;
import com.andframe.thread.AfDispatch;
import com.andframe.thread.AfListViewTask;
import com.andframe.thread.AfTask;
import com.andframe.util.java.AfCollections;
import com.andframe.view.AfListView;
import com.andframe.view.pulltorefresh.AfPullToRefreshBase.OnRefreshListener;
/**
 * @Description: �����б��� Frament
 * @Author: scwang
 * @Version: V1.0, 2015-2-27 ����10:26:21
 * @param <T>
 */
public abstract class AfListViewFrament<T> extends AfTabFragment implements
		OnRefreshListener, OnItemClickListener, OnClickListener {

	protected AfTask mLoadTask;

	protected AfModuleNodata mNodata;
	protected AfModuleProgress mProgress;
	protected AfFrameSelector mSelector;

	protected AfListView mListView;
	protected AfListAdapter<T> mAdapter;

	/**
	 * ����ʹ�õ� class ����jsonҪ�õ���
	 * ���� ��������Ϊ TASK_LOAD AfListTask ���Զ�ʹ�û��湦��
	 */
	public Class<T> mCacheClazz = null;
	/** 
	 *  ����ʹ�õ� KEY_CACHELIST = this.getClass().getName()
	 * 		KEY_CACHELIST Ϊ����ı�ʶ
	 */
	public String KEY_CACHELIST = this.getClass().getName();
	
	public AfListViewFrament() {
		// TODO Auto-generated constructor stub
		
	}
	/**
	 * ʹ�û���������������캯��
	 * @param clazz
	 */
	public AfListViewFrament(Class<T> clazz) {
		// TODO Auto-generated constructor stub
		this.mCacheClazz = clazz;
	}
	/**
	 * ʹ�û���������������캯��
	 * 	�����Զ��建���ʶ
	 * @param clazz
	 */
	public AfListViewFrament(Class<T> clazz, String KEY_CACHELIST) {
		// TODO Auto-generated constructor stub
		this.mCacheClazz = clazz;
		this.KEY_CACHELIST = KEY_CACHELIST;
	}

	/**
	 * @Description: ��������
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:19:01
	 * @param bundle
	 * @param view
	 * @throws Exception
	 */
	@Override
	protected void onCreated(AfBundle bundle, AfView view) throws Exception {
		// TODO Auto-generated method stub
		super.onCreated(bundle, view);

		mNodata = newModuleNodata(this);
		mProgress = newModuleProgress(this);
		mSelector = newAfFrameSelector(this);

		mListView = new AfListView(findListView(this));
		mListView.setOnRefreshListener(this);
		mListView.setOnItemClickListener(this);

		if (mLoadTask != null) {
			setLoading();
		} else if (mAdapter == null) {
			setLoading();
			postTask(new AbListViewTask(mCacheClazz,KEY_CACHELIST));
		} else if (mAdapter.getCount() == 0) {
			setNodata();
		} else {
			/**
			 * �ӳ�ִ�� ��Adapter
			 * 	��ֹ������ onCreated ��ListView ���HeaderView
			 * 	������� FootView �����쳣
			 */
			AfApplication.dispatch(new AfDispatch() {
				@Override
				protected void onDispatch() {
					// TODO Auto-generated method stub
					setData(mAdapter);
				}
			});
		}
	}
	
	@Override
	protected void onSwitchOver(int count) {
		// TODO Auto-generated method stub
		super.onSwitchOver(count);
	}

	/**
	 * ��ȡ�б�ؼ�
	 * @param pageable
	 * @return pageable.findListViewById(id)
	 */
	protected abstract ListView findListView(AfPageable pageable);

	/**
	 * �½�ҳ��ѡ����
	 * @param pageable
	 * @return
	 */
	protected abstract AfFrameSelector newAfFrameSelector(AfPageable pageable);
	/**
	 * �½�����ҳ��
	 * @param pageable
	 * @return
	 */
	protected abstract AfModuleProgress newModuleProgress(AfPageable pageable);
	/**
	 * �½�������ҳ��
	 * @param pageable
	 * @return
	 */
	protected abstract AfModuleNodata newModuleNodata(AfPageable pageable);

	/**
	 * @Description: ��ʾ����ҳ��
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:18:26
	 * @param adapter
	 */
	public void setData(AfListAdapter<T> adapter) {
		// TODO Auto-generated method stub
		mListView.setAdapter(adapter);
		mSelector.SelectFrame(mListView);
	}

	/**
	 * @Description: ���ڼ���������ʾ
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:18:05
	 */
	public void setLoading() {
		// TODO Auto-generated method stub
		mProgress.setDescription("���ڼ���...");
		mSelector.SelectFrame(mProgress);
	}
	
	/**
	 * ������ҳ��ˢ�¼�����
	 * ������Ҫ��д�������Ļ����Զ� 
	 * mNodataRefreshListener ���¸�ֵ
	 */
	private OnClickListener mNodataRefreshListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onRefresh();
			setLoading();
		}
	};

	/**
	 * @Description: ���������
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:16:46
	 * @Modified: ���δ���setNodata����
	 */
	public void setNodata() {
		// TODO Auto-generated method stub
		mNodata.setDescription("��Ǹ����������");
		mSelector.SelectFrame(mNodata);
		mNodata.setOnRefreshListener(mNodataRefreshListener);
	}

	/**
	 * @Description: ������Ϣ����
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:16:26
	 * @param ex
	 */
	public void setLoadError(Throwable ex) {
		// TODO Auto-generated method stub
		mNodata.setDescription(AfException.handle(ex, "���ݼ��س����쳣"));
		mNodata.setOnRefreshListener(mNodataRefreshListener);
		mSelector.SelectFrame(mNodata);
	}

	/**
	 * @Description: �û����ط�ҳ֪ͨ�¼�
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:15:54
	 * @return
	 */
	@Override
	public boolean onMore() {
		// TODO Auto-generated method stub
		postTask(new AbListViewTask(mAdapter));
		return true;
	}

	/**
	 * @Description: �û�ˢ������֪ͨ�¼�
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:14:52
	 * @return
	 */
	@Override
	public boolean onRefresh() {
		// TODO Auto-generated method stub
		postTask(new AbListViewTask(null));
		return true;
	}

	/**
	 * @Description: �����б����¼�
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:25:18
	 * @Modified:
	 * @param parent
	 * @param view
	 * @param index
	 * @param id
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
		// TODO Auto-generated method stub
		T model = mAdapter.getItemAt(mListView.getDataIndex(index));
		onItemClick(model,index);
	}

	/**
	 * @Description: onItemClick �¼��� ��װ һ����������������д�������
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:20:05
	 * @param model
	 * @param index
	 */
	protected void onItemClick(T model, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		if (v != null && v.getId() == AfModuleNodata.ID_BUTTON) {
//			onRefresh();
//			setLoading();
//		}
	}

	/**
	 * ���ݼ����ڲ������ࣨ���ݼ����¼��Ѿ�ת������ʵ�ʴ�����룩
	 * @author scwang
	 */
	protected class AbListViewTask extends AfListViewTask<T> {

		/**
		 * ���Դ������ػ������񣨿�ܻ������ʧ�ܻ�ı��ˢ������
		 * @param clazz
		 * @param KEY_CACHELIST
		 */
		public AbListViewTask(Class<T> clazz, String KEY_CACHELIST) {
			super(clazz, KEY_CACHELIST);
			// TODO Auto-generated constructor stub
		}

		/**
		 * ���Դ������ظ��ࣨ��ҳ������ ������null���Դ���ˢ������
		 * @param adapter ������������ͳ�Ƶ�ǰ���������ҳ������null���Դ���ˢ������
		 */
		public AbListViewTask(AfListAdapter<T> adapter) {
			super(adapter);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onPrepare() {
			// TODO Auto-generated method stub
			mLoadTask = this;
			return super.onPrepare();
		}
		
		@Override
		protected List<T> onLoad() {
			// TODO Auto-generated method stub
			List<T> list = AfListViewFrament.this.onTaskLoad();
			if (!AfCollections.isEmpty(list)) {
				return list;
			}
			return super.onLoad();
		}

		//�¼�ת�� �ο� AfListViewFrament.onListByPage
		@Override
		protected List<T> onListByPage(Page page, int task) throws Exception {
			// TODO Auto-generated method stub
			return AfListViewFrament.this.onTaskListByPage(page,task);
		}

		//�¼�ת�� �ο� AfListViewFrament.onLoaded
		@Override
		protected boolean onLoaded(boolean isfinish, List<T> ltdata,Date cachetime) {
			// TODO Auto-generated method stub
			mLoadTask = null;
			return AfListViewFrament.this.onLoaded(this,isfinish, ltdata,cachetime);
		}

		//�¼�ת�� �ο� AfListViewFrament.onRefreshed
		@Override
		protected boolean onRefreshed(boolean isfinish, List<T> ltdata) {
			// TODO Auto-generated method stub
			mLoadTask = null;
			return AfListViewFrament.this.onRefreshed(this,isfinish, ltdata);
		}
		//�¼�ת�� �ο� AfListViewFrament.onMored
		@Override
		protected boolean onMored(boolean isfinish, List<T> ltdata,
				boolean ended) {
			// TODO Auto-generated method stub
			mLoadTask = null;
			return AfListViewFrament.this.onMored(this,isfinish, ltdata);
		}

	}

	/**
	 * @Description: ������ؽ�������ʱ�䣨���Ĭ�ϵ���onRefreshed�¼�����
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-22 ����9:19:02
	 * @Modified: ���δ���onLoaded����
	 * @param task 
	 * @param isfinish
	 * @param ltdata
	 * @param cachetime ����ʱ��
	 * @return
	 */
	protected boolean onLoaded(AbListViewTask task, boolean isfinish,
			List<T> ltdata, Date cachetime) {
		// TODO Auto-generated method stub
		boolean deal = onRefreshed(task,isfinish,ltdata);
		if (isfinish && !AfCollections.isEmpty(ltdata)) {
			//�����ϴ�ˢ�»���ʱ��
			mListView.setLastUpdateTime(cachetime);
		}
		return deal;
	}
	/**
	 * @param task 
	 * @Description: ����ˢ�½��������¼�
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-20 ����11:29:09
	 * @Modified: ���δ���onRefreshed����
	 * @param isfinish �Ƿ�ɹ�ִ��
	 * @param ltdata
	 * @return ����true �Ѿ����ô���ҳ����ʾ ����false ��ܻ�����Ĭ�ϴ�����
	 */
	@SuppressWarnings("static-access")
	protected boolean onRefreshed(AbListViewTask task, boolean isfinish, List<T> ltdata) {
		// TODO Auto-generated method stub
		if (isfinish) {
			//֪ͨ�б�ˢ�����
			mListView.finishRefresh();
			if (!AfCollections.isEmpty(ltdata)) {
				mAdapter = newAdapter(getActivity(), ltdata);
				setData(mAdapter);
				if (ltdata.size() < task.PAGE_SIZE) {
					mListView.removeMoreView();
				}else {
					mListView.addMoreView();
				}
			} else {
				setNodata();
			}
		} else {
			//֪ͨ�б�ˢ��ʧ��
			mListView.finishRefreshFail();
			if (mAdapter != null && mAdapter.getCount() > 0) {
				mListView.setAdapter(mAdapter);
				mSelector.SelectFrame(mListView);
				makeToastLong(task.makeErrorToast("����ʧ��"));
			} else {
				setLoadError(task.mException);
			}
		}
		return true;
	}
	/**
	 * @Description: ������ظ�����������¼�
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-20 ����11:34:04
	 * @Modified: ���δ���onMored����
	 * @param loadListTask
	 * @param isfinish
	 * @param ltdata
	 * @return ����true �Ѿ����ô���ҳ����ʾ ����false ��ܻ�����Ĭ�ϴ�����
	 */
	@SuppressWarnings("static-access")
	protected boolean onMored(AbListViewTask task, boolean isfinish,
			List<T> ltdata) {
		// TODO Auto-generated method stub
		// ֪ͨ�б�ˢ�����
		mListView.finishLoadMore();
		if (isfinish) {
			if (!AfCollections.isEmpty(ltdata)) {
				final int count = mAdapter.getCount();
				// �����б�
				mAdapter.addData(ltdata);
				mListView.smoothScrollToPosition(count+1);
			}
			if (ltdata.size() < task.PAGE_SIZE) {
				// �رո���ѡ��
				makeToastShort("����ȫ��������ϣ�");
				mListView.removeMoreView();
			}
		} else {
			makeToastLong(task.makeErrorToast("��ȡ����ʧ�ܣ�"));
		}
		return true;
	}
	/**
	 * ��ȡ�б����Item
	 * �����д newAdapter ֮�󣬱���������Ч
	 * @param data ��Ӧ������
	 * @return ʵ�� ���ֽӿ� IAfLayoutItem ��Item����
	 * 	new LayoutItem implements IAfLayoutItem<T>(){}
	 */
	protected abstract IAfLayoutItem<T> getItemLayout(T data);

	/**
	 * @Description: ���ػ����б�����ҳ�����첽�߳���ִ�У������Ը���ҳ�������
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-22 ����9:27:48
	 * @Modified: ���δ���onLoad����
	 * @return ���� null ����ʹ�ÿ�����û���
	 */
	protected List<T> onTaskLoad() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * ���ݷ�ҳ���أ����첽�߳���ִ�У������Ը���ҳ�������
	 * @param page ��ҳ����
	 * @param task ����id
	 * @return ���ص��������б�
	 * @throws Exception
	 */
	protected abstract List<T> onTaskListByPage(Page page, int task) throws Exception;

	/**
	 * @Description: ��������ltdata�½�һ�� ������ ��д�������֮��getItemLayout������ʧЧ
	 * @Author: scwang
	 * @Version: V1.0, 2015-1-20 ����4:46:34
	 * @Modified: ���δ���newAdapter����
	 * @param context
	 * @param ltdata
	 * @return
	 */
	protected AfListAdapter<T> newAdapter(Context context, List<T> ltdata) {
		// TODO Auto-generated method stub
		return new AbListViewAdapter(getContext(), ltdata);
	}

	/**
	 * @Description: ListView�������������¼��Ѿ�ת��getItemLayout����ʵ�ʴ�����룩
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-27 ����10:21:27
	 */
	protected class AbListViewAdapter extends AfListAdapter<T>{

		public AbListViewAdapter(Context context, List<T> ltdata) {
			super(context, ltdata);
			// TODO Auto-generated constructor stub
		}

		/**
		 * @Description: ת���¼��� AfListViewFrament.this.getItemLayout(data);
		 * @Author: scwang
		 * @param data
		 * @return 
		 */
		@Override
		protected IAfLayoutItem<T> getItemLayout(T data) {
			// TODO Auto-generated method stub
			return AfListViewFrament.this.getItemLayout(data);
		}
		
	}
}
