package scripts;

public class kuir {
    public static void main(String[] args) throws Exception {

        String command =  args[0];
        String path = args[1];



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
            if(args[2].equals("-q")){
                searcher searcher = new searcher(path, args[3]);
                searcher.CalcSim();
            }

        }

    }
}
