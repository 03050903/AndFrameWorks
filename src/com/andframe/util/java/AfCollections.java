package com.andframe.util.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * ���Ϲ�����
 * @Description: AfCollections
 * @Author: scwang
 */
public class AfCollections {

	public interface Comparable<T>{
		boolean equals(T left,T right);
	}
	
	public static class ComparableImpl<TT> implements Comparable<TT>{
		@Override
		public boolean equals(TT left, TT right) {
			// TODO Auto-generated method stub
			if (left != right) {
				if (left != null && right != null) {
					return left.equals(right);
				}
				return false;
			}
			return true;
		}
	}

	/**
	 * 
	 * ��ȡ�� left �� right �����ڵ�Ԫ��
	 * @param left���������������ı䣩
	 * @param right���������������ı䣩
	 * @return �� left right ������ list
	 */
	public static <T> List<T> redundance(List<T> left, List<T> right) {
		return redundance(left, right, new ComparableImpl<T>());
	}
	/**
	 * 
	 * ��ȡ�� left �� right �����ڵ�Ԫ��
	 * @param left���������������ı䣩
	 * @param right���������������ı䣩
	 * @param comparable �Ƚ���
	 * @return �� left right ������ list
	 */
	public static <T> List<T> redundance(List<T> left, List<T> right,Comparable<T> comparable) {
		// TODO Auto-generated method stub
		List<T> redundance = new ArrayList<T>();
		List<T> tright = new ArrayList<T>(right);
		for (T f : left) {
			boolean find = false;
			for (int i = 0; i < tright.size(); i++) {
				if (comparable.equals(f, tright.get(i))) {
					find = true;
					tright.remove(i);
					break;
				}
			}
			if (!find) {
				redundance.add(f);
			}
		}
		return redundance;
	}
	
	/**
	 * �� left ����� right �е�����Ԫ�أ����ų��ظ���
	 * @param left �����������ɾ���ɹ�Ԫ�ؽ������ࣩ
	 * @param right���������������ı䣩
	 * @param comparable �Ƚ���
	 */
	public static <T> void addall(List<T> left, List<T> right) {
		// TODO Auto-generated method stub
		addall(left, right, new ComparableImpl<T>());
	}
	
	/**
	 * �� left ����� right �е�����Ԫ�أ����ų��ظ���
	 * @param left �����������ɾ���ɹ�Ԫ�ؽ������ࣩ
	 * @param right���������������ı䣩
	 * @param comparable �Ƚ���
	 */
	public static <T> void addall(List<T> left, List<T> right, Comparable<T> comparable) {
		List<T> redundance = redundance(right, left,comparable);
		left.addAll(redundance);
	}
	
	/**
	 * �� left ��ɾ�� right �е�����Ԫ��
	 * @param left �����������ɾ���ɹ�Ԫ�ؽ�����٣�
	 * @param right���������������ı䣩
	 */
	public static <T> void remove(List<T> left, List<T> right) {
		// TODO Auto-generated method stub
		remove(left, right, new ComparableImpl<T>());
	}
	/**
	 * �� left ��ɾ�� right �е�����Ԫ��
	 * @param left �����������ɾ���ɹ�Ԫ�ؽ�����٣�
	 * @param right���������������ı䣩
	 * @param comparable 
	 */
	public static <T> void remove(List<T> left, List<T> right, Comparable<T> comparable) {
		for (T r : right) {
			for (int i = 0; i < left.size(); i++) {
				if (comparable.equals(left.get(i), r)) {
					left.remove(i);
					break;
				}
			}
		}
	}
	/**
	 * �жϼ��� collects �Ƿ�Ϊ��
	 * @param collects
	 * @return collects == null || collects.size() == 0
	 */
	public static boolean isEmpty(Collection<?> collects) {
		// TODO Auto-generated method stub
		return collects == null || collects.size() == 0;
	}
	/**
	 * �жϼ��� collects �Ƿ�Ϊ��
	 * @param collects
	 * @return collects == null || collects.size() == 0
	 */
	public static boolean isNotEmpty(Collection<?> collects) {
		// TODO Auto-generated method stub
		return collects != null && collects.size() > 0;
	}
	
	/**
	 * �Ƚ� left right �е�Ԫ���Ƿ���� ���� ˳��
	 * @param left���������������ı䣩
	 * @param right���������������ı䣩
	 * @return ��� true ���� false
	 */
	public static <T> boolean equals(List<T> left,List<T> right) {
		// TODO Auto-generated method stub
		return equals(left, right,new ComparableImpl<T>());
	}

	/**
	 * �Ƚ� left right �е�Ԫ���Ƿ���� ���� ˳��
	 * @param left���������������ı䣩
	 * @param right���������������ı䣩
	 * @param comparable �Ƚ���
	 * @return ��� true ���� false
	 */
	public static <T> boolean equals(List<T> left,List<T> right, Comparable<T> comparable) {
		if (left != right) {
			if (left != null && right != null && left.size() == right.size()) {
				for (int i = 0,len = left.size(); i < len; i++) {
					if (!comparable.equals(left.get(i), right.get(i))) {
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return true;
	}
}
