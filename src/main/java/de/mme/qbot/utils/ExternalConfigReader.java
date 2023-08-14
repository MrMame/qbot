package de.mme.qbot.utils;


import de.mme.qbot.QbotApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

public class ExternalConfigReader {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CONFIGFOLDER = "externalConfigs/";
    private Properties configProps = null;
    private String completeConfigFilename;






    public ExternalConfigReader(String configFilename) throws IOException {
        try{
            this.configProps = readExternalConfigProperties(configFilename);
        }catch (IOException e){
            logger.error("IOException while trying to read configFilename '" + this.completeConfigFilename + "'");
            throw e;
        }catch(Exception e){
            logger.error("General Exception while trying to read configFilename '" + this.completeConfigFilename + "'");
            throw e;
        }

    }

    public String readProperty(String propertyName){
        String returnValue = configProps.getProperty(propertyName);
        if(returnValue == null) {
            logger.warn("There is no property found named '"
                    + propertyName + "' in external config file '"
                    + this.completeConfigFilename + "'");
        }
        return returnValue;
    }




    private Properties readExternalConfigProperties(String Filename) throws IOException {
        String configFilename = getJarPath() + CONFIGFOLDER + Filename;
        this.completeConfigFilename = configFilename;

        Properties discordProps = new Properties();
        try(FileInputStream fis = new FileInputStream(configFilename)){
            discordProps.load(fis);
        }
        return discordProps;
    }




    private String getJarPath(){

        String jarPath="";

//        try {
                // Get path of the JAR file
//                jarPath = QbotApplication.class
//                        .getProtectionDomain()
//                        .getCodeSource()
//                        .getLocation()
//                        .toURI()
//                        .getPath();

            String path = QbotApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();

            // If running a JAR File, the path will contain also tehh name of the JAR File, seperated by "file:"
            // also conatining the folder path inside the jar. before "file:" the directory is targeted.
            // e.g on Windows "F:\Java-Projects\qbot\target\file:\F:\Java-Projects\qbot\target\qbot-0.0.1-SNAPSHOT.jar!\BOOT-INF"
            // Therefore the streing gets splitted on "file:" and the leading index is used.
            if(path.contains("file:")){
                System.out.println("path variable using JAR-Container RAW" + path + "\n");
                jarPath = path.split("file:")[0].split("!")[0];
                System.out.println("path variable using JAR-Container AFTER ! Split" + jarPath + "\n");
                jarPath = jarPath.substring(1,jarPath.lastIndexOf("/")+1);
                System.out.println("path variable using JAR-Container FINISHED " + path + "\n");
            }else{
                // NOT running from JAR fiel
                System.out.println("path variable using NO JAR-Container " + path + "\n");
                jarPath = path;
            }


//        File jarFile = new File(path);
//        jarPath = jarFile.getParentFile().getAbsolutePath();
          System.out.println("JAR Path : " + jarPath + "\n");


            // Get Folder Name
            jarPath = "/" + jarPath.substring(1,jarPath.lastIndexOf("/")+1);

//            jarPath = System.getProperty("user.dir");

//
//
//            } catch (URISyntaxException e) {
//                logger.error("Error getting JARs path'" + jarPath + "'");
//                e.printStackTrace();
//            }
        return jarPath;
    }

}
