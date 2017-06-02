package com.bierbobo.config.tool.codegen.service;

import com.bierbobo.config.tool.codegen.domain.EntityMeta;
import java.io.IOException;
import java.util.List;

public abstract interface ExcelService
{
  public abstract List<EntityMeta> parseExcel(String paramString)
    throws IOException;
}