package net.codaverde.java.project;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Callable;
import org.apache.commons.configuration2.YAMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Unmatched;

@Command(name = "java-project", mixinStandardHelpOptions = true, version = "Java Project 1.0",
        description = "Basic Shell of a Java Project.")
public class Main implements Callable<Integer> {

    private static File configFile;
    private YAMLConfiguration config;

    @Unmatched
    private final String[] unused = new String[0];

    private static final Logger LOG = LogManager.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        configFile = new File(System.getProperty("app.conf.dir") + "/" + System.getProperty("app.name") + ".yml");
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        try{
          loadConfig();
        } catch (final ConfigurationException ex) {
            LOG.error("Error loading config file at " + configFile, ex);
            return -1;
        }
        LOG.info(Arrays.toString(unused));
        return 0;
    }

    private void loadConfig() throws ConfigurationException {
        
        if(configFile == null){
            throw new ConfigurationException("No configuration file defined.");
            
        }
        if(!configFile.exists()){
            throw new ConfigurationException(configFile.getAbsolutePath() + " does not exist.");
        }
        if(!configFile.canRead()){
            throw new ConfigurationException(configFile.getAbsolutePath() + " cannot be read.");
        }
        
        final FileBasedConfigurationBuilder<YAMLConfiguration> builder
                = new FileBasedConfigurationBuilder<>(YAMLConfiguration.class)
                        .configure(new org.apache.commons.configuration2.builder.fluent.Parameters().hierarchical().setFile(configFile));
        
            config = builder.getConfiguration();
    }

}
