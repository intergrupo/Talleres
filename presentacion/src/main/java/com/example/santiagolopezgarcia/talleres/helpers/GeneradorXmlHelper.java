package com.example.santiagolopezgarcia.talleres.helpers;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by santiagolopezgarcia on 9/28/17.
 */

public class GeneradorXmlHelper {

    public File generarXml(String stringXml, String nombreXml) {
        String xmlRecords = stringXml;
        Document d1 = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            d1 = builder.parse(new InputSource(new StringReader(xmlRecords)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Source source = new DOMSource(d1);

            File file = new File(Constantes.traerRutaAdjuntos() + nombreXml);
            Result result = new StreamResult(file);

            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
            return file;
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        }
        return null;
    }
}