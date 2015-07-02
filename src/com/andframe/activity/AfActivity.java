package com.andframe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.andframe.feature.AfIntent;
/**
 * ��� Activity
 * @author SCWANG
 */
public abstract class AfActivity extends com.andframe.activity.framework.AfActivity implements OnItemClickListener {

	/**
	 * final ԭʼ onCreate(Bundle bundle)
	 * ����ֻ����д onCreate(Bundle bundle,AfIntent intent)
	 */
	@Override
	protected final void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
	}

	/**
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 * final ��д onActivityResult ʹ�� try-catch ���� 
	 * 		onActivityResult(AfIntent intent, int questcode,int resultcode)
	 * @see AfActivity#onActivityResult(AfIntent intent, int questcode,int resultcode)
	 * {@link AfActivity#onActivityResult(AfIntent intent, int questcode,int resultcode)}
	 */
	@Override
	protected final void onActivityResult(int questcode, int resultcode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(questcode, resultcode, data);
	}
	/**
	 * @Description: final ��װ onItemClick �¼����� ��ֹ�׳��쳣����
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-14 ����10:34:56
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	public final void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		super.onItemClick(parent, view, position, id);
	}

	/**
	 * ת�� onBackPressed �¼��� AfFragment
	 */
	@Override
	public final void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
