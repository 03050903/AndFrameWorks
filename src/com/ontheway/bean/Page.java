package com.ontheway.bean;

/**
 * ��ҳ��ѯ�� 
 * ��ҳ��ѯ��ʼ��������ʼ��ҳ�ȴ� 0 ��ʼ����
 * @author Administrator
 *
 */
public class Page {
    public boolean IsASC = true;
    public String Order = "";
    public int FirstResult = 0;
    public int MaxResult = 0;
    public static final int PAGEMODE_PAGING = 1;
    public static final int PAGEMODE_ADDITIAONAL = 2;
    

    public Page(){}

    /**
     * ����һ���µķ�ҳ��ѯʾ�������Ǵ�0��ʼ
     * 
     * @param sizeOrmax ��ҳ��С �� ׷�Ӳ�ѯ��С
     * @param curOrbegin ��ҳ��ʼҳ �� ׷����ʼҳ
     * Ĭ�� ׷�ӣ�PAGEMODE_ADDITIAONAL 
     */
    public Page(int max,int begin)
    {
        this.FirstResult = begin;
        this.MaxResult = max;
    }
    /**
     * ����һ���µķ�ҳ��ѯʾ�������Ǵ�0��ʼ
     * 
     * @param sizeOrmax ��ҳ��С �� ׷�Ӳ�ѯ��С
     * @param curOrbegin ��ҳ��ʼҳ �� ׷����ʼҳ
     * @param order ������
     * Ĭ�� ׷�ӣ�PAGEMODE_ADDITIAONAL 
     */
    public Page(int max,int begin,String order)
    {
    	this.Order = order;
        this.FirstResult = begin;
        this.MaxResult = max;
    }
    /**
     * ����һ���µķ�ҳ��ѯʾ��
     * @param sizeOrmax ��ҳ��С �� ׷�Ӳ�ѯ��С
     * @param curOrbegin ��ҳ��ʼҳ �� ׷����ʼҳ
     * @param order ������
     * @param isAsc �Ƿ�����
     */
    public Page(int max,int begin,String order,boolean isAsc)
    {
    	this.Order = order;
        this.FirstResult = begin;
        this.MaxResult = max;
    	this.IsASC = isAsc;
    }
    /**
     * ����һ���µķ�ҳ��ѯʾ��
     * 
     * @param order ������
     * @param isAsc �Ƿ�����
     */
    public Page(String order,String asc)
    {
    	if(order != null && !order.equals("")){
        	this.Order = order;
        	this.IsASC = "asc".equals(asc)||"ASC".equals(asc);
    	}
    }

    /**
     * 
     * @Description: ת���ɵ�ҳ����
     * @Version: V1.0, 2015-2-2 ����5:02:53
     * @return
     */
    public int getPageSize(){
    	return MaxResult;
    }
    /**
     * @Description: ת���ɵ�ǰҳ��
     * @Version: V1.0, 2015-2-2 ����5:02:17
     * @return ����0��ʼ��
     */
    public int getcurrentPage(){
    	return FirstResult / MaxResult;
    }

    public String getOrder() {
    	if(Order == null){
    		return "";
    	}
		return Order;
	}
    
    public String getAsc(){
    	if(Order == null || Order.equals("")){
    		return "";
    	}
		return IsASC?"asc":"desc";
    }

	public String toOrderString() {
		// TODO Auto-generated method stub
    	if(Order == null || Order.equals("")){
    		return "";
    	}
		return " order by " + Order + (IsASC?" asc":" desc");
	}
}
