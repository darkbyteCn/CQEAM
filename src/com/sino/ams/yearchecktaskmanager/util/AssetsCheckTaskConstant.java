package com.sino.ams.yearchecktaskmanager.util;


public  interface AssetsCheckTaskConstant {

	//��������
	//��һ������
	String ORDER_NAME ="����̵�����";
	//����������
	String ORDER_NAME_NON_ADDRESS ="��ʵ���ʲ�����̵�����";
	String ORDER_NAME_ADDRESS_WIRELESS ="ʵ�������ʲ�����̵�����";
	String ORDER_NAME_ADDRESS_NON_WIRELESS ="ʵ�ط������ʲ�����̵�����";
	
	//�ʲ�����
	String ASSETS_TYPE_NON_ADDRESS ="ASSETS_TYPE_NON_ADDRESS"; //��ʵ��
	String ASSETS_TYPE_ADDRESS_WIRELESS ="ASSETS_TYPE_ADDRESS_WIRELESS"; //ʵ������
	String ASSETS_TYPE_ADDRESS_NON_WIRELESS ="ASSETS_TYPE_ADDRESS_NON_WIRELESS"; //ʵ�ط�����
	
	//��ʵ���ʲ��·���ʽ
	String NON_ADDRESS_METHOD_FOR_CITY_MANAGER = "NON_ADDRESS_METHOD_FOR_CITY_MANAGER"; //�¸����й���Ա
	String NON_ADDRESS_METHOD_FOR_DEPT_MANAGER = "NON_ADDRESS_METHOD_FOR_DEPT_MANAGER"; //�¸����в��Ź���Ա
	String NON_ADDRESS_METHOD_FOR_SOME_PERSON ="NON_ADDRESS_METHOD_FOR_SOME_PERSON";//�¸��ʲ������˻����ض���Ա
	
	//��ʵ���ʲ�����
	String NON_ADDRESS_CATEGORY_SOFTWIRE = "NON_ADDRESS_CATEGORY_SOFTWARE";//�����
	String NON_ADDRESS_CATEGORY_CLIENT = "NON_ADDRESS_CATEGORY_CLIENT";//�ͻ�����
	String NON_ADDRESS_CATEGORY_PIPELINE ="NON_ADDRESS_CATEGORY_PIPELINE";//���¡���·���ܵ�
	
	// ���񹤵�����
	 String ORDER_TYPE_ASS_CHK_TASK = "ASS-CHK-TASK"; // �̵����񹤵�����
	 //String ORDER_TYPE_NON_ADDRESS = "NON-ADDRESS"; // ��ʵ���̵����񹤵�
	 //��ʵ���ʲ�
	 String ORDER_TYPE_NON_ADDRESS_SOFTWARE= "NON-ADDRESS-SOFTWARE"; // ��ʵ��[�����]
	 String ORDER_TYPE_NON_ADDRESS_CLIENT = "NON-ADDRESS-CLIENT"; // ��ʵ��[�ͻ�����]
	 String ORDER_TYPE_NON_ADDRESS_PIPELINE = "NON-ADDRESS-PIPELINE"; // ��ʵ��[���¡���·���ܵ���]
	 //ʵ���ʲ�
	 String ORDER_TYPE_ADDRESS_WIRELESS = "ADDRESS-WIRELESS"; // ʵ��������
	 String ORDER_TYPE_ADDRESS_NON_WIRELESS = "ADDRESS-NON-WIRELESS";// ʵ�ط�������

	// �Ѵ��������·���ִ���У���ɣ�����
	 String DO_CREATE = "DO_CREATE";
	 String DO_SEND = "DO_SEND";
	 String DO_COMPLETE = "DO_COMPLETE";
	 String DO_CANCLE = "DO_CANCLE";
	 
	 String YEAR_TASK_NAME = "����̵�����";
	 String LOOK_UP_CITY_MANAGER = "LOOK_UP_CITY_MANAGER"; //���ҵ��й�˾�ʲ�����Ա
	 String ORDER_NUMBER = "�·����Զ�����";
	 
	 //��������˽�ɫ
	 String ORDER_RECIVE_ROLE_TO_CITY = "��˾�ʲ�����Ա"; //ʡ������
	 String ORDER_RECIVE_ROLE_TO_DEPT_FOR_NON_ADDRESS = "�����ʲ�����Ա"; //���е�����,��ʵ���ʲ�
	 String ORDER_RECIVE_ROLE_TO_DEPT_FOR_ADDRESS_WIRELESS = "ʵ�������ʲ��̵�������"; //ʵ������
	 String ORDER_RECIVE_ROLE_TO_DEPT_FOR_ADDRESS_NON_WIRELESS = "�����ʲ�����Ա"; //ʵ�ط�����
	 String ORDER_RECIVE_ROLE_TO_COMMON_USER = "ϵͳ�û�"; //���ļ�����
	 
	 //�ʲ�����
	 String ASSETS_BIG_CLASS_MIS = "MIS"; //����
	 String ASSETS_BIG_CLASS_TD = "TD"; //TD
	 String ASSETS_BIG_CLASS_TF = "TF"; //ͨ��
	 String ASSETS_BIG_CLASS_TT = "TT"; //��ͨ
	 String ASSETS_BIG_CLASS_ALL = "ALL"; //ȫ��
	 
//jeffery
	 
	 String CHECKED="CHECKED";//�̵�
	 
	 String CHECKED_NAME="�̵�";
	 
	 String CHECKED_LOSS="CHECKED_LOSS";//�̿�
	 
	 String CHECKED_LOSS_NAME="�̿�";
	 
	 String CHECKED_NOT_MACTH="CHECKED_NOT_MACTH";//��Ϣ����
	 
	 String CHECKED_NOT_MACTH_NAME="��Ϣ����";
	 
	 String DO_RETURN = "DO_RETURN";//���˻�
	 

}
