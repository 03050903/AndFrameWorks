package com.andframe.view.treeview;

import java.util.Collection;

/***
 * AfTreeNodeable ���ι�����
 * @author SCWANG
 *		���������е����νṹת��
 * @param <T>
 */
public interface AfTreeNodeable<T>{
	Collection<T> getChildren(T model);
}
