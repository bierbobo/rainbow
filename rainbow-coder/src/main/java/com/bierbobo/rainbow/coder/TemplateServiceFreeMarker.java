package com.bierbobo.rainbow.coder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * Created by lifubo on 2017/5/6.
 */

public class TemplateServiceFreeMarker {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private FreeMarkerConfigurer freeMarkerConfigurer;

    private Configuration freeMarkerConfiguration;

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public void setFreeMarkerConfiguration(Configuration freeMarkerConfiguration) {
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    public String getContent(String templateName, Map<String, Object> model) {
        try {
            Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.equals(ex.getMessage());
            try {
                Template t = freeMarkerConfiguration.getTemplate(templateName);
                return FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            } catch (Exception e) {
                e.printStackTrace();
                logger.equals(e.getMessage());
            }
        }

        return null;
    }
}
