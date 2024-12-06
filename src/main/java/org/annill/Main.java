package org.annill;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static String nameIntegerFile = "integers.txt";
    private static String nameFloatFile = "floats.txt";
    private static String nameStringFile = "strings.txt";

    public static void main(String[] args) {
        try {
            CommandLineArgs parsedArgs = CommandLineArgs.parse(args);
            processFiles(parsedArgs);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(ErrorsEnums.UNKNOWN.getErrors() + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processFiles(CommandLineArgs args) throws IOException {
        Map<String, BufferedWriter> writers = new HashMap<>();
        Map<String, DataStatistics> statistics = new HashMap<>();

        for (String inputFile : args.getInputFiles()) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile), StandardCharsets.UTF_8)) {
                String line;

                while ((line = reader.readLine()) != null) {
                    processLine(line, args, statistics, writers);
                }
            } catch (IOException e) {
                System.err.println(ErrorsEnums.MISTAKE_READING_FILE.getErrors() + inputFile );
            }
        }

        for (BufferedWriter writer : writers.values()) {
            writer.close();
        }

        printStatistics(args, statistics);
    }

    private static void processLine(String line, CommandLineArgs args, Map<String, DataStatistics> statistics, Map<String, BufferedWriter> writers) throws IOException {
        String type;
        if (line.matches(RegexPattern.Int.getPattern())) {
            type = nameIntegerFile;
        } else if (line.matches(RegexPattern.Float.getPattern())) {
            type = nameFloatFile;
        } else {
            type = nameStringFile;
        }
        File file = getFile(args, type);

        if (!file.exists()) {
            file.createNewFile();
        }
        String fileName = file.getName();

        if (!writers.containsKey(fileName)) {
            Path paths = Paths.get(file.getPath());
            if (args.isAppendMode()) {
                writers.put(fileName, Files.newBufferedWriter(paths, StandardCharsets.UTF_8, StandardOpenOption.APPEND));
            } else {
                writers.put(fileName, Files.newBufferedWriter(paths, StandardCharsets.UTF_8));
            }
            statistics.put(fileName, new DataStatistics());
        }

        if (args.isFullStatistics()) {
            statistics.get(fileName).addFullStatistics(line);
        }
        if (args.isShortStatistics()) {
            statistics.get(fileName).addShortStatistics();
        }

        BufferedWriter writer = writers.get(fileName);
        writer.write(line);
        writer.newLine();
    }

    private static void printStatistics(CommandLineArgs args, Map<String, DataStatistics> statistics) {
        statistics.forEach((k, v) -> {
            if (args.isShortStatistics()) System.out.println(k + "\n" + v.getShortStatistics());
            if (args.isFullStatistics()) System.out.println(k + "\n" + v.getFullStatistics());
        });
    }

    private static File getFile(CommandLineArgs args, String type) throws IOException {
        File file;
        if (!args.getOutputPath().isEmpty()) {
            Path dirPath = Paths.get(args.getOutputPath());
            File currentDir = new File("");
            String absolutePath = currentDir.getAbsolutePath();

            Path path = Path.of(absolutePath + dirPath );

            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }

            if (!args.getPrefix().isEmpty()) {
                file = new File(path  +"/"+ args.getPrefix() + type);
            } else {
                file = new File(path  +"/"+  type);
            }
        } else {
            if (!args.getPrefix().isEmpty()) {
                file = new File(args.getPrefix() + type);
            } else {
                file = new File(type);
            }
        }
        return file;
    }
}