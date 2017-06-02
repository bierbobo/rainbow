package com.bierbobo.service;


import com.bierbobo.domain.EntityMeta;

import java.io.IOException;
import java.util.List;

public abstract interface ExcelService
{
  public abstract List<EntityMeta> parseExcel(String paramString)
    throws IOException;
}