import java.util.*;

public class KUniversalString {

    private void run(){
        int k = getInput();
        List<String> binaryStrings = createBinaryStrings(k);
        List<DeBruijnNode> nodes = makeDeBruijnGraph(binaryStrings, k);
    }

    private List<DeBruijnNode> makeDeBruijnGraph(List<String> binaryStrings, int k){
        List<DeBruijnNode> nodes = new ArrayList<>();
        List<String> kMerOverlaps = createBinaryStrings(k-1);
        for(int i=0;i<kMerOverlaps.size();i++){
            nodes.add(new DeBruijnNode(i,kMerOverlaps.get(i)));
        }

        //this is too slow because 2^14^2 = like 270 billion
        //I'm gonna have to search with the edges
        for(int i=0;i<binaryStrings.size();i++){
            for(int j=0;j<binaryStrings.size();j++){
                String prefixString = binaryStrings.get(i);
                String suffixString = binaryStrings.get(j);
                String prefixStringOl = prefixString.substring(1);
                String sufficStringOL = suffixString.substring(0,suffixString.length()-2);
                if(prefixStringOl.equals(sufficStringOL)){
                    String suffixStringSuffix = suffixString.substring(1);
                    int firstNodeIndex = Integer.parseInt(sufficStringOL, 2);
                    int secondNodeIndes = Integer.parseInt(suffixStringSuffix,2);

                }
            }
        }
        return nodes;
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
