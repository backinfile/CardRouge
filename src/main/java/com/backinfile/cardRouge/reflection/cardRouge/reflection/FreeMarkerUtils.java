package com.backinfile.cardRouge.reflection.cardRouge.reflection;

import com.backinfile.cardRouge.reflection.cardRouge.Log;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class FreeMarkerUtils {
    public static void formatFile(String templateFilePath, String templateFileName, Map<String, Object> rootMap, String outPath,
                                  String outFileName) {
        File file = new File(outPath, outFileName);
        try {
            Configuration config = new Configuration(Configuration.VERSION_2_3_22);
            config.setDefaultEncoding("UTF-8");
            config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//            config.setDirectoryForTemplateLoading(new File(templateFilePath));
            config.setClassLoaderForTemplateLoading(FreeMarkerUtils.class.getClassLoader(), templateFilePath);

            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                Template template = config.getTemplate(templateFileName, "UTF-8");
                template.process(rootMap, writer);
            }
            Log.gen.info("gen {} success", file.getPath());
        } catch (Exception e) {
            Log.gen.error("gen {} failed", file.getPath());
            throw new RuntimeException(e);
        }
    }
}
