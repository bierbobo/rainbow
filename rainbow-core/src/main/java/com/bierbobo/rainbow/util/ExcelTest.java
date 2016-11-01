package com.bierbobo.rainbow.util;//package com.bierbobo.util;
//
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.streaming.SXSSFSheet;
//import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Date;
//
//
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.xssf.eventusermodel.XSSFReader;
//import org.apache.poi.xssf.model.SharedStringsTable;
//import org.apache.poi.xssf.usermodel.XSSFRichTextString;
//import org.xml.sax.Attributes;
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//import org.xml.sax.XMLReader;
//import org.xml.sax.helpers.DefaultHandler;
//import org.xml.sax.helpers.XMLReaderFactory;
//
///**
// * Created by lifubo on 2016/10/18.
// */
//public class ExcelTest {
//
//    public static void main(String[] args) throws IOException {
//
//    /*    Date date1=new Date();
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook .createSheet("这里第一页");
//        FileOutputStream fos = new FileOutputStream("D://test0215.xlsx");
//         for (int rr = 0; rr < 200000; rr++) {
//            Row writeRow = sheet.createRow(rr);
//            for (int cc = 0; cc < 10; cc++) {
//                writeRow.createCell(cc).setCellValue("测试" + rr + "," + cc);
//            }
//        }
//        workbook .write(fos);
//        fos.close();
//
//        Date date2=new Date();
//        System.out.println((date2.getTime()-date1.getTime())/1000);
//*/
//        for (int i = 0; i <10 ; i++) {
//            testWrite(i);
//        }
//
//    }
//
//    private static void testWrite(int i) throws IOException {
//        Date date2=new Date();
//        FileOutputStream out = new FileOutputStream(new File("D://result"+i+".xlsx"));
//
//        Workbook writeWB = new SXSSFWorkbook(100);
//        Sheet writeSheet = writeWB.createSheet();
//
//        for (int rr = 0; rr < 200000; rr++) {
//            Row writeRow = writeSheet.createRow(rr);
//            for (int cc = 0; cc < 50; cc++) {
//                writeRow.createCell(cc).setCellValue("测试" + rr + "," + cc);
//            }
//
//            //每当行数达到设置的值就刷新数据到硬盘,以清理内存
//            if(rr%10000==0){
//                ((SXSSFSheet)writeSheet).flushRows();
//            }
//        }
//        writeWB.write(out);
//        out.close();
//
//        Date date3=new Date();
//        System.out.println((date3.getTime()-date2.getTime())/1000);
//    }
//
//    public void readOneSheet(String path) throws Exception {
//        OPCPackage pkg = OPCPackage.open(path);
//        XSSFReader r = new XSSFReader(pkg);
//        SharedStringsTable sst = r.getSharedStringsTable();
//
//        XMLReader parser = fetchSheetParser(sst);
//
//        InputStream sheet = r.getSheet("rId1");
//
//        InputSource sheetSource = new InputSource(sheet);
//        parser.parse(sheetSource);
//
//        sheet.close();
//    }
//
//    /**
//     * 读取所有工作簿的入口方法
//     * @param path
//     * @throws Exception
//     */
//    public void process(String path) throws Exception {
//        OPCPackage pkg = OPCPackage.open(path);
//        XSSFReader r = new XSSFReader(pkg);
//        SharedStringsTable sst = r.getSharedStringsTable();
//
//        XMLReader parser = fetchSheetParser(sst);
//
//        Iterator<InputStream> sheets = r.getSheetsData();
//        while (sheets.hasNext()) {
//            curRow = 0;
//            sheetIndex++;
//            InputStream sheet = sheets.next();
//            InputSource sheetSource = new InputSource(sheet);
//            parser.parse(sheetSource);
//            sheet.close();
//        }
//    }
//
//    /**
//     * 该方法自动被调用，每读一行调用一次，在方法中写自己的业务逻辑即可
//     * @param sheetIndex 工作簿序号
//     * @param curRow 处理到第几行
//     * @param rowList 当前数据行的数据集合
//     */
//    public void optRow(int sheetIndex, int curRow, List<String> rowList) {
//        String temp = "";
//        for(String str : rowList) {
//            temp += str + "_";
//        }
//        System.out.println(temp);
//    }
//
//
//    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
//        XMLReader parser = XMLReaderFactory
//                .createXMLReader("org.apache.xerces.parsers.SAXParser");
//        this.sst = sst;
//        parser.setContentHandler(this);
//        return parser;
//    }
//
//    public void startElement(String uri, String localName, String name,
//                             Attributes attributes) throws SAXException {
//        // c => 单元格
//        if (name.equals("c")) {
//            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
//            String cellType = attributes.getValue("t");
//            if (cellType != null && cellType.equals("s")) {
//                nextIsString = true;
//            } else {
//                nextIsString = false;
//            }
//        }
//        // 置空
//        lastContents = "";
//    }
//
//
//    public void endElement(String uri, String localName, String name)
//            throws SAXException {
//        // 根据SST的索引值的到单元格的真正要存储的字符串
//        // 这时characters()方法可能会被调用多次
//        if (nextIsString) {
//            try {
//                int idx = Integer.parseInt(lastContents);
//                lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
//                        .toString();
//            } catch (Exception e) {
//
//            }
//        }
//
//        // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
//        // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
//        if (name.equals("v")) {
//            String value = lastContents.trim();
//            value = value.equals("") ? " " : value;
//            rowlist.add(curCol, value);
//            curCol++;
//        } else {
//            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
//            if (name.equals("row")) {
//                optRow(sheetIndex, curRow, rowlist);
//                rowlist.clear();
//                curRow++;
//                curCol = 0;
//            }
//        }
//    }
//
//    public void characters(char[] ch, int start, int length)
//            throws SAXException {
//        // 得到单元格内容的值
//        lastContents += new String(ch, start, length);
//    }
//}
