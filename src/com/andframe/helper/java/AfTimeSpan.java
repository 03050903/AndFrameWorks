package com.andframe.helper.java;

import java.util.Date;
/**
 * @Description: java ʱ���ȼ���
 * @Author: scwang
 * @Version: V1.0, 2015-3-13 ����5:01:05
 * @Modified: ���δ���AfTimeSpan��
 */
public class AfTimeSpan
{
    private long time;

    public static final long TIME_SECOND = 1000;
    public static final long TIME_MINUTE = 1000*60;
    public static final long TIME_HOUR = 1000*60*60;
    public static final long TIME_DAY = 1000*60*60*24;
    
//    private static double TIME_RECIPROCAL_SECOND = 1/TIME_SECOND;
//    private static double TIME_RECIPROCAL_MINUTE = 1/(TIME_MINUTE);
//    private static double TIME_RECIPROCAL_HOUR = 1/(TIME_HOUR);
//    private static double TIME_RECIPROCAL_DAY = 1/(TIME_DAY);
    //
    // ժҪ:
    //     ���µ� System.TimeSpan ��ʼ��Ϊָ���Ŀ̶�����
    //
    // ����:
    //   ticks:
    //     �� 1 ����Ϊ��λ��ʾ��ʱ��Ρ�
    public AfTimeSpan(long ticks)
    {
        time = ticks;
    }
    //
    // ժҪ:
    //     ���µ� System.TimeSpan ��ʼ��Ϊָ����Сʱ������������������
    //
    // ����:
    //   hours:
    //     Сʱ����
    //
    //   minutes:
    //     ��������
    //
    //   seconds:
    //     ������
    //
    // �쳣:
    //   System.ArgumentOutOfRangeException:
    //     �ò���ָ��һ��С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue �� System.TimeSpan
    //     ֵ��
    public AfTimeSpan(int hours, int minutes, int seconds)
    {
        time = 0;
        time += seconds*TIME_SECOND;
        time += minutes*TIME_MINUTE;
        time += hours*TIME_HOUR;
    }
    //
    // ժҪ:
    //     ���µ� System.TimeSpan ��ʼ��Ϊָ����������Сʱ������������������
    //
    // ����:
    //   days:
    //     ������
    //
    //   hours:
    //     Сʱ����
    //
    //   minutes:
    //     ��������
    //
    //   seconds:
    //     ������
    //
    // �쳣:
    //   System.ArgumentOutOfRangeException:
    //     �ò���ָ��һ��С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue �� System.TimeSpan
    //     ֵ��
    public AfTimeSpan(int days, int hours, int minutes, int seconds)
    {
        time = 0;
        time += seconds*TIME_SECOND;
        time += minutes*TIME_MINUTE;
        time += hours*TIME_HOUR;
        time += days*TIME_DAY;
    }
    //
    // ժҪ:
    //     ���µ� System.TimeSpan ��ʼ��Ϊָ����������Сʱ�����������������ͺ�������
    //
    // ����:
    //   days:
    //     ������
    //
    //   hours:
    //     Сʱ����
    //
    //   minutes:
    //     ��������
    //
    //   seconds:
    //     ������
    //
    //   milliseconds:
    //     ��������
    //
    // �쳣:
    //   System.ArgumentOutOfRangeException:
    //     �ò���ָ��һ��С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue �� System.TimeSpan
    //     ֵ��
    public AfTimeSpan(int days, int hours, int minutes, int seconds, int milliseconds)
    {
        time = milliseconds;
        time += seconds*TIME_SECOND;
        time += minutes*TIME_MINUTE;
        time += hours*TIME_HOUR;
        time += days*TIME_DAY;
    }

    // ժҪ:
    //     ��ȡ��ǰ System.TimeSpan �ṹ����ʾ��ʱ�������������֡�
    //
    // ���ؽ��:
    //     ��ʵ�����������֡�����ֵ����������Ҳ�����Ǹ�����
    public int getDays() { 
        return (int)(time/TIME_DAY);
    }
    //
    // ժҪ:
    //     ��ȡ��ǰ System.TimeSpan �ṹ����ʾ��ʱ������Сʱ�����֡�
    //
    // ���ؽ��:
    //     ��ǰ System.TimeSpan �ṹ��Сʱ����������ֵ�ķ�ΧΪ -23 �� 23��
    public int getHours() { 
        return (int)(time/TIME_HOUR)%24;
    }
    //
    // ժҪ:
    //     ��ȡ��ǰ System.TimeSpan �ṹ����ʾ��ʱ�����ķ��������֡�
    //
    // ���ؽ��:
    //     ��ǰ System.TimeSpan �ṹ�ķ��ӷ���������ֵ�ķ�ΧΪ -59 �� 59��
    public int getMinutes() { 
        return (int)(time/TIME_MINUTE)%60;
    }
    //
    // ժҪ:
    //     ��ȡ��ǰ System.TimeSpan �ṹ����ʾ��ʱ�������������֡�
    //
    // ���ؽ��:
    //     ��ǰ System.TimeSpan �ṹ�������������ֵ�ķ�ΧΪ -59 �� 59��
    public int getSeconds() { 
        return (int)(time/TIME_SECOND)%60;
    }
    //
    // ժҪ:
    //     ��ȡ��ǰ System.TimeSpan �ṹ����ʾ��ʱ�����ĺ��������֡�
    //
    // ���ؽ��:
    //     ��ǰ System.TimeSpan �ṹ�ĺ������������ֵ�ķ�ΧΪ -999 �� 999��
    public int getMilliseconds() { 
        return (int)(time%TIME_SECOND);
    }
    //
    // ժҪ:
    //     ��ȡ��ʾ��ǰ System.TimeSpan �ṹ��ֵ�Ŀ̶�����
    //
    // ���ؽ��:
    //     ��ʵ�������Ŀ̶�����
    public long getTicks() { 
        return time;
    }
    //
    // ժҪ:
    //     ��ȡ�������������С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ����������
    public double getTotalDays() { 
        return 1.0*time/TIME_DAY;
    }
    //
    // ժҪ:
    //     ��ȡ�������������С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ����������
    public double getUnSignedTotalDays() { 
        return Math.abs(1.0*time/TIME_DAY);
    }
    //
    // ժҪ:
    //     ��ȡ�������������С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ����������
    public long getUnSignedIntegerTotalDays() { 
        return Math.abs(time/TIME_DAY);
    }
    //
    // ժҪ:
    //     ��ȡ����Сʱ����Сʱ��С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ����Сʱ����
    public double getTotalHours() { 
        return 1.0*time/TIME_HOUR;
    }
    //
    // ժҪ:
    //     ��ȡ����Сʱ����Сʱ��С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ����Сʱ����
    public double getUnSignedTotalHours() { 
        return Math.abs(1.0*time/TIME_HOUR);
    }
    //
    // ժҪ:
    //     ��ȡ����Сʱ����Сʱ��С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ����Сʱ����
    public long getUnSignedIntegerTotalHours() { 
        return Math.abs(time/TIME_HOUR);
    }
    //
    // ժҪ:
    //     ��ȡ�����������ͷ��ӵ�С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ���ܷ�������
    public double getTotalMinutes() {
        return 1.0*time/TIME_MINUTE;
    }
    //
    // ժҪ:
    //     ��ȡ�����������ͷ��ӵ�С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ���ܷ�������
    public double getUnSignedTotalMinutes() {
        return Math.abs(1.0*time/TIME_MINUTE);
    }
    //
    // ժҪ:
    //     ��ȡ�����������ͷ��ӵ�С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ���ܷ�������
    public long getUnSignedIntegerTotalMinutes() {
        return Math.abs(time/TIME_MINUTE);
    }
    //
    // ժҪ:
    //     ��ȡ�������������С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ����������
    public double getTotalSeconds(){
        return 1.0*time/TIME_SECOND;
    }
    //
    // ժҪ:
    //     ��ȡ�����������ͺ����С�����ֱ�ʾ�ĵ�ǰ System.TimeSpan �ṹ��ֵ��
    //
    // ���ؽ��:
    //     ��ʵ����ʾ���ܺ�������
    public long getTotalMilliseconds(){
        return this.time;
    }

    // ժҪ:
    //     ��ָ���� System.TimeSpan ��ӵ���ʵ���С�
    //
    // ����:
    //   ts:
    //     Ҫ���ϵ�ʱ������
    //
    // ���ؽ��:
    //     һ�����󣬱�ʾ��ʵ����ֵ�� ts ��ֵ��
    //
    // �쳣:
    //   System.OverflowException:
    //     �����ɵ� System.TimeSpan С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue��
    public AfTimeSpan Add(AfTimeSpan ts)
    {
        return new AfTimeSpan(this.time+ts.time);
    }

    // ժҪ:
    //     ��ָ���� System.TimeSpan ��ӵ���ʵ���С�
    //
    // ����:
    //   ts:
    //     Ҫ��ȥ��ʱ������
    //
    // ���ؽ��:
    //     һ�����󣬱�ʾ��ʵ����ֵ�� ts ��ֵ��
    //
    // �쳣:
    //   System.OverflowException:
    //     �����ɵ� System.TimeSpan С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue��
    public AfTimeSpan Minus(AfTimeSpan ts)
    {
        return new AfTimeSpan(this.time-ts.time);
    }
    //
    // ժҪ:
    //     �Ƚ����� System.TimeSpan ֵ��������һ��������������ָʾ��һ��ֵ�Ƕ��ڡ����ڻ��ǳ��ڵڶ���ֵ��
    //
    // ����:
    //   t1:
    //     Ҫ�Ƚϵĵ�һ��ʱ������
    //
    //   t2:
    //     Ҫ�Ƚϵĵڶ���ʱ������
    //
    // ���ؽ��:
    //     ����ֵ֮һ��ֵ˵��-1 t1 ���� t2��0t1 ���� t2��1t1 ���� t2��
    public static int Compare(AfTimeSpan t1, AfTimeSpan t2)
    {
        if(t1.time<t2.time) return -1;
        if(t1.time>t2.time) return 1;
        return 0;
    }
    //
    // ժҪ:
    //     �Ƚ��Ƿ�С�� AfTimeSpan t 
    //
    // ����:
    //   t: Ҫ�Ƚϵĵ�ʱ������
    //
    // ���ؽ��:
    //     ����ֵ֮һ��true С�� t  : false ���ڵ��� t
    public boolean LessThan(AfTimeSpan t)
    {
        return this.time < t.time;
    }
    //
    // ժҪ:
    //     �Ƚ��Ƿ���� AfTimeSpan t 
    //
    // ����:
    //   t: Ҫ�Ƚϵĵ�ʱ������
    //
    // ���ؽ��:
    //     ����ֵ֮һ��true ���� t  : false С�ڵ��� t
    public boolean GreaterThan(AfTimeSpan t)
    {
        return this.time > t.time;
    }
    //
    // ժҪ:
    //     ����ʵ����ָ��������бȽϣ�������һ��������������ָʾ��ʵ���Ƕ��ڡ����ڻ��ǳ���ָ������
    //
    // ����:
    //   value:
    //     Ҫ�ȽϵĶ��󣬻�Ϊ null��
    //
    // ���ؽ��:
    //     ����ֵ֮һ��ֵ˵��-1��ʵ������ value��0��ʵ������ value��1��ʵ������ value��- �� -value Ϊ null��
    //
    // �쳣:
    //   System.ArgumentException:
    //     value ���� System.TimeSpan��
    public int CompareTo(Object value)
    {
        if (value == null)
        {
            return 1;
        }
        try
        {
            long num = ((AfTimeSpan) value).time;
            if (this.time > num)
            {
                return 1;
            }
            if (this.time < num)
            {
                return -1;
            }
        }
        catch(Exception ex)
        {
            return 1;
        }
        return 0;

    }
    //
    // ժҪ:
    //     ����ʵ����ָ���� System.TimeSpan ������бȽϣ�������һ��������������ָʾ��ʵ���Ƕ��ڡ����ڻ��ǳ��� System.TimeSpan
    //     ����
    //
    // ����:
    //   value:
    //     Ҫ���ʵ�����бȽϵĶ���
    //
    // ���ؽ��:
    //     һ���з������֣�������ָʾ��ʵ���� value �����ֵ��ֵ˵����������ʵ������ value�����ʵ������ value����������ʵ������ value��
    public int Compare(AfTimeSpan value)
    {
        long num = value.time;
        if (this.time > num)
        {
            return 1;
        }
        if (this.time < num)
        {
            return -1;
        }
        return 0;

    }
    //
    // ժҪ:
    //     �����µ� System.TimeSpan ������ֵ�ǵ�ǰ System.TimeSpan ����ľ���ֵ��
    //
    // ���ؽ��:
    //     һ���¶�����ֵ�ǵ�ǰ System.TimeSpan ����ľ���ֵ��
    //
    // �쳣:
    //   System.OverflowException:
    //     ��ʵ����ֵΪ System.TimeSpan.MinValue��
    public AfTimeSpan Duration()
    {
        return new AfTimeSpan((this.time >= 0L) ? this.time : -this.time);
    }
    //
    // ժҪ:
    //     ����һ��ֵ����ֵָʾ��ʵ���Ƿ���ָ���Ķ�����ȡ�
    //
    // ����:
    //   value:
    //     ���ʵ�����бȽϵĶ���
    //
    // ���ؽ��:
    //     ��� value �Ǳ�ʾ�뵱ǰ System.TimeSpan �ṹ������ͬʱ������ System.TimeSpan ������Ϊ true������Ϊ
    //     false��
    public boolean Equals(Object value)
    {
    	if (value instanceof AfTimeSpan) {
            return ((AfTimeSpan)value).time == this.time;
		}
       return false;
    }
    //
    // ժҪ:
    //     ����һ��ֵ����ֵָʾ��ʵ���Ƿ���ָ���� System.TimeSpan ������ȡ�
    //
    // ����:
    //   obj:
    //     ���ʵ�����бȽϵĶ���
    //
    // ���ؽ��:
    //     ��� obj ��ʾ��ʱ�������ʵ����ͬ����Ϊ true������Ϊ false��
    public boolean Equals(AfTimeSpan obj)
    {
        return obj.time == this.time;
    }
    //
    // ժҪ:
    //     ����һ��ֵ����ֵָʾ System.TimeSpan ������ָ��ʵ���Ƿ���ȡ�
    //
    // ����:
    //   t1:
    //     Ҫ�Ƚϵĵ�һ��ʱ������
    //
    //   t2:
    //     Ҫ�Ƚϵĵڶ���ʱ������
    //
    // ���ؽ��:
    //     ��� t1 �� t2 ��ֵ��ȣ���Ϊ true������Ϊ false��
    public static boolean Equals(AfTimeSpan t1, AfTimeSpan t2)
    {
        return t1.time == t2.time;
    }
    //
    // ժҪ:
    //     ���ر�ʾָ�������� System.TimeSpan�����ж�������ָ����ȷ����ӽ��ĺ��롣
    //
    // ����:
    //   value:
    //     ��������ȷ����ӽ��ĺ��롣
    //
    // ���ؽ��:
    //     ��ʾ value �Ķ���
    //
    // �쳣:
    //   System.OverflowException:
    //     value С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue��- �� -value
    //     Ϊ System.Double.PositiveInfinity��- �� -value Ϊ System.Double.NegativeInfinity��
    //
    //   System.ArgumentException:
    //     value ���� System.Double.NaN��
    public static AfTimeSpan FromDays(double value){
        return Interval(value, 0x5265c00); 
    }
    //
    // ժҪ:
    //     ���ر�ʾָ��Сʱ���� System.TimeSpan�����ж�Сʱ����ָ����ȷ����ӽ��ĺ��롣
    //
    // ����:
    //   value:
    //     ��ȷ����ӽ��ĺ����Сʱ����
    //
    // ���ؽ��:
    //     ��ʾ value �Ķ���
    //
    // �쳣:
    //   System.OverflowException:
    //     value С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue��- �� -value
    //     Ϊ System.Double.PositiveInfinity��- �� -value Ϊ System.Double.NegativeInfinity��
    //
    //   System.ArgumentException:
    //     value ���� System.Double.NaN��
    public static AfTimeSpan FromHours(double value)
    {
        return Interval(value, 0x36ee80);
    }
    //
    // ժҪ:
    //     ���ر�ʾָ���������� System.TimeSpan��
    //
    // ����:
    //   value:
    //     ��������
    //
    // ���ؽ��:
    //     ��ʾ value �Ķ���
    //
    // �쳣:
    //   System.OverflowException:
    //     value С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue��- �� -value
    //     Ϊ System.Double.PositiveInfinity��- �� -value Ϊ System.Double.NegativeInfinity��
    //
    //   System.ArgumentException:
    //     value ���� System.Double.NaN��
    public static AfTimeSpan FromMilliseconds(double value)
    {
        return Interval(value, 1);
    }
    //
    // ժҪ:
    //     ���ر�ʾָ���������� System.TimeSpan�����жԷ�������ָ����ȷ����ӽ��ĺ��롣
    //
    // ����:
    //   value:
    //     ����������ȷ����ӽ��ĺ��롣
    //
    // ���ؽ��:
    //     ��ʾ value �Ķ���
    //
    // �쳣:
    //   System.OverflowException:
    //     value С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue��- �� -value
    //     Ϊ System.Double.PositiveInfinity��- �� -value Ϊ System.Double.NegativeInfinity��
    //
    //   System.ArgumentException:
    //     value ���� System.Double.NaN��
    public static AfTimeSpan FromMinutes(double value)
    {
        return Interval(value, 0xea60);
    }
    //
    // ժҪ:
    //     ���ر�ʾָ�������� System.TimeSpan�����ж�������ָ����ȷ����ӽ��ĺ��롣
    //
    // ����:
    //   value:
    //     ��������ȷ����ӽ��ĺ��롣
    //
    // ���ؽ��:
    //     ��ʾ value �Ķ���
    //
    // �쳣:
    //   System.OverflowException:
    //     value С�� System.TimeSpan.MinValue ����� System.TimeSpan.MaxValue��- �� -value
    //     Ϊ System.Double.PositiveInfinity��- �� -value Ϊ System.Double.NegativeInfinity��
    //
    //   System.ArgumentException:
    //     value ���� System.Double.NaN��
    public static AfTimeSpan FromSeconds(double value)
    {
        return Interval(value, 0x3e8);
    }
    //
    // ժҪ:
    //     ���ر�ʾָ��ʱ��� System.TimeSpan�����ж�ʱ���ָ���Կ̶�Ϊ��λ��
    //
    // ����:
    //   value:
    //     ��ʾʱ��Ŀ̶�����
    //
    // ���ؽ��:
    //     ��ʾ value �Ķ���
    public static AfTimeSpan FromTicks(long value){
        return new AfTimeSpan(value);
    }
    //
    // ժҪ:
    //     ���ر�ʾָ��ʱ��� System.TimeSpan�����ж�ʱ���ָ���Կ̶�Ϊ��λ��
    //
    // ����:
    //   t1 t2:
    //     һǰһ���ʱ�䡣
    //
    // ���ؽ��:
    //     ��ʾ t2 - t1 ��ʱ��
    public static AfTimeSpan FromDate(Date t1,Date t2){
        return new AfTimeSpan(t2.getTime() - t1.getTime());
    }
    //
    // ժҪ:
    //     ���ر�ʾָ��ʱ��� System.TimeSpan�����ж�ʱ���ָ���Կ̶�Ϊ��λ��
    //
    // ����:
    //   last:
    //     ��һ��ʱ�䡣
    //
    // ���ؽ��:
    //     ��ʾ last - now�����ڣ� ��ʱ��
    public static AfTimeSpan FromLast(Date last){
        return new AfTimeSpan(new Date().getTime() - last.getTime());
    }
    
    private static AfTimeSpan Interval(double value, int scale)
    {
        double num = value * scale;
        double num2 = num + ((value >= 0.0) ? 0.5 : -0.5);
        return new AfTimeSpan(((long) num2));
    }

     

}
