package com.ascendant.common.gen.plugin;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import sun.nio.cs.Surrogate;

public class GenMain {
	public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        InputStream resourceAsStream = GenMain.class.getResourceAsStream("/gen/generatorConfig.xml");

        Configuration config = cp.parseConfiguration(resourceAsStream
                );
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
	}
}
