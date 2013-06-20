package com.sino.ams.adjunct.business;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.sino.ams.adjunct.dto.FileDTO;
import com.sino.ams.adjunct.model.FileMaintenanceModel;
import com.sino.base.config.ConfigLoader;
import com.sino.base.config.UploadConfig;
import com.sino.base.constant.calen.CalendarConstant;
import com.sino.base.constant.calen.DateConstant;
import com.sino.base.data.Row;
import com.sino.base.data.RowSet;
import com.sino.base.db.query.SimpleQuery;
import com.sino.base.db.sql.model.SQLModel;
import com.sino.base.db.util.DBOperator;
import com.sino.base.db.util.SeqProducer;
import com.sino.base.dto.DTOSet;
import com.sino.base.exception.ConfigException;
import com.sino.base.exception.ContainerException;
import com.sino.base.exception.DTOException;
import com.sino.base.exception.DataHandleException;
import com.sino.base.exception.QueryException;
import com.sino.base.exception.WebFileDownException;
import com.sino.base.util.CalendarUtil;
import com.sino.base.util.StrUtil;
import com.sino.base.web.request.download.WebFileDownload;
import com.sino.base.log.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-7-29
 * Time: 10:08:11
 * To change this template use File | Settings | File Templates.
 */
public class FileMaintenanceDAO {
    private Connection conn = null;
    private HttpServletRequest req = null;
    private FileMaintenanceModel model = null;
    private UploadConfig uploadConfig = null;

    public FileMaintenanceDAO(Connection conn, HttpServletRequest req, FileDTO dtoParameter) throws ConfigException {
        this.conn = conn;
        this.req = req;
        this.uploadConfig = ConfigLoader.loadUploadConfig();
        this.model = new FileMaintenanceModel(dtoParameter);
    }

    /**
     * 得到上传附件列表
     *
     * @throws QueryException
     */
    public void getAttaches() throws QueryException {
        SQLModel sqlModel = model.getAttachesModel();
        SimpleQuery sq = new SimpleQuery(sqlModel, conn);
        sq.setCalPattern(CalendarConstant.LINE_PATTERN);
        sq.executeQuery();
        req.setAttribute("UPLOAD_FILES", sq.getSearchResult());
    }

    /**
     * 得到上传附件个数
     *
     * @return 上传附件个数
     */
    public int getAttacheCount() {
        int attachCount = 0;
        try {
            SQLModel sqlModel = model.getAttachCountModel();
            SimpleQuery sq = new SimpleQuery(sqlModel, conn);
            sq.setCalPattern(CalendarConstant.LINE_PATTERN);
            sq.executeQuery();
            Row row = sq.getFirstRow();
            attachCount = Integer.parseInt(row.getStrValue("ATTACH_COUNT"));
        } catch (Throwable ex) {
            Logger.logError(ex);
        }
        return attachCount;
    }

    public void downloadAttach(String orderPkValue, HttpServletResponse res) throws
            QueryException, WebFileDownException {
        WebFileDownload down = new WebFileDownload(req, res);
        FileDTO dto = getFileById(orderPkValue);
        if (dto == null) {
            throw new WebFileDownException("附件不存在，可能已经被删除！");
        }
        down.setFileName(dto.getFileName());
        down.setFilePath(dto.getFilePath());
        down.download();
    }

    public FileDTO getFileById(String orderPkValue) throws QueryException {
        FileDTO dtoParameter = new FileDTO();
        dtoParameter.setOrderPkValue(orderPkValue);
        model = new FileMaintenanceModel(dtoParameter);
        SQLModel sm = model.getAttachByIdModel();
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.setDTOClassName(FileDTO.class.getName());
        sq.executeQuery();
        return (FileDTO) sq.getFirstDTO();
    }


    public void uploadAttaches(int userId) throws Exception, DataHandleException, UnsupportedEncodingException, DTOException, FileUploadException {
        String orderPkName = "";
        String fileType = "";
        String orderType = "";
        String orderTable = "";
        Map desc_temp = new HashMap();
        Map desc = new HashMap();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        long size = uploadConfig.getSingleSize();
        int s = (int) (size * 1024); //单位为B
        factory.setSizeThreshold(s);
        //临时交换空间
        String savePath = uploadConfig.getSavePath();
        String tmpPath = savePath + File.separator + "tmp";
        File tmpDir = new File(tmpPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        factory.setRepository(new File(tmpPath));
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        //文件大小限制
        upload.setSizeMax(uploadConfig.getTotalSize() * 1024); //多个文件上传的总大小
        upload.setFileSizeMax(s); //单个文件大小
        // Parse the request
        List
                /* FileItem */ items = null;
        try {
            items = upload.parseRequest(req);
            if (items != null) {
                DTOSet dtos = new DTOSet();
                Iterator iter = items.iterator();
                /*
                 *不能保证非file域在file域前得到值，因此要所有的循环完成后，才能存到数据库中
                 *否则有可能还没得到头ID，等必要信息就开始存数据库
                 */
                while (iter.hasNext()) {
                    FileDTO dtoParameter = new FileDTO();
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        String name = item.getFieldName();
                        String value = item.getString("GBK");
                        if (name.equals("orderPkName")) {
                            orderPkName = value;
                        } else if (name.equals("fileType")) {
                            fileType = value;
                        } else if (name.equals("transType")) {
                            orderType = value;
                        } else if (name.equals("orderTable")) {
                            orderTable = value;
                        } else if (name.indexOf("description") != -1) {
                            String n = name.substring(name.length() - 1, name.length());
                            desc_temp.put(n, value);
                        }

                    } else {
                        String fieldName = item.getFieldName();
                        String fileName = item.getName();
                        String fileValue = item.getString();
                        if (fileName == null || fileName.equals("")) {
                            continue;
                        }
                        fileName = FilenameUtils.getName(fileName);
                        long sizeInBytes = item.getSize();
                        String fileext = FilenameUtils.getExtension(fileName);
                        String reName = getFileName();
                        String targetPath = savePath + File.separator + reName +
                                "." + fileext;
                        File dirFile = new File(savePath);
                        if (!dirFile.exists()) {
                            dirFile.mkdirs();
                        }
                        File targetfile = new File(targetPath);
                        //save file
                        item.write(targetfile);
                        //remove temp file
                        item.delete();
                        dtoParameter.setFileName(fileName);
                        dtoParameter.setFilePath(targetPath);
                        dtoParameter.setCreatedBy(userId);

                        String n = fieldName.substring(fieldName.length() - 1, fieldName.length());
                        desc.put(Integer.toString(dtos.getSize()), desc_temp.get(n));

                        dtos.addDTO(dtoParameter);
                    }
                }

                addAttaches(dtos, orderPkName, orderTable, fileType, orderType, desc);
            }
        } catch (SQLException e) {
            throw e;
        } catch (DataHandleException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (DTOException e) {
            throw e;
        } catch (FileUploadException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }

    public static String generateGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 给附件重新命名
     * 命名规则为年月+序列号，
     */
    public String getFileName() throws SQLException {
        SeqProducer sp = new SeqProducer(conn);
        //int seq = sp.getStrNextSeq("AMS_ASSETS_ATTACH_S");
        String seq = generateGUID();
        String sta_date = CalendarUtil.getCurrDate(DateConstant.STA_PATTERN);
        String year_month = sta_date.substring(0, 6);
        return year_month + "-" + seq;
    }

    //将附件相关信息存入数据库
    public void addNewAttach(FileDTO dtoParameter) throws DataHandleException {
        this.model = new FileMaintenanceModel(dtoParameter);
        SQLModel sm = model.getInsertNewAttachModel();
        DBOperator.updateRecord(sm, conn);
    }

    /**
     * 保存附件
     *
     * @param dtos
     * @param orderPkName
     * @param fileType
     * @param orderType
     * @throws SQLException
     * @throws DataHandleException
     */
    private void addAttaches(DTOSet dtos, String orderPkName, String orderTable, String fileType, String orderType, Map descriptions) throws SQLException, DataHandleException {
        if (dtos != null && dtos.getSize() > 0) {
            for (int i = 0; i < dtos.getSize(); i++) {
                FileDTO dtoParameter = (FileDTO) dtos.getDTO(i);
                if (!dtoParameter.getFileName().equals("") && !dtoParameter.getFilePath().equals("")) {
                    dtoParameter.setOrderPkName(orderPkName);
                    dtoParameter.setOrderTable(orderTable);
                    dtoParameter.setFileType(fileType);
                    dtoParameter.setOrderType(orderType);
                    String des = (String) descriptions.get(Integer.toString(i));
                    dtoParameter.setDescription(des);
                    addNewAttach(dtoParameter);
                }
            }
        }
    }

    public void deleteAttaches(String[] orderPkValues) throws SQLException,
            QueryException, ContainerException, DataHandleException {
        if (orderPkValues != null && orderPkValues.length > 0) {
            for (int i = 0; i < orderPkValues.length; i++) {
                try {

                    String orderPkValue = orderPkValues[i];
                    //取路径
                    String path = getPathById(orderPkValue);
                    //删除文件
                    File file = new File(path);
                    file.delete();
                    //从数据库删除记录
                    deleteFromDB(orderPkValue);
                } catch (QueryException e) {
                    conn.rollback();
                    throw e;
                } catch (ContainerException e) {
                    conn.rollback();
                    throw e;
                } catch (DataHandleException e) {
                    conn.rollback();
                    throw e;
                } finally {
                }
            }
        }
    }

    /**
     * 从数据库中取得附件存储路径
     *
     * @param orderPkValue
     * @return
     * @throws QueryException
     * @throws ContainerException
     */
    private String getPathById(String orderPkValue) throws QueryException,
            ContainerException {
        String path = "";
        FileDTO dtoParameter = new FileDTO();
        dtoParameter.setOrderPkValue(orderPkValue);
        model = new FileMaintenanceModel(dtoParameter);
        SQLModel sm = model.getPathModel();
        SimpleQuery sq = new SimpleQuery(sm, conn);
        sq.executeQuery();
        RowSet rs = sq.getSearchResult();
        if (rs != null && rs.getSize() > 0) {
            Row row = rs.getRow(0);
            path = StrUtil.nullToString(row.getValue("FILE_PATH"));
        }
        return path;
    }

    /**
     * 从数据库中删除附件信息
     *
     * @param orderPkValue
     * @throws DataHandleException
     */
    private void deleteFromDB(String orderPkValue) throws DataHandleException {
        FileDTO dtoParameter = new FileDTO();
        dtoParameter.setOrderPkValue(orderPkValue);
        model = new FileMaintenanceModel(dtoParameter);
        SQLModel sm = model.getDeleteAttachModel();
        DBOperator.updateRecord(sm, conn);
    }


}
