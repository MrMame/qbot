package de.mme.qbot.utils;


import de.mme.qbot.QbotApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        try {
                // Get path of the JAR file
                jarPath = QbotApplication.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .toURI()
                        .getPath();
                //System.out.println("JAR Path : " + jarPath + "\n");
                // Get Folder Name
                jarPath = "/" + jarPath.substring(1,jarPath.lastIndexOf("/")+1);

//            jarPath = System.getProperty("user.dir");



            } catch (URISyntaxException e) {
                logger.error("Error getting JARs path'" + jarPath + "'");
                e.printStackTrace();
            }
        return jarPath;
    }

}
