package scripts;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class makeKeyword {
    private String input_file;
    private String output_flie = "./index.xml";

    public makeKeyword(String file) {
        this.input_file = file;
    }

    public void convertXml() throws Exception{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document kkmDoc = docBuilder.parse(input_file);

        Element root = kkmDoc.getDocumentElement();
        NodeList list = root.getElementsByTagName("doc");
        for(int i = 0; i < list.getLength(); i++){
            Element element = (Element) list.item(i);
            String bodyText = element.getLastChild().getTextContent();

            KeywordExtractor ke = new KeywordExtractor();
            KeywordList klBody = ke.extractKeyword(bodyText,true);
            String kkmtext= "";
            for (int j = 0; j< klBody.size(); j++){
                Keyword kwrd = klBody.get(j);
                kkmtext = kkmtext + (kwrd.getString() + ":" + kwrd.getCnt() + "#");

            }
            element.getLastChild().setTextContent(kkmtext);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();


        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");


        DOMSource source = new DOMSource(kkmDoc);
        StreamResult result = new StreamResult(new FileOutputStream(new File(output_flie)));

        transformer.transform(source,result);

    }

}
