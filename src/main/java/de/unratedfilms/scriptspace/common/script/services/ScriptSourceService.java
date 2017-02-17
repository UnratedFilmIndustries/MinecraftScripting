
package de.unratedfilms.scriptspace.common.script.services;

import static de.unratedfilms.scriptspace.common.Consts.LOGGER;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import de.unratedfilms.scriptspace.common.Config;
import de.unratedfilms.scriptspace.common.Consts;
import de.unratedfilms.scriptspace.common.script.SourceScript;

public class ScriptSourceService {

    private static final String DEFAULT_SCRIPTS_DIR = "/assets/" + Consts.MOD_ID + "/defaultscripts";

    public static Set<SourceScript> getAvailableScripts() {

        Set<SourceScript> sourceScripts = new HashSet<>();

        // Custom script files come first, default files second; that way, custom files can override default files if they want to
        sourceScripts.addAll(getCustomScripts());
        sourceScripts.addAll(getDefaultScripts());

        return sourceScripts;
    }

    private static List<SourceScript> getDefaultScripts() {

        LOGGER.info("Adding default scripts that have not been overridden");

        List<SourceScript> scripts = new ArrayList<>();

        try {
            for (String scriptName : getDefaultScriptNames()) {
                // Get the script sourcecode
                String scriptResourcePath = DEFAULT_SCRIPTS_DIR + "/" + scriptName;
                String scriptCode = Resources.toString(ScriptSourceService.class.getResource(scriptResourcePath), Charsets.UTF_8);

                scripts.add(new SourceScript(scriptName, scriptCode));
            }
        } catch (IOException e) {
            LOGGER.error("Error while retrieving default script files", e);
        }

        return scripts;
    }

    private static List<String> getDefaultScriptNames() throws IOException {

        List<String> scriptNames = new ArrayList<>();

        try (InputStream in = ScriptSourceService.class.getResourceAsStream(DEFAULT_SCRIPTS_DIR + "/defaultscripts.index");
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String scriptName;

            while ( (scriptName = br.readLine()) != null) {
                if (!StringUtils.isBlank(scriptName)) {
                    scriptNames.add(scriptName.trim());
                }
            }
        }

        return scriptNames;
    }

    private static List<SourceScript> getCustomScripts() {

        final List<SourceScript> scripts = new ArrayList<>();

        for (String directoryPath : Config.scriptDirectories) {
            final Path directory = Consts.MINECRAFT_DIR.resolve(directoryPath);

            // Create the script directory if it doesn't exist yet
            if (!Files.exists(directory)) {
                LOGGER.info("Creating new configured script dir under '{}'", directory);

                try {
                    Files.createDirectories(directory);
                } catch (IOException e) {
                    LOGGER.error("Failed to create the configured script dir '{}'", directory, e);
                }
            }

            LOGGER.info("Checking dir '{}' for potential scripts", directory);

            try {
                // Recursively walk through the file tree and find all script files
                Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                        try {
                            // Get the script name
                            String scriptName = directory.relativize(file).toString();
                            // Get the script sourcecode
                            String scriptCode = new String(Files.readAllBytes(file), Charsets.UTF_8);

                            scripts.add(new SourceScript(scriptName, scriptCode));
                        } catch (IOException e) {
                            LOGGER.error("Error while reading custom script file '{}'", file, e);
                        }

                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

                        LOGGER.error("Failed to visit the file '{}' while searching for potential script files", file, exc);
                        return FileVisitResult.CONTINUE;
                    }

                });
            } catch (IOException e) {
                LOGGER.error("Unexpected exception while iterating through the script file dir '{}'", directory, e);
            }
        }

        return scripts;
    }

    private ScriptSourceService() {

    }

}
