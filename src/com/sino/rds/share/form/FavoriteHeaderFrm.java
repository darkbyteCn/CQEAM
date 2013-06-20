package com.sino.rds.share.form;

import com.sino.base.ExtendedArrayList;
import com.sino.rds.appbase.form.RDSBaseFrm;
import com.sino.rds.foundation.web.component.WebOptions;

import java.util.List;

/**
 * <p>Title: 报表数据模型 RDS_FAVORITE_HEADER</p>
 * <p>Description: 代码工具自动生成DTO数据传输对象</p>
 * <p>Copyright: 北京思诺博信息科技有限公司 Copyright (c) 2006</p>
 * <p>Company: 北京思诺博信息科技有限公司</p>
 *
 * @author 唐明胜
 * @version 1.0
 */

public class FavoriteHeaderFrm extends RDSBaseFrm {
    private String favoriteRemark = "";
    private String favoriteName = "";
    private String headerId = "";
    private String reportName = "";
    private String[] reportIds = null;
    private List<FavoriteLineFrm> lines = new ExtendedArrayList(FavoriteLineFrm.class);
    private WebOptions myFavoriteOptions = null;

    public String getFavoriteRemark() {
        return favoriteRemark;
    }

    public void setFavoriteRemark(String favoriteRemark) {
        this.favoriteRemark = favoriteRemark;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    public List<FavoriteLineFrm> getLines() {
        return lines;
    }

    public void setLines(List<FavoriteLineFrm> lines) {
        this.lines = lines;
    }

    public WebOptions getMyFavoriteOptions() {
        return myFavoriteOptions;
    }

    public void setMyFavoriteOptions(WebOptions myFavoriteOptions) {
        this.myFavoriteOptions = myFavoriteOptions;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String[] getReportIds() {
        return reportIds;
    }

    public void setReportIds(String[] reportIds) {
        this.reportIds = reportIds;
    }
}