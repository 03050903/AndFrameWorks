package com.andframe.view.treeview;

/***
 * AfTreeEstablishable ����ת����
 * @author SCWANG
 *	������ �� list תΪ tree
 * @param <T>
 */
public interface AfTreeEstablishable<T>{
	boolean isTopNode(T model);
	boolean isChildOf(T child,T parent);
}
