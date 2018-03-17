import java.util.*;

public class KUniversalString {

    private void run(){
        int k = getInput();
        List<String> binaryStrings = createBinaryStrings(k);
        List<DeBruijnNode> nodes = makeDeBruijnGraph(binaryStrings, k);
        List<DeBruijnNode> contigGraph = makeContigGraph(nodes);
    }

    private List<DeBruijnNode> makeDeBruijnGraph(List<String> edges, int k){
        List<DeBruijnNode> nodes = new ArrayList<>();
        List<String> kMerOverlaps = createBinaryStrings(k-1);
        for(int i=0;i<kMerOverlaps.size();i++){
            nodes.add(new DeBruijnNode(i,kMerOverlaps.get(i)));
        }
        for(String edge:edges){
            //this works because the node's integer index is equal to its binary value
            int prefix = Integer.parseInt(edge.substring(0,edge.length()-1), 2);
            DeBruijnNode from = nodes.get(prefix);
            int suffix = Integer.parseInt(edge.substring(1), 2);
            DeBruijnNode to = nodes.get(suffix);
            from.pushConnectedNode(to.index);
            //add incoming node here if useful
        }
        return nodes;
    }

    private List<DeBruijnNode> makeContigGraph(List<DeBruijnNode> nodes){
        return null;
    }

    private List<String> createBinaryStrings(int k) {
        List<String> binaryStrings = new ArrayList<>();
        int numStrings = 1<<k;
        for(int i=0;i<numStrings;i++){
            String nextString = "";
            for(int j=k-1; j >= 0; j--) {
                char nextChar = (i & 1 << j) >> j == 1 ? '1' : '0';
                nextString+=nextChar;
            }
            //System.out.println(nextString);
            binaryStrings.add(nextString);
        }
        return binaryStrings;
    }

    private class DeBruijnNode {
        int index;
        String str;
        Deque<Integer> connectedNodes;
        //list of incoming nodes?

        public DeBruijnNode(int index, String str) {
            this.index = index;
            this.str = str;
            connectedNodes = new ArrayDeque<>();
        }

        public void pushConnectedNode(Integer node){
            connectedNodes.push(node);
        }

        public Integer popConnectedNode(){
            return connectedNodes.pollLast();
        }

        @Override
        public String toString() {
            String rtrn = "Node {";
            rtrn += "Index: " + index;
            rtrn += " String: " + str;
            rtrn += " " + connectedNodes.size() + " connected nodes";
            return rtrn;
        }
    }
    public static void main(String[] args){
        KUniversalString kUniversalString = new KUniversalString();
        kUniversalString.run();

    }


    private int getInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


}
