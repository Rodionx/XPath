/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioxpath;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author xp
 */
public class gestionXPath {
    public int EjecutaXPath(){
        try {
            //Declaramos el String salida para usarlo despues en cualquier funcion.
            String salida = "";
            //Crea un objeto DocumentBuilderFactory para el DOM(JAXP)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Crear un árbol DOM (parsear) con el archivo LibrosXML.xml
            Document XMLDoc = factory.newDocumentBuilder().parse(new File("Libros.xml"));
            //Crea el objeto XPath
            XPath xPath = XPathFactory.newInstance().newXPath();
            //Crea un XPathExpresion con la consulta deseada
            XPathExpression exp = xPath.compile("/Libros/*/Autor");
            //Ejecuta la consulta indicando que se ejecute sobre el DOM y que devolverá el resultado como una lista de nodos.
            Object result = exp.evaluate(XMLDoc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;
            //Ahora recorre la lista para sacar los resultados.
            //Como sé que me devuelve nodos autor, su hijo será el nodos
            //Text que contiene el valor , es decir el nombre del autor.
            for(int i = 0; i < nodeList.getLength(); i++){
                salida = salida + "\n" + nodeList.item(i).getFirstChild().getNodeValue();
            }
            System.out.println(salida);
            return 0;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return -1;
        }
    }
}
