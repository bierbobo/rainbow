package com.bierbobo.rainbow.service.exportData;

import com.bierbobo.rainbow.domain.common.CommonResult;
import com.bierbobo.rainbow.domain.common.Constants;
import com.bierbobo.rainbow.domain.entity.ExportData;
import com.bierbobo.rainbow.domain.entity.Task;
import com.bierbobo.rainbow.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lifubo on 2016/11/8.
 */
public abstract class ExportAbstractService<T> {

    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 生成的excel文件，先保存在本地，才发到jss，这个路径就是保存本地时的路径
     */
    @Value("${export.data.folder}")
    private String EXPORT_PATH;


    /**
     * 查询数据，具体逻辑子类实现(方法重载)
     *
     * @param data
     * @param task
     * @return
     */
    protected abstract CommonResult<Boolean> queryData(ExportData data,Task task);


    /**
     * 封装了所有的业务逻辑，对外的入口方法
     *
     * 1.查询出所有数据，生成到文件中。
     * 2.遍查询数据，遍生成文件。
     *
     *
     * @param task
     * @param para
     * @return
     */
    public CommonResult<Boolean> process(Task task, ExportData para) {

        try {



            return process1(task);

        } catch (Exception e) {
            logger.info("导出任务异常===", e);
            return new CommonResult<Boolean>(false, null, "导出失败" );
        }

    }


    public void generateExcel() throws IOException {

        //生成EXCEL
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet("sheet1");
        BufferedOutputStream bufferedOutputStream=null;
        FileOutputStream fileStream = null;
        List<ArrayList<String>> dataList=null;
        String fileAllPath =null;


        try {

            //指定的是否需要设置单元格
            List<String> colFormatInfo = dataList.get(0);

            //遍历，每次遍历创建一行【从第二行开始是表头和数据,第一行为指定的是否需要设置单元格】
            for(Integer rowNum = 1; rowNum < dataList.size(); rowNum++) {

                //创建POI的行对象SXSSFRow，将本行的数据存在临时变量tempList中
                SXSSFRow row = (SXSSFRow) sheet.createRow(rowNum-1);
                List<String> tempList = dataList.get(rowNum);

                //变成生成一行的每列,tempList保存一行数据
                //遍历一行
                for(Integer columnIndex = 0; columnIndex < tempList.size(); columnIndex++) {

                    Cell cell = row.createCell(columnIndex);
                    //对于sheet标头，不进行数据类型转化
                    if(rowNum == 1 ){
                        cell.setCellValue(tempList.get(columnIndex));

                    }else{

                        //设置单元格格式【数字型、是否保留小数】
                        String columnType = colFormatInfo.get(columnIndex);

                        if(Constants.EXPORT_NUM_FORMAT_ENUM.equalsIgnoreCase(columnType)){

                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            String tempValue = tempList.get(columnIndex);
                            if(StringUtils.isBlank(tempValue)){
                                tempValue = "0";
                            }
                            tempValue = tempValue.trim();
                            //数据加“0”是防止内容为空
                            cell.setCellValue(Double.valueOf(tempValue));

                        }else if(Constants.EXPORT_BIGDECIMAL_FORMAT_ENUM.equalsIgnoreCase(columnType)){

                            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                            String tempValue = tempList.get(columnIndex);
                            if(StringUtils.isBlank(tempValue)){
                                tempValue = "0.0000";
                            }
                            tempValue = tempValue.trim();
                            cell.setCellValue(Double.valueOf(tempValue));
                        }else{
                            cell.setCellValue(tempList.get(columnIndex));
                        }
                    }
                }

                //每当行数达到设置的值就刷新数据到硬盘,以清理内存
                if(rowNum% Constants.FLUSH_ROWS==0){
                    sheet.flushRows();
                }

            }

            fileStream = new FileOutputStream(fileAllPath);
            bufferedOutputStream = new BufferedOutputStream(fileStream);
            workbook.write(bufferedOutputStream);
            bufferedOutputStream.flush();

        }catch (Exception e) {

            logger.error("queryData()采销大表导出失败", e);

        }finally {
            if(workbook != null) {
                workbook.dispose();
            }
            if(fileStream != null){
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private CommonResult<Boolean> process2(Task task) throws Exception {

        CommonResult queryResult = queryData(null,null);

        int size=0;
//        size=queryResult.getData().size();
        logger.info("uuid为" + task.getUuid() + "的导出任务获取数据量:" + size);

        String folderPath = FileUtils.getFolderPath(EXPORT_PATH, true);// 文件夹路径【参数true说明保留最后的路径分割符】
        String fileName = task.getName().replaceAll(":", "").replaceAll("：", "") + ".xls";// 文件名称
        String fileAllPath = folderPath + UUID.randomUUID().toString() + fileName;// 文件全路径

        // 数据写入velocity模板
        generateXmlAndSave();
        //上传文件
        boolean bool = this.uploadJss(null, fileName, fileAllPath);
        if(bool){
            return new CommonResult<Boolean>(true, 200L, "导出成功");
        }else{
            return new CommonResult<Boolean>(false, 200L, "上传文件失败");
        }
    }


    private CommonResult<Boolean> process1(Task task) throws Exception {
        CommonResult queryResult = queryData(null,null);

        int size=0;
//        size=queryResult.getData().size();
        logger.info("uuid为" + task.getUuid() + "的导出任务获取数据量:" + size);

        String folderPath = FileUtils.getFolderPath(EXPORT_PATH, true);// 文件夹路径【参数true说明保留最后的路径分割符】
        String fileName = task.getName().replaceAll(":", "").replaceAll("：", "") + ".xls";// 文件名称
        String fileAllPath = folderPath + UUID.randomUUID().toString() + fileName;// 文件全路径

        // 数据写入velocity模板
        generateXmlAndSave();
        //上传文件
        boolean bool = this.uploadJss(null, fileName, fileAllPath);
        if(bool){
            return new CommonResult<Boolean>(true, 200L, "导出成功");
        }else{
            return new CommonResult<Boolean>(false, 200L, "上传文件失败");
        }
    }

    protected  void generateXmlAndSave(){

    }

    /**
     * 将文件上传到jss
     * @throws Exception
     * */
    public boolean uploadJss(ExportData para,String fileName,String fileAllPath) throws Exception {
        boolean bool = false;
        try{
            // 上传JSS
            CommonResult<String> jssResult =null;

//                    jssService.uploadFileAndGetDownUrl(BUCKET_NAME, fileName, fileAllPath);

            // 保存下载地址
            if (jssResult.isSuccess()) {
                para.setDownloadUrl("");
//                exportDao.updateByPrimaryKey(para);
                bool = true;
                logger.info("导出任务uuid为" + para.getId() + "的任务，上传到JSS的文件下载地址=" + jssResult.getData());
                // 发送邮件给相应的采销
//				MailUtil.sendMail(para.getEmail(), "导出完成：" + task.getName(),"请点击 <a href=\"" + jssResult.getData() + "\">下载</a>");
                logger.info("uploadJss()方法上传成功");
            } else {
                logger.info("uploadJss()方法上传失败" + jssResult.getMessage());
            }
        } catch (Exception e) {
            logger.info("uploadJss()方法上传失败",e);
            throw e;
        } finally{
            // 删除本地文件
            FileUtils.deleteLocalFile(fileAllPath);
        }
        return bool;
    }

}
