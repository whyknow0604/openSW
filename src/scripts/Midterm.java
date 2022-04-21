package scripts;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class Midterm {
    private String data_path;
    private String query = "";

    String[] titleArray;
    String[] bodyArray;
    String[] query_keyword_Array;
    KeywordList[] bodyKeywordList;

    public Midterm(String path, String query) {
        this.data_path = path;
        this.query = query;
    }

    public void showSnippet() throws Exception {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(data_path);
        Element root = doc.getDocumentElement();
        NodeList Bodylist = root.getElementsByTagName("body");
        NodeList Titlelist = root.getElementsByTagName("title");

        KeywordExtractor ke = new KeywordExtractor();
        KeywordList klBody = ke.extractKeyword(query,true);

        titleArray = new String[Titlelist.getLength()];
        bodyArray = new String[Bodylist.getLength()];
        bodyKeywordList = new KeywordList[Bodylist.getLength()];
        query_keyword_Array = new String[klBody.size()];
        for(int i = 0; i < Titlelist.getLength(); i++){
            titleArray[i] = Titlelist.item(i).getTextContent();
            bodyArray[i] = Bodylist.item(i).getTextContent();
        }

        for(int i = 0; i < klBody.size(); i++){
            Keyword kwrd = klBody.get(i);
            query_keyword_Array[i] = kwrd.getString();
        }

        for(int i = 0; i < Titlelist.getLength(); i++){
            KeywordList bodykeyword = ke.extractKeyword(bodyArray[i],true);
            bodyKeywordList[i] = bodykeyword;
        }

        for(int i = 0; i < Titlelist.getLength(); i++){
            System.out.println(i);
            for(int j = 0; j < bodyKeywordList[i].size(); j++){
                System.out.print(bodyKeywordList[i].get(j).getString()+ " ");
            }
        }

        for(int i = 0; i < query_keyword_Array.length; i++){
            for(int j = 0; j < titleArray.length; j++){
                for(int x = 0; x < bodyKeywordList[j].size(); x++){
                    if(bodyKeywordList[j].get(x).getString().equals(query_keyword_Array[i])){
                        
                    }

                }
            }
        }

    }
}

