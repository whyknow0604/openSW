package scripts;

public class kuir {
    public static void main(String[] args) throws Exception {

        String command =  "-s";
        String path = "index.post";
        String query = "건면 파스타와 생면 파스타 모두 다양한 모양과 변형을 가지고 있다.";

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
        else if(command.equals("-s")) {
            searcher searcher = new searcher(path, query);
            searcher.innerproduct();
        }

    }
}
