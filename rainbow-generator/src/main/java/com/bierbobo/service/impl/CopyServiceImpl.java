package com.bierbobo.service.impl;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.bierbobo.service.CopyService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CopyServiceImpl
  implements CopyService
{
  private static final Log log = LogFactory.getLog(CopyServiceImpl.class);

  public void copy(String srcClassPathFolder, String targetPath, String filePathSuffix, String fileName)
  {
    InputStream is = null;
    OutputStream os = null;
    try {
      String srcFile = null;
      if (StringUtils.isBlank(srcClassPathFolder))
        srcFile = "/" + fileName;
      else {
        srcFile = "/" + srcClassPathFolder + "/" + fileName;
      }

      is = CopyServiceImpl.class.getResourceAsStream(srcFile);
      File dir = new File(targetPath + "/" + filePathSuffix);
      if (!dir.exists()) {
        dir.mkdirs();
      }
      os = FileUtils.openOutputStream(new File(dir, fileName));
      IOUtils.copy(is, os);
    } catch (IOException e) {
      log.error("", e);
    } finally {
      IOUtils.closeQuietly(is);
      IOUtils.closeQuietly(os);
    }
  }
}