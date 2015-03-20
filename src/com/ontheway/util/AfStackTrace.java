package com.ontheway.util;

/**
 * @Description: java��ջ������
 * 1.���Բ鿴��ǰ��������
 * 2.�����жϵ�ǰ�����Ƿ��Ѿ��ݹ����
 * @Author: scwang
 * @Version: V1.0, 2015-3-13 ����4:58:20
 * @Modified: ���δ���AfStackTrace��
 */
public class AfStackTrace {

	/**
	 * @Description: ��ȡ���� getCurrentStatck ��Statck
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-26 ����4:21:59
	 * @return ����getCurrentStatck��Statck
	 */
	public static StackTraceElement getCurrentStatck(){
		int index = 1;
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		StackTraceElement stack = stacks[index];
		if (!stack.getClassName().equals(AfStackTrace.class.getName())) {
			for (int i = 0; i < stacks.length; i++) {
				if (AfStackTrace.class.getName().equals(stacks[i].getClassName())) {
					index = i;
					stack = stacks[i];
					break;
				}
			}
		}
		index++;
		stack = stacks[index];
		return stack;
	}
	/**
	 * @Description: �жϵ���isLoopCall �ķ����Ƿ��Ѿ���ѭ���ݹ����
	 * @Author: scwang
	 * @Version: V1.0, 2015-2-26 ����4:18:47
	 * @return true �Ѿ��ݹ� false û�еݹ�
	 */
	public static boolean isLoopCall() {
		int index = 1;
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		StackTraceElement stack = stacks[index];
		if (!stack.getClassName().equals(AfStackTrace.class.getName())) {
			for (int i = 0; i < stacks.length; i++) {
				if (AfStackTrace.class.getName().equals(stacks[i].getClassName())) {
					index = i;
					stack = stacks[i];
					break;
				}
			}
		}
		index++;
		stack = stacks[index];
		for (int i = index + 1; i < stacks.length; i++) {
			StackTraceElement element = stacks[i];
			if (element.getClassName().equals(stack.getClassName())
					&& element.getMethodName().equals(stack.getMethodName())) {
				//System.out.println("�ݹ���");
				return true;
			}
		}
		return false;
	}
}
