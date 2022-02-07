package at.lost_inc.ugandaknucklesbot.Commands.Core.Plugins;

import at.lost_inc.ugandaknucklesbot.Commands.API.BotCommand;
import at.lost_inc.ugandaknucklesbot.Commands.API.Command;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public final class PluginLoader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<BotCommand> commandClasses = new ArrayList<>();

    public PluginLoader(@NotNull Path pluginDir) throws IOException {
        final File dir = pluginDir.toFile();

        if(!dir.isDirectory())
            throw new IllegalStateException("pluginDir should lead to a directory!");

        final File[] files = dir.listFiles((file, name) -> name.endsWith(".jar"));
        if(files == null)
            return;

        final List<Enumeration<? extends ZipEntry>> jarFileEntries = new ArrayList<>();
        final List<URL> jarFileUrls = new ArrayList<>();
        for(File file : files) {
            jarFileEntries.add(new JarFile(file).entries());
            jarFileUrls.add(file.toURI().toURL());
        }

        final ClassLoader classLoader = new URLClassLoader(jarFileUrls.toArray(new URL[0]));
        for(Enumeration<? extends ZipEntry> entries : jarFileEntries)
            while (entries.hasMoreElements()) {
                final ZipEntry entry = entries.nextElement();
                if(!entry.getName().endsWith(".class"))
                    continue;

                final String entryName = entry.getName().substring(0, entry.getName().length() - 6).replaceAll("/", ".");
                try {
                    Class<?> clazz = classLoader.loadClass(entryName);
                    if(!BotCommand.class.equals(clazz.getSuperclass()))
                        continue;
                    if(!clazz.isAnnotationPresent(Command.class))
                        continue;

                    commandClasses.add((BotCommand) clazz.newInstance());
                } catch (ClassNotFoundException ignored) {
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.warn(String.format("Error while loading %s:", entryName), e);
                }
            }
    }

    public BotCommand @NotNull [] getCommands() {
        return commandClasses.toArray(new BotCommand[0]);
    }
}
