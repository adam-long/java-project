package net.codaverde.java.project;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Unmatched;

@Command(name = "java-project", mixinStandardHelpOptions = true, version = "Java Project 1.0",
        description = "Basic Shell of a Java Project.")
public class Main implements Callable<Integer> {

    @Option(names = {"-c", "--config"}, description = "Location of configuration file.")
    private File file;

    @Unmatched
    private final String[] unused = new String[0];

    private static final Logger LOG = LogManager.getLogger(Main.class.getName());

    public static void main(final String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        LOG.info(Arrays.toString(unused));
        return 0;
    }

}
