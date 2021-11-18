/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioxpath;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author xp
 */
public class gestionXPath {
    Document doc = null;
    String datos_nodo[]= null;
    public int abrir_XML_Path (File fichero){
        try {
            //Se crea un objeto DocumentBuiderFactory.
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            //Indica que el modelo DOM no debe contemplar los comentarios
            //que tenga el XML
            factory.setIgnoringComments(true);
            //Ignora los espacios en blanco que tenga el documento
            factory.setIgnoringElementContentWhitespace(true);
            //Se crea un objeto DocumentBuilder para cargar en él la
            //estructura de árbol DOM a partir del XML seleccionado.
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Interpreta (parsear) el documento XML (file) y genera el
            //DOM equivalente.
            doc= builder.parse(fichero);
            //Ahora doc apunta al árbol DOM listo para ser recorrido
            return 0;
        } catch (Exception e) {
        }
        return -1;
        
    }
    public String EjecutaXPath(String nombre, String busqueda){
        try {    
            //Creamos un string vacio llamado salida.
            String salida = "";
            //Crea el objeto XPath
            XPath xPath = XPathFactory.newInstance().newXPath();
            //Crea un XPathExpresion con la consulta deseada
            XPathExpression exp = xPath.compile(busqueda);
            //Ejecuta la consulta indicando que se ejecute sobre el DOM y que devolverá el resultado como una lista de nodos.
            Object result = exp.evaluate(doc, XPathConstants.NODESET);
            NodeList nodeList = (NodeList) result;
            //Ahora recorre la lista para sacar los resultados.
            //Como sé que me devuelve nodos autor, su hijo será el nodos
            //Text que contiene el valor , es decir el nombre del autor.
            for(int i = 0; i < nodeList.getLength(); i++){
                
                if(nodeList.item(i).getNodeName() == "Libro"){
                    datos_nodo = procesarLibro(nodeList.item(i));
                    salida=salida + "\n" + "Publicado en: " + datos_nodo[0];
                    salida=salida + "\n" + "El titulo es: " + datos_nodo[1];
                    salida=salida + "\n" + "El autor es: " + datos_nodo[2];
                    salida=salida + "\n --------------------------------";
                }else{
                    salida = salida + "\n" + nodeList.item(i).getFirstChild().getNodeValue();
                }
            }
            return salida;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return null;
        }
    }
    
    private String[] procesarLibro(Node n){
        try {
            String datos[]= new String[3];
            Node ntemp= null;
            int contador = 1;

            datos[0] = n.getAttributes().item(0).getNodeValue();

            NodeList nodos = n.getChildNodes();

            for(int i=0; i<nodos.getLength(); i++){
                ntemp = nodos.item(i);

                if(ntemp.getNodeType() == Node.ELEMENT_NODE){
                    datos[contador] = ntemp.getFirstChild().getNodeValue();
                    contador++;
                }
            }
            return datos;
        } catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }
}
