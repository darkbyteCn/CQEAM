package com.sino.ams.constant;

/**
 * <p>Title: SinoEAM</p>
 * <p>Description: ɽ���ƶ�ʵ���ʲ�����ϵͳ</p>
 * <p>Copyright: ����˼ŵ����Ϣ�������޹�˾��Ȩ����Copyright (c) 2007</p>
 * <p>Company: ����˼ŵ����Ϣ�������޹�˾</p>
 * @author ����ʤ
 * @version 1.0
 */

public interface DictConstant {
    //start==============================�ֵ����=================================//
    String ITEM_TYPE = "ITEM_TYPE"; //��������
    String OBJECT_CATEGORY = "OBJECT_CATEGORY"; //�ص�����
    String WORKORDER_TYPE = "WORKORDER_TYPE"; //��������
    String WORKORDER_STATUS = "WORKORDER_STATUS"; //����״̬
    String INV_TYPE = "INV_TYPE"; //�ֿ�����
    String SPREAD_TYPE = "SPREAD_TYPE"; //�������跽ʽ
    String CABEL_USAGE = "CABEL_USAGE"; //������;
    String ORDER_STATUS = "ORDER_STATUS"; //����״̬
    String ASSETS_BUSINESS_TYPE = "ASSETS_BUSINESS_TYPE"; //�ʲ�ҵ������
    String INV_BUSINESS_TYPE = "INV_BUSINESS_TYPE"; //���ҵ������
    String ORDER_TYPE_ASSETS = "ORDER_TYPE_ASSETS"; //�ʲ���������
    String ORDER_TYPE_SPARE = "ORDER_TYPE_SPARE"; //��浥������
    String FLOW_STATUS = "FLOW_STATUS"; //����ҵ��״̬
    String PROCESS_ACTION = "PROCESS_ACTION"; //���ݴ�������
    String ORDER_ITEM_STATUS = "ORDER_ITEM_STATUS"; //�����豸״̬
    String INS_BUSINESS_TYPE = "INS_BUSINESS_TYPE"; //�����Ǳ�ҵ������
    String TIME_MEASURE_UNIT = "TIME_MEASURE_UNIT"; //ʱ�������λ
    String PLAN_STATUS = "PLAN_STATUS"; //�ƻ�״̬
    String UNIT_OF_MEASURE = "UNIT_OF_MEASURE"; //������λ
    String CHECK_MODLE = "CHECK_MODLE"; //Ѳ��ģʽ
    String PROJECT_TYPE = "PROJECT_TYPE"; //��Ŀ����
    String PROJECT_STATUS = "PROJECT_STATUS"; //��Ŀ״̬
    String INV_PRIVI = "INV_PRIVI"; //���Ȩ��
    String FINANCE_PROP = "FINANCE_PROP"; //��������
    String ASSETS_STATUS = "ASSETS_STATUS"; //�����ʲ�״̬
    String OU_STATUS = "OU_STATUS"; //ȫ��OU
    String PAY_TYPE = "PAY_TYPE";  //���ʽ
    String LAND_AREA_UNIT = "LAND_AREA_UNIT";   //���ص�λ ���ػ���
    String AREA_UNIT = "AREA_UNIT";//�����λ
    String HOUSE = "HOUSE"; //����
    String LAND = "LAND";   //����
    String INTANGIBLE = "INTANGIBLE"; //�����ʲ�
    String RENT = "RENT";//�����ʲ���Ϣ
    String DG = "DG";//�����ʲ�
    String ITEM_STATUS = "ITEM_STATUS";//�豸״̬
    String HOUSE_USAGE = "HOUSE_USAGE";//������;
    String HOUSE_STATUS = "HOUSE_STATUS";//����״̬
    String SPARE_REASON = "SPARE_REASON";//���������ԭ��
//end==============================�ֵ����=================================//

//===================�����ֵ�=============================

    //start=================================��������===========================//
    String ORDER_TYPE_NEW = "10"; //�½�����
    String ORDER_TYPE_EXT = "11"; //���ݹ���
    String ORDER_TYPE_CHECK = "12"; //Ѳ�칤��
    String ORDER_TYPE_FIX = "13"; //ά�޹���
    String ORDER_TYPE_TRANS = "14"; //��Ǩ����
    String ORDER_TYPE_SUB = "15"; //���ݹ���
    String ORDER_TYPE_REP = "16"; //�滻����
    String ORDER_TYPE_DB = "17"; //��������
    String ORDER_TYPE_HDV = "18"; //���ӹ���
//    String ORDER_TYPE_ZERO ="19"; //�㹺����
    String ZERO_PRJ_ID="d87500c590d34fd3882dd1a73757ef0d";//�㹺�������⹤�̱��
//end=================================��������===========================//

    //start=================================����״̬===========================//
    String WORKORDER_STATUS_NEW = "10"; //����
    String WORKORDER_STATUS_DISTRUIBUTED = "11"; //���·�
    String WORKORDER_STATUS_DOWNLOADED = "12"; //������
    String WORKORDER_STATUS_UPLOADED = "13"; //���ϴ�
    String WORKORDER_STATUS_ACHIEVED = "14"; //�ѹ鵵
    String WORKORDER_STATUS_CANCELE = "15"; //��ȡ��
//end=================================����״̬===========================//

    //start===============================רҵ����===========================//
    String CATEGORY_BTS = "BTS"; //��վ
    String CATEGORY_DATA = "DATA"; //����
    String CATEGORY_ELEC = "ELEC"; //����
    String CATEGORY_EXCHG = "EXCHG"; //����
    String CATEGORY_BSC = "BSC"; //BSC��ؽ���
    String CATEGORY_TRANS = "TRANS"; //����
    String CATEGORY_NETOPT = "NETOPT"; //����
    String CATEGORY_HOUSE = "HOUSE"; //���޷���
    String CATEGORY_LAND = "LAND"; //��������
    String CATEGORY_INSTRU = "INSTRUMENT"; //�����Ǳ�
//end===============================רҵ����===========================//

    //start====================�����ڵ�(�ݶ�--2007-09-22 zhoujs)============//
    String WORKORDER_NODE = "WORKORDER_NODE";
    String WORKORDER_NODE_NEW = "PROCESS1"; //�Ǽ�
    String WORKORDER_NODE_APPROVE = "PROCESS2"; //���
    String WORKORDER_NODE_DISTRUIBUTE = "PROCESS3"; //�·�
    String WORKORDER_NODE_DOWNLOADED = "PROCESS4"; //������
    String WORKORDER_NODE_UPLODADED = "PROCESS5"; //��ɨ���ϴ�
    String WORKORDER_NODE_ACHIEVE = "PROCESS6"; //�·�
    String WORKORDER_NODE_CANCEL = "PROCESS7"; //����
//end======================�����ڵ�(�ݶ�--2007-09-22 zhoujs)===========//

    //start=========================������ѯ����===============================//
    String WOR_STATUS_NEW = "10"; //����
    String WOR_STATUS_DEPLOY = "11"; //���·�
    String WOR_STATUS_DOWNLOAD = "12"; //������
    String WOR_STATUS_UPLOAD = "13"; //���ϴ�
    String WOR_STATUS_ARCHIVED = "14"; //�ѹ鵵
    String WOR_STATUS_CANCELED = "15"; //��ȡ��
    String WOR_STATUS_OVERTIME = "1";    //��ʱ
    String WOR_STATUS_INTEGAZATION = "76";    //�����ۺϲ�ѯ
    String WOR_STATUS_BATCH = "2";     //��������������ѯ
//end===========================����״̬===============================//

    //start=========================���̴�������===========================//
    String FLOW_SAVE = "FLOW_SAVE"; //�ݴ�
    String FLOW_COMPLETE = "FLOW_COMPLETE"; //���
    String FLOW_TO = "FLOW_TO"; //����
    String FLOW_BACK = "FLOW_BACK"; //�˻�
    String FLOW_CANCEL = "FLOW_CANCEL"; //����
//end=========================���̴�������===========================//

    //start=============================�ʲ�������������======================//
//    String ASSETS_FLOW_REDEPLOY = "RE_DEPLOY"; //�ʲ���������//�ó�����������
//    String ASSETS_FLOW_CLEAR = "CLEAR"; //�ʲ���������//�ó�����������
//    String ASSETS_FLOW_DISCARD = "DISCARD"; //�ʲ���������//�ó�����������
//    String ASSETS_FLOW_DOTATION = "DOTATION"; //�ʲ���������//�ó�����������
//end=============================�ʲ�������������======================//

    //start===============����״̬(�ʲ�ҵ�񡢱���ҵ��=========================//
    String SAVE_TEMP = "SAVE_TEMP"; //�ݴ�
    String IN_PROCESS = "IN_PROCESS"; //������
    String REJECTED = "REJECTED"; //�˻�
    String APPROVED = "APPROVED"; //������--�����������
    String COMPLETED = "COMPLETED"; //���--���ݴ����ҵ�����
    String CANCELED = "CANCELED"; //����
    String CREATE = "CREATE"; //�½�
    String ACCEPTED = "ACCEPTED"; //������
    String SCANING = "SCANING"; //��ɨ��
    String RECEIVED = "RECEIVED";
    String ASSIGNED = "ASSIGNED";
    String CONFIRMD = "CONFIRMD";
    String DISTRIBUTED = "DISTRIBUTED";
    String DOWNLOADED = "DOWNLOADED";
    String UPLOADED = "UPLOADED";
    String ARCHIEVED = "ARCHIEVED";

//end===============����״̬(�ʲ�ҵ�񡢱���ҵ��=========================//

    //start===============�ʲ�����ص�����=========================//
    String NETADDR_BTS = "10"; //��վ�ص�
    String NETADDR_DATA = "50"; //���ݻ���
    String NETADDR_TRANS = "40"; //�������
    String NETADDR_EXCHG = "20"; //��������
    String NETADDR_BSC = "30"; //BSC��ػ���
    String NETADDR_NETOPT = "15"; //���ŵص�
    String NETADDR_ELE = "60"; //��������
    String NETADDR_OTHERS = "80";//�����ص�
//end===============�ʲ�����ص�����=========================//

    //start===============�����ֿ�����=========================//
    String INV_NORMAL = "71"; //������
    String INV_TO_REPAIR = "72"; //���޿�
    String INV_SEND_REPAIR = "73"; //���޿�
    String INV_ON_WAY = "74"; //��;��
    String INV_DISCARD = "75"; //���Ͽ�
//end===============�����ֿ�����=========================//

    //start===============�������跽ʽ=========================//
    String CABEL_COVER_LINE = "1"; //ֱ��
    String CABEL_IN_SKY = "2"; //�ܿ�
    String CABEL_IN_WATER = "3"; //ˮ��
//end===============�������跽ʽ=========================//

    //start===============������;=========================//
    String CABEL_USAGE_1 = "1"; //һ������
    String CABEL_USAGE_2 = "2"; //��������
    String CABEL_USAGE_LOCAL = "3"; //��������·
//end===============������;=========================//

    //start===============�ʲ���������=========================//
    String ASS_RED = "ASS-RED"; //������
    String ASS_DIS = "ASS-DIS"; //���ϵ�
    String ASS_DIS_OTHER = "ASS-DIS-OTHER"; //���ϵ�
    String ASS_FAV = "ASS-FAV"; //���뵥
    String ASS_CLR = "ASS-CLR"; //����
    String ASS_FREE = "ASS-FREE";//���õ�
    String ASS_RFU = "ASS-RFU"; //����������(������)
    String ASS_DEVALUE = "ASS-DEVALUE"; //��ֵ��
//end===============�ʲ���������=========================//

    //start===============������������=========================//
    String BJBF = "BJBF"; //���ϵ�
    String BJCK = "BJCK"; //���ⵥ
    String BJDB = "BJDB"; //������
    String BJFH = "BJFH"; //���޷�����
    String BJFK = "BJFK"; //����
    String BJRK = "BJRK"; //�¹���ⵥ
    String BJSL = "BJSL"; //���쵥
    String BJSX = "BJSX"; //���޵�
    String BJFP = "BJFP"; //����
    String BJGH = "BJGH"; //�黹,����NM
    String TMRK = "TMRK"; //���������豸���
    String TMCK = "TMCK"; //���������豸����
    String FTMRK = "FTMRK"; //�����������豸���
    String FTMCK = "FTMCK"; //�����������豸����
    String BJPD = "BJPD"; //�����̵�
    String FXSQ = "FXSQ"; //��������
    String ZKZY = "ZKZY"; //�ӿ�ת��
    String JCHG = "JCHG";//���ϸ�
    String HJGH = "HJGH";//ʡ��˾����Ӧ���黹
    String BFHS = "BFHS";//���ϻ���
//end===============������������=========================//

    //start===============�豸ɨ��״̬=========================//
    String SCAN_STATUS_NEW = "0"; //����
    String SCAN_STATUS_EXISTS = "5"; //��
    String SCAN_STATUS_NONE = "6"; //��
    String SCAN_STATUS_OFFLINE = "7"; //�����豸
    String SCAN_STATUS_REMAIN = "8";//ʣ���豸
//end===============�豸ɨ��״̬=========================//

    //start===============�����ƻ�״̬=========================//
    String PLAN_STUS_NEW = "1"; //�½�
    String PLAN_STUS_TIMEDOUT = "3"; //����
    String PLAN_STUS_RECEIVED = "4"; //�ѽ���
//end===============�����ƻ�״̬=========================//

    //start===============������Ŀ״̬=========================//
    String PRJ_STS_APPROVED = "APPROVED"; //������
    String PRJ_STS_CLOSED = "CLOSED"; //�ѹر�
    String PRJ_STS_UNAPPROVED = "UNAPPROVED"; //δ����
//end===============������Ŀ״̬=========================//

    //start===============���Ȩ��=========================//
    String INV_PRIVI_APP = "INV_APPLY"; //��������
    String INV_PRIVI_OUT = "INV_OUT"; //��������
    String INV_PRIVI_RPI = "INV_REPAIR_IN"; //���޷���
    String INV_PRIVI_RVI = "INV_RCV_IN"; //�������
    String INV_PRIVI_BDI = "INV_BAD_IN"; //�������
    String INV_PRIVI_BDR = "INV_BAD_RETURN"; //�����黹
    String INV_PRIVI_QRY = "INV_QUERY"; //������ѯ
    String INV_PRIVI_ODP = "INV_ORDER_PRINT"; //���ݴ�ӡ
//end===============���Ȩ��=========================//

    //start===============�豸��������=========================//
    String FIN_PROP_PRJ = "PRJ_MTL"; //��������
    String FIN_PROP_ASSETS = "ASSETS"; //�ʲ�
    String FIN_PROP_SPARE = "SPARE"; //����
    String FIN_PROP_OTHER = "OTHER"; //����
    String FIN_PROP_UNKNOW = "UNKNOW";  //δ֪
    String FIN_PROP_RENT = "RENT_ASSETS"; //����
    String FIN_PROP_DG = "DG_ASSETS"; //ͨ����ͨ�ʲ�,����
    String FIN_PROP_QD = "QD_ASSETS"; //ͨ�������ʲ�
    String FIN_PROP_TT = "TT_ASSETS"; //��ͨ�ʲ�
    String FIN_PROP_DH = "DH_ASSETS"; //��ֵ�׺�
    String FIN_PROP_TD = "TD_ASSETS";//TD�ʲ�
//end===============�豸��������=========================//

    //start===============���ר��=========================//
    String HAS_PRIVI_YES = "1";
    String HAS_PRIVI_NO = "0";
    //end===============���ר��=========================//
    //start===============�Ǿ�=========================//
    String INS_BRW = "INS-BRW";   //����
    String INS_RET = "INS-RET";   //����
    String INS_CHECK = "INS-CHK";
    String INS_REP = "INS-REP";  //����
    String INS_VRE = "INS-VRE";//��Ӧ�̷���

    String BORROW = "BORROW";    //����
    String DISCARDED = "DISCARDED";  //����
    String SEND_REPAIR = "SEND_REPAIR";//����
//end===============�Ǿ�=========================//

    //end===============ͳ�Ʊ���=========================//
    //start===============�������ͳ��=========================//
    String ITEM_REPAIR_QUERY = "1";    //�豸������-������
    String VENTOR_REPAIR_QUERY = "2";    //�豸������-������
    String REPAIR_STATISTIC_ITEM_NAME = "3";  //�豸������-������
    String REPAIR_STATISTIC_VENDOR = "4";  //�豸������-������
//end===============�������ͳ��=========================//

    //start===============�豸״̬=========================//
    String ITEM_STATUS_CLEARED = "CLEARED";//�Ѵ���
    String ITEM_STATUS_DISCARDED = "DISCARDED";//�ѱ���
    String ITEM_STATUS_TO_DISCARD_TRANS = "TO_DISCARD_TRANS";  //������-��˾�����
    String ITEM_STATUS_NORMAL = "NORMAL";//����
    String ITEM_STATUS_ON_WAY = "ON_WAY";//��;
    String ITEM_STATUS_SEND_REPAIR = "SEND_REPAIR";//���ޣ����ڱ���
    String ITEM_STATUS_TO_DISCARD = "TO_DISCARD";//�����ϣ����ڱ���
    String ITEM_STATUS_TO_REPAIR = "TO_REPAIR";//���ޣ����ڱ���
    String ITEM_STATUS_TO_ASSETS = "TO_ASSETS";//��ת��
    String ITEM_STATUS_PRE_ASSETS = "PRE_ASSETS";//Ԥת��
//end===============�豸״̬=========================//


    //start==========================��¼�豸�䶯��ʷ�ĵ�������============================//
    String LOG_ORDER_WORK = "1";//����
    String LOG_ORDER_INV = "2";//��浥��
    String LOG_ORDER_ASSETS = "3";//�ʲ�����
//end==========================��¼�豸�䶯��ʷ�ĵ�������============================//

    //begin====================ʡ��˾����======================
    String PROVINCE_CODE_NM = "42";//�����ƶ���˾ʡ����
    String PROVINCE_CODE_JIN = "41";//ɽ���ƶ���˾ʡ����
//    String PROVINCE_CODE_JIN = "411";//ɽ���ƶ���˾ʡ����
    String PROVINCE_CODE_SHAN = "40";//�����ƶ���˾ʡ����
    String PROVINCE_CODE_HN = "25"; //�����ƶ���˾ʡ����
	String PROVINCE_CODE_SHAN_BENBU = "4010";//�����ƶ���˾ʡ��������
//end===================ʡ��˾����==================

    String EXPORT_RES_LOC = "EXPORT_RES_LOC";//�������εص�
    String EXPORT_SCAN_LOC_Y = "EXPORT_SCAN_LOC_Y";//������Ѳ��ص�
    String EXPORT_SCAN_LOC_N = "EXPORT_SCAN_LOC_N";//����δѲ��ص�

    String EXPORT_LOC_ITEM = "EXPORT_LOC_ITEM";//������ǰ�ص��豸�嵥
    String EXPORT_SCAN_ITEM = "EXPORT_SCAN_ITEM";//�������Ѳ����
    String EXPORT_DIFF_ITEM = "EXPORT_DIFF_ITEM";//�����������

    String OWN_ITEM = "OWN_ITEM";//ӵ���豸
    String SCAN_ITEM_Y = "SCAN_ITEM_Y";//��Ѳ���豸
    String SCAN_ITEM_N = "SCAN_ITEM_N";//δѲ���豸

    String MLAND = "����";
    String MHOUSE = "����";
    String HOUSE_AND_LAND = "���غ�һ";
    String UNSETTLED = "δ����";
    String SETTLED = "�Ѵ���";

    String[] GRP_FLOW_VALUE = {"0", "1", "2"};
    String[] GRP_FLOW_CAPTI = {"��ͨ���", "�������", "�������"};

    String[] BOOLEAN_VALUE = {"Y", "N"};
    String[] BOOLEAN_CAPTI = {"��", "��"};

    int CAPITAL_COMPUTE = 1;
    
    String YES = "��";
    String NO = "��";

    //===================�豸״̬
    String STATUS_FREE = "FREE";
    String STATUS_DEVALUE = "DEVALUE";
    
    String ITEM_RED = "ITEM-RED";//ʵ�����

    
}
