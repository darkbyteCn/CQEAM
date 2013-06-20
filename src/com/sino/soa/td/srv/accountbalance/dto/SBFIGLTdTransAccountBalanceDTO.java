package com.sino.soa.td.srv.accountbalance.dto;

import com.sino.base.dto.CheckBoxDTO;

/**
 * Created by IntelliJ IDEA.
 * User: T_suhuipeng
 * Date: 2011-10-13
 * Time: 11:33:36
 * To change this template use File | Settings | File Templates.
 */
public class SBFIGLTdTransAccountBalanceDTO extends CheckBoxDTO {

    private String periodName = "";
    private String currencyCode = "";
    private String actualFlag = "";
    private String codeCombinationId = null;
    private String segment1 = "";
    private String segment2 = "";
    private String segment3 = "";
    private String segment4 = "";
    private String segment5 = "";
    private String segment6 = "";
    private String segment7 = "";
    private String beginBalanceDr = null;
    private String beginBalanceCr = null;
    private String beginBalance = null;
    private String periodNetDr = null;
    private String periodNetCr = null;
    private String periodNet = null;
    private String endBalanceDr = null;
    private String endBalanceCr = null;
    private String endBalance = null;
    private String structuredHierarchyNameCom = "";
    private String structuredHierarchyNameCos = "";
    private String setOfBooks = "";

    private String accountType = "";
    private String companyCode = "";
    private String concatenatedSemgmentsFrom = "";

	private String bookTypeCode = "";
	private String startLastUpdateDate = "";
	private String endLastUpdateDate = "";
    private String assetsType = "";

    public SBFIGLTdTransAccountBalanceDTO() {
        super();
    }

    /**
     * 功能：设置科目余额服务属性 对应的会计期间
     * @param periodName String
     */
    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    /**
     * 功能：设置科目余额服务属性 币种代码
     * @param currencyCode String
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * 功能：设置科目余额服务属性 金额类型
     * @param actualFlag String
     */
    public void setActualFlag(String actualFlag) {
        this.actualFlag = actualFlag;
    }

    /**
     * 功能：设置科目余额服务属性 科目组合ID
     * @param codeCombinationId String
     */
    public void setCodeCombinationId(String codeCombinationId) {
        this.codeCombinationId = codeCombinationId;
    }

    /**
     * 功能：设置科目余额服务属性 公司代码
     * @param segment1 String
     */
    public void setSegment1(String segment1) {
        this.segment1 = segment1;
    }

    /**
     * 功能：设置科目余额服务属性 成本中心代码
     * @param segment2 String
     */
    public void setSegment2(String segment2) {
        this.segment2 = segment2;
    }

    /**
     * 功能：设置科目余额服务属性 科目代码
     * @param segment3 String
     */
    public void setSegment3(String segment3) {
        this.segment3 = segment3;
    }

    /**
     * 功能：设置科目余额服务属性 品牌代码
     * @param segment4 String
     */
    public void setSegment4(String segment4) {
        this.segment4 = segment4;
    }

    /**
     * 功能：设置科目余额服务属性 项目代码
     * @param segment5 String
     */
    public void setSegment5(String segment5) {
        this.segment5 = segment5;
    }

    /**
     * 功能：设置科目余额服务属性 往来代码
     * @param segment6 String
     */
    public void setSegment6(String segment6) {
        this.segment6 = segment6;
    }

    /**
     * 功能：设置科目余额服务属性 备用代码
     * @param segment7 String
     */
    public void setSegment7(String segment7) {
        this.segment7 = segment7;
    }

    /**
     * 功能：设置科目余额服务属性 期初借方余额
     * @param beginBalanceDr String
     */
    public void setBeginBalanceDr(String beginBalanceDr) {
        this.beginBalanceDr = beginBalanceDr;
    }

    /**
     * 功能：设置科目余额服务属性 期初贷方余额
     * @param beginBalanceCr String
     */
    public void setBeginBalanceCr(String beginBalanceCr) {
        this.beginBalanceCr = beginBalanceCr;
    }

    /**
     * 功能：设置科目余额服务属性 期初余额
     * @param beginBalance String
     */
    public void setBeginBalance(String beginBalance) {
        this.beginBalance = beginBalance;
    }

    /**
     * 功能：设置科目余额服务属性 本期借方发生额
     * @param periodNetDr String
     */
    public void setPeriodNetDr(String periodNetDr) {
        this.periodNetDr = periodNetDr;
    }

    /**
     * 功能：设置科目余额服务属性 本期贷方发生额
     * @param periodNetCr String
     */
    public void setPeriodNetCr(String periodNetCr) {
        this.periodNetCr = periodNetCr;
    }

    /**
     * 功能：设置科目余额服务属性 本期发生数
     * @param periodNet String
     */
    public void setPeriodNet(String periodNet) {
        this.periodNet = periodNet;
    }

    /**
     * 功能：设置科目余额服务属性 期末借方余额
     * @param endBalanceDr String
     */
    public void setEndBalanceDr(String endBalanceDr) {
        this.endBalanceDr = endBalanceDr;
    }

    /**
     * 功能：设置科目余额服务属性 期末贷方余额
     * @param endBalanceCr String
     */
    public void setEndBalanceCr(String endBalanceCr) {
        this.endBalanceCr = endBalanceCr;
    }

    /**
     * 功能：设置科目余额服务属性 期末数
     * @param endBalance String
     */
    public void setEndBalance(String endBalance) {
        this.endBalance = endBalance;
    }

    /**
     * 功能：设置科目余额服务属性 公司层级
     * @param structuredHierarchyNameCom String
     */
    public void setStructuredHierarchyNameCom(String structuredHierarchyNameCom) {
        this.structuredHierarchyNameCom = structuredHierarchyNameCom;
    }

    /**
     * 功能：设置科目余额服务属性 成本中心层级
     * @param structuredHierarchyNameCos String
     */
    public void setStructuredHierarchyNameCos(String structuredHierarchyNameCos) {
        this.structuredHierarchyNameCos = structuredHierarchyNameCos;
    }


    /**
     * 功能：获取科目余额服务属性 对应的会计期间
     * @return String
     */
    public String getPeriodName() {
        return this.periodName;
    }

    /**
     * 功能：获取科目余额服务属性 币种代码
     * @return String
     */
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * 功能：获取科目余额服务属性 金额类型
     * @return String
     */
    public String getActualFlag() {
        return this.actualFlag;
    }

    /**
     * 功能：获取科目余额服务属性 科目组合ID
     * @return String
     */
    public String getCodeCombinationId() {
        return this.codeCombinationId;
    }

    /**
     * 功能：获取科目余额服务属性 公司代码
     * @return String
     */
    public String getSegment1() {
        return this.segment1;
    }

    /**
     * 功能：获取科目余额服务属性 成本中心代码
     * @return String
     */
    public String getSegment2() {
        return this.segment2;
    }

    /**
     * 功能：获取科目余额服务属性 科目代码
     * @return String
     */
    public String getSegment3() {
        return this.segment3;
    }

    /**
     * 功能：获取科目余额服务属性 品牌代码
     * @return String
     */
    public String getSegment4() {
        return this.segment4;
    }

    /**
     * 功能：获取科目余额服务属性 项目代码
     * @return String
     */
    public String getSegment5() {
        return this.segment5;
    }

    /**
     * 功能：获取科目余额服务属性 往来代码
     * @return String
     */
    public String getSegment6() {
        return this.segment6;
    }

    /**
     * 功能：获取科目余额服务属性 备用代码
     * @return String
     */
    public String getSegment7() {
        return this.segment7;
    }

    /**
     * 功能：获取科目余额服务属性 期初借方余额
     * @return String
     */
    public String getBeginBalanceDr() {
        return this.beginBalanceDr;
    }

    /**
     * 功能：获取科目余额服务属性 期初贷方余额
     * @return String
     */
    public String getBeginBalanceCr() {
        return this.beginBalanceCr;
    }

    /**
     * 功能：获取科目余额服务属性 期初余额
     * @return String
     */
    public String getBeginBalance() {
        return this.beginBalance;
    }

    /**
     * 功能：获取科目余额服务属性 本期借方发生额
     * @return String
     */
    public String getPeriodNetDr() {
        return this.periodNetDr;
    }

    /**
     * 功能：获取科目余额服务属性 本期贷方发生额
     * @return String
     */
    public String getPeriodNetCr() {
        return this.periodNetCr;
    }

    /**
     * 功能：获取科目余额服务属性 本期发生数
     * @return String
     */
    public String getPeriodNet() {
        return this.periodNet;
    }

    /**
     * 功能：获取科目余额服务属性 期末借方余额
     * @return String
     */
    public String getEndBalanceDr() {
        return this.endBalanceDr;
    }

    /**
     * 功能：获取科目余额服务属性 期末贷方余额
     * @return String
     */
    public String getEndBalanceCr() {
        return this.endBalanceCr;
    }

    /**
     * 功能：获取科目余额服务属性 期末数
     * @return String
     */
    public String getEndBalance() {
        return this.endBalance;
    }

    /**
     * 功能：获取科目余额服务属性 公司层级
     * @return String
     */
    public String getStructuredHierarchyNameCom() {
        return this.structuredHierarchyNameCom;
    }

    /**
     * 功能：获取科目余额服务属性 成本中心层级
     * @return String
     */
    public String getStructuredHierarchyNameCos() {
        return this.structuredHierarchyNameCos;
    }

	/**
	 * @return the setOfBooks
	 */
	public String getSetOfBooks() {
		return setOfBooks;
	}

	/**
	 * @param setOfBooks the setOfBooks to set
	 */
	public void setSetOfBooks(String setOfBooks) {
		this.setOfBooks = setOfBooks;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the concatenatedSemgmentsFrom
	 */
	public String getConcatenatedSemgmentsFrom() {
		return concatenatedSemgmentsFrom;
	}

	/**
	 * @param concatenatedSemgmentsFrom the concatenatedSemgmentsFrom to set
	 */
	public void setConcatenatedSemgmentsFrom(String concatenatedSemgmentsFrom) {
		this.concatenatedSemgmentsFrom = concatenatedSemgmentsFrom;
	}

	/**
	 * @return the bookTypeCode
	 */
	public String getBookTypeCode() {
		return bookTypeCode;
	}

	/**
	 * @param bookTypeCode the bookTypeCode to set
	 */
	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	/**
	 * @return the endLastUpdateDate
	 */
	public String getEndLastUpdateDate() {
		return endLastUpdateDate;
	}

	/**
	 * @param endLastUpdateDate the endLastUpdateDate to set
	 */
	public void setEndLastUpdateDate(String endLastUpdateDate) {
		this.endLastUpdateDate = endLastUpdateDate;
	}

	/**
	 * @return the startLastUpdateDate
	 */
	public String getStartLastUpdateDate() {
		return startLastUpdateDate;
	}

	/**
	 * @param startLastUpdateDate the startLastUpdateDate to set
	 */
	public void setStartLastUpdateDate(String startLastUpdateDate) {
		this.startLastUpdateDate = startLastUpdateDate;
	}

	public String getAssetsType() {
		return assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

}