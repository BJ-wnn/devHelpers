package com.github.nan.dataprocess.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
public class XmlParserUtil {

    /**
     *  使用JAXB 将 XML 文件解析为 Java 对象
     *
     * @param xmlFilePath
     * @param targetType
     * @return
     * @param <T>
     */
    public static <T> T parseXmlFile(Path xmlFilePath, Class<T> targetType) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(targetType);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return targetType.cast(unmarshaller.unmarshal(xmlFilePath.toFile()));
        } catch (JAXBException e) {
            throw new RuntimeException("Error parsing XML file", e);
        }
    }
}
