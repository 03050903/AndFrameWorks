package com.andframe.module;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.andframe.$;
import com.andframe.activity.AfActivity;
import com.andframe.annotation.interpreter.Injecter;
import com.andframe.annotation.interpreter.LayoutBinder;
import com.andframe.annotation.interpreter.ViewBinder;
import com.andframe.api.pager.Pager;
import com.andframe.api.viewer.ViewModuler;
import com.andframe.api.viewer.ViewQuery;
import com.andframe.api.viewer.ViewQueryHelper;
import com.andframe.api.viewer.Viewer;
import com.andframe.exception.AfExceptionHandler;
import com.andframe.impl.helper.AfViewQueryHelper;
import com.andframe.impl.viewer.ViewerWarpper;

/**
 * 视图模块实现基类
 */
@SuppressLint("ViewConstructor")
@SuppressWarnings("unused")
public abstract class AfViewModuler extends ViewerWarpper implements Viewer, ViewModuler, ViewQueryHelper {

	public static <T extends AfViewModuler> T init(Object handler, Class<T> clazz, Viewer viewable, int viewId) {
		T module = null;
		try {
			module = clazz.newInstance();
			if (module != null && !module.isValid()) {
				AfViewModuler viewModule = module;
				viewModule.setTarget(viewable, viewable.findViewByID(viewId));
				viewModule.onBindHandler(handler);
			}
			return module;
		} catch (IllegalAccessException e) {
			AfExceptionHandler.handle(e, "类 " + clazz.getSimpleName() + " 必须有一个公有构造函数");
		} catch (InstantiationException e) {
			AfExceptionHandler.handle(e, "类 " + clazz.getSimpleName() + " 必须有一个无参构造函数");
		} catch (Throwable e) {
			AfExceptionHandler.handle(e, "AfViewModuler.init");
		}
		return module;
	}

	public static <T extends AfViewModuler> T init(Object handler, Class<T> clazz, Viewer viewable) {
		int layoutId = LayoutBinder.getBindLayoutId(clazz, viewable.getContext());
		if (layoutId <= 0) {
			return null;
		}
		return init(handler, clazz, viewable, layoutId);
	}

	protected AfViewModuler(){
		super((View)null);
	}

	protected Pager getPager() {

		if (getContext() instanceof AfActivity) {
			return ((AfActivity) getContext());
		}
		Activity activity = $.pager().currentActivity();
		if (activity != null) {
			return (AfActivity) activity;
		}
		return null;
	}

	/**
	 * 如果不想要采用 注入的形式
	 * 子类构造函数中必须调用这个函数
	 */
	protected void initializeComponent(@NonNull Viewer viewer){
		int layoutId = LayoutBinder.getBindLayoutId(this, viewer.getContext());
		if (layoutId > 0) {
			view = viewer.findViewById(layoutId);
			setTarget(viewer, view);
		} else {
			AfExceptionHandler.handle(getClass().getSimpleName() + "ViewModuler 必须指定BindLayout","ViewModuler.initializeComponent.");
		}
	}

	/**
	 * 如果不想要采用 注入的形式
	 * 子类构造函数中必须调用这个函数
	 */
	protected void initializeComponent(@NonNull Viewer viewer, int id){
		view = viewer.findViewById(id);
		setTarget(viewer, view);
	}

	/**
	 * 如果不想要采用 注入的形式
	 * 子类构造函数中必须调用这个函数
	 */
	protected void initializeComponent(@NonNull View view){
		setTarget(null, view);
	}

	public void onBindHandler(Object handler) {

	}

	private void setTarget(Viewer viewer, View target) {
		if (target != null) {
			this.view = target;
			this.onCreated(viewer, target);
		}
	}

	protected void onCreated(Viewer viewable, View view) {
		Injecter.doInject(this, getContext());
		ViewBinder.doBind(this);
	}

	@Override
	public void hide() {
		if(isValid() && view != null){
			view.setVisibility(View.GONE);
		}
	}

	@Override
	public void show() {
		if(isValid() && view != null){
			view.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean isValid() {
		return view != null;
	}

	@Override
	public boolean isVisibility() {
		return isValid() && view.getVisibility() == View.VISIBLE;
	}

	//<editor-fold desc="ViewQuery 集成">

	ViewQuery<? extends ViewQuery> $$ = AfViewQueryHelper.newHelper(this);

	@Override
	public ViewQuery<? extends ViewQuery> $(View... views) {
		return $$.$(views);
	}

	@Override
	public ViewQuery<? extends ViewQuery> $(Integer id, int... ids) {
		return $$.$(id, ids);
	}

	@Override
	public ViewQuery<? extends ViewQuery> $(String idvalue, String... idvalues) {
		return $$.$(idvalue);
	}

	@Override
	public ViewQuery<? extends ViewQuery> $(Class<? extends View> type) {
		return $$.$(type);
	}

	@Override
	public ViewQuery<? extends ViewQuery> $(Class<? extends View>[] types) {
		return $$.$(types);
	}
	//</editor-fold>

}
