package dev.juligame.Jconfig;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public abstract class JConfig {
    static Gson gson = (new GsonBuilder())
            .setPrettyPrinting()
            .addSerializationExclusionStrategy(new ExclusionStrategy() {
                public boolean shouldSkipField(FieldAttributes field) {
                    return (field.getAnnotation(NotSerialize.class) != null);
                }


                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).addDeserializationExclusionStrategy(new ExclusionStrategy() {
                public boolean shouldSkipField(FieldAttributes field) {
                    return (field.getName().equals("path") || field.getName().equals("cfgName"));
                }


                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).disableHtmlEscaping()
            .create();
    @NotSerialize
    private final String cfgName;
    @NotSerialize
    public File path = null;

    public JConfig(String path, String suffix) {
        this.path = new File(path);
        if (suffix.isEmpty()) {
            this.cfgName = getClass().getSimpleName();
        } else {
            this.cfgName = getClass().getSimpleName() + "_" + suffix;
        }
    }

    public void load()  {
        File confFile = new File(this.path, this.cfgName + ".json");
        Class<JConfig> clazz = (Class) getClass();

        try (Reader reader = new InputStreamReader(new FileInputStream(confFile), StandardCharsets.UTF_8)) {
            JConfig conf = gson.fromJson(reader, clazz);
            for (Field field : conf.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(this, field.get(conf));
            }
        } catch (FileNotFoundException ex) {
            save();
            System.out.println("Config file not found, creating new one...");
        } catch (Exception ex) {
            System.out.println("Failed to load " + this.cfgName + ".json");
            ex.printStackTrace();
        }
    }

    public void save() {
        File confFile = new File(this.path, this.cfgName + ".json");
        if (!confFile.getParentFile().exists()) {
            confFile.getParentFile().mkdirs();
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(confFile), StandardCharsets.UTF_8)) {            gson.toJson(this, writer);
        } catch (Exception ex) {
            System.out.println("Failed to save " + this.cfgName + ".json");
            ex.printStackTrace();
        }
    }

    private File getPath() {
        try {
            return
                    (new File((new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())).getPath())).getAbsolutePath().contains(".jar") ? new File(System.getProperty("user.dir")) : (new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())).getParentFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPath(File path) {
        this.path = path;
    }
}