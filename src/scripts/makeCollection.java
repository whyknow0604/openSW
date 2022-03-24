package scripts;

import org.jsoup.Jsoup;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class makeCollection {
    private String data_path;
    private String output_file = "./collection.xml";

    public makeCollection(String path){
        this.data_path = path;
    }

    public void makeXml() throws Exception {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        //document 생성
        org.w3c.dom.Document myDoc = docBuilder.newDocument();

        //docs 태그 생성
        Element docs = myDoc.createElement("docs");
        myDoc.appendChild(docs);


        File[] fileList = makefile();

        // docs 태그 안의 id 변수 생성
        int id = 0;

        // file[] 메서드를 이용해 폴더 안에 있는 파일 읽기
        for (File file : fileList) {
            // doc 태그 생성
            Element doc = myDoc.createElement("doc");
            // doc 태그 내의 id 값 설정
            doc.setAttribute("id", String.valueOf(id));
            id += 1;
            //docs태그의 하위 태그로 설정
            docs.appendChild(doc);

            //jsoup을 이용하여 html태그를 string으로 parsing
            org.jsoup.nodes.Document html = Jsoup.parse(file,"UTF-8");
            String titleData = html.title();
            String bodyData = html.body().text();


            //title 태그 생성
            Element title = myDoc.createElement("title");
            title.appendChild(myDoc.createTextNode(titleData));
            doc.appendChild(title);

            //body 태그 생성
            Element body = myDoc.createElement("body");
            body.appendChild(myDoc.createTextNode(bodyData));
            doc.appendChild(body);
        }
        //xml 파일로 출력하기 위해 TrasformerFactory를 사용
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        //Encoding을 UTF-8로 설정함
        transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");

        //docs를 Dom 타입으로 변환
        DOMSource source = new DOMSource(myDoc);
        StreamResult result = new StreamResult(new FileOutputStream(new File(output_file)));

        transformer.transform(source,result);
    }

    public File[] makefile(){
        String readFilePath = this.data_path;
        System.out.println(readFilePath);
        File rw = new File(readFilePath);
        return rw.listFiles();
    }

}
