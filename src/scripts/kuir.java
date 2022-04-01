package scripts;

public class kuir {
    public static void main(String[] args) throws Exception {

        String command =  "-s";
        String path = "index.post";
        String query = "라면에는 면, 분말 스프가 파스타 볶음밥 있다.";

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
            searcher.searchQuery();
        }

    }
}
