package scripts;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;

public class indexer {
    private String input_file;
    private String output_flie = "./index.post";

    public indexer(String file) {
        this.input_file = file;
    }

    public void makeHashmapPost() throws Exception{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document kkmDoc = docBuilder.parse(input_file);

        Element root = kkmDoc.getDocumentElement();
        NodeList list = root.getElementsByTagName("body");

        FileOutputStream fileStream = new FileOutputStream(output_flie);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);

        HashMap indexMap = new HashMap();

        HashMap[] bodyMaps = new HashMap[5];
        for(int i = 0; i < bodyMaps.length; i++){
            bodyMaps[i] = new HashMap();
        }

        for(int i = 0; i < list.getLength(); i++){
            Element element = (Element) list.item(i);
            String bodyText = element.getTextContent();
            String[] index = bodyText.split("#");
            for(int j = 0; j < index.length; j++) {
                String[] key = index[j].split(":");
                if(indexMap.get(key[0]) != null){
                    int result = Integer.parseInt(key[1]) +Integer.parseInt(String.valueOf(indexMap.get(key[0])));
                    indexMap.put(key[0],Integer.toString(result));
                }
                else{
                    indexMap.put(key[0], key[1]);
                }
                bodyMaps[i].put(key[0], key[1]);
            }
        }

        Iterator<String> it = indexMap.keySet().iterator();

        while(it.hasNext()){
            String key= it.next();
            String value = "";
            int df = 0;
            int[] tf = new int[5];
            for(int i = 0; i < 5; i++) {
                if (bodyMaps[i].get(key) != null) {
                    df++;
                    tf[i] = Integer.parseInt(String.valueOf(bodyMaps[i].get(key)));
                }
            }
            for(int i =0; i< 5; i++){
                double exp = tf[i] * Math.log(5/df);
                value += (" "+i+" "+Math.round(exp*100)/100.0);
            }
            indexMap.put(key,value);
            System.out.println(key+" →"+value);
        }

        objectOutputStream.writeObject(indexMap);
        objectOutputStream.close();

    }
}
