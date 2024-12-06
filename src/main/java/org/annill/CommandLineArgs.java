package org.annill;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CommandLineArgs {
    private final List<String> inputFiles = new ArrayList<>();
    private String outputPath = "";
    private String prefix = "";
    private boolean appendMode = false;
    private boolean fullStatistics = false;
    private boolean shortStatistics = false;

    public boolean isAppendMode() {
        return appendMode;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }

    public boolean isShortStatistics() {
        return shortStatistics;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public static CommandLineArgs parse(String[] args) {
        CommandLineArgs parsed = new CommandLineArgs();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (++i < args.length) {
                        String parse = args[i];
                        parsed.outputPath = Path.of(parse +"/").toString();
                    } else throw new IllegalArgumentException(ErrorsEnums.NOT_FIND_VALUE_FOR_OPTION_O.getErrors());
                    break;
                case "-p":
                    if (++i < args.length) parsed.prefix = args[i];
                    else throw new IllegalArgumentException((ErrorsEnums.NOT_FIND_VALUE_FOR_OPTION_P.getErrors()));
                    break;
                case "-a":
                    parsed.appendMode = true;
                    break;
                case "-f":
                    parsed.fullStatistics = true;
                    break;
                case "-s":
                    parsed.shortStatistics = true;
                    break;
                default:
                    if (args[i].startsWith("-"))
                        throw new IllegalArgumentException(ErrorsEnums.NOT_FIND_OPTION.getErrors() + args[i]);
                    parsed.inputFiles.add(args[i]);
            }
        }
        if (parsed.inputFiles.isEmpty()) {
            throw new IllegalArgumentException(ErrorsEnums.NOT_FIND_INPUT_FILE.getErrors());
        }
        return parsed;
    }
}
