package com.andadver;
/**
 * ���������ӿ�
 * @author Administrator
 */
public interface PointsNotifier {
	public void getPointsFailed(String error) ;
	public void getPoints(String currency, int point);
}
