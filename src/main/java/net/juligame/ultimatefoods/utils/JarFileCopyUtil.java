package net.juligame.ultimatefoods.utils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.*;

public class JarFileCopyUtil {

    public static void copyPathFromJar(String jarPathInside, String destinationPath) throws IOException {
        // Get the JAR file path of the running application
        String jarFilePath = JarFileCopyUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        jarFilePath = URLDecoder.decode(jarFilePath, "UTF-8");
        if (jarFilePath.endsWith(".jar")) {
            // Open the JAR file for reading
            try (JarFile jarFile = new JarFile(jarFilePath)) {
                // Iterate through the entries (files and directories) inside the JAR
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    // Check if the entry's name starts with the specified path
                    if (entry.getName().startsWith(jarPathInside)) {
                        // Extract the entry (file or directory) to the destination path
                        copyEntry(jarFile, entry, destinationPath, jarPathInside);
                    }
                }
            }
        } else {
            System.err.println("This is not a JAR file. The function can only be executed from within a JAR.");
        }
    }

    private static void copyEntry(JarFile jarFile, JarEntry entry, String destinationPath, String jarPathInside) throws IOException {
        // Build the destination path for the entry
        Path destPath = Paths.get(destinationPath, entry.getName().substring(jarPathInside.length()));

        if (entry.isDirectory()) {
            // Create the directory if it does not exist
            Files.createDirectories(destPath);
        } else {
            // Create parent directories for the file if they don't exist
            File file = destPath.toFile();
            file.setReadable(true);
            file.setWritable(true);
            // Copy the file from the JAR to the destination path
            try (InputStream is = jarFile.getInputStream(entry)) {
                Files.copy(is, destPath, StandardCopyOption.REPLACE_EXISTING);
            }
            try {
                Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rw-r--r--");
                Files.setPosixFilePermissions(destPath, permissions);
            }catch (Exception ignored) {
                ///
            }
            // Set permissions

        }
    }
}
