package scripts;

public class kuri {
    public static void main(String[] args) throws Exception {

        String command =  "-i";
        String path = "./index.xml";

        if(command.equals("-c")) {
            makeCollection collection = new makeCollection(path);
            collection.makeXml();
        }
        else if(command.equals("-k")) {
            makeKeyword keyword = new makeKeyword(path);
            keyword.convertXml();
        }
        else if(command.equals("-i")) {
            indexer indexer = new indexer(path);
            indexer.makeHashmapPost();
        }

    }
}
