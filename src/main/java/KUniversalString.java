import java.util.*;

public class KUniversalString {

    private void run(){
        int k = getInput();
        List<String> binaryStrings = createBinaryStrings(k);
        List<DeBruijnNode> nodes = createNodes(binaryStrings,k);
        List<DeBruijnEdge> edges = createEdges(binaryStrings,nodes);
        Deque<Integer> circuit = makeEulerianCircuit(edges,nodes.size());
    }



    private List<DeBruijnEdge> createEdges(List<String> binaryStrings, List<DeBruijnNode> nodes){
        List<DeBruijnEdge> edges = new ArrayList<>(binaryStrings.size());
        for (int i = 0; i < binaryStrings.size(); i++) {
            edges.add(new DeBruijnEdge(i,binaryStrings.get(i)));
        }
        for(DeBruijnNode node:nodes){
            for(Integer incoming:node.getIncomingEdges()){
                for(Integer outgoing:node.getOutgoingEdges()){
                    edges.get(incoming).addConnectingEdge(outgoing);
                }
            }
        }
        return edges;
    }


    private List<DeBruijnNode> createNodes(List<String> binaryStrings, int k){
        List<DeBruijnNode> nodes = new ArrayList<>(1<<(k-1));
        List<String> kMerOverlaps = createBinaryStrings(k-1);
        for(int i=0;i<kMerOverlaps.size();i++){
            nodes.add(new DeBruijnNode(i,kMerOverlaps.get(i)));
        }
        nodes = populateDeBruijnNodes(binaryStrings, nodes);
        return nodes;
    }

    private List<DeBruijnNode> populateDeBruijnNodes(List<String> binaryStrings, List<DeBruijnNode> nodes) {
        for(int i=0;i<binaryStrings.size();i++){
            String binaryString = binaryStrings.get(i);//this works because the node's integer index is equal to its binary value
            int prefix = Integer.parseInt(binaryString.substring(0,binaryString.length()-1), 2);
            nodes.get(prefix).addOutgoingEdge(i);
            int suffix = Integer.parseInt(binaryString.substring(1), 2);
            nodes.get(suffix).addIncomingEdge(i);
        }
        return nodes;
    }


    private List<String> createBinaryStrings(int k) {
        int numStrings = 1<<k;
        List<String> binaryStrings = new ArrayList<>(numStrings);
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
        private final int index;
        private final String str;
        private List<Integer> outgoingEdges;
        private List<Integer> incomingEdges;

        public DeBruijnNode(int index, String str) {
            this.index = index;
            this.str = str;
            outgoingEdges = new ArrayList<>();
            incomingEdges = new ArrayList<>();
        }

        public int getIndex() {
            return index;
        }

        public String getStr() {
            return str;
        }

        public void addIncomingEdge(int e){
            incomingEdges.add(e);
        }

        public void addOutgoingEdge(int e){
            outgoingEdges.add(e);
        }

        public List<Integer> getOutgoingEdges() {
            return outgoingEdges;
        }

        public List<Integer> getIncomingEdges() {
            return incomingEdges;
        }

        @Override
        public String toString() {
            String rtrn = "Node {";
            rtrn += "Index: " + index;
            rtrn += " String: " + str;
            rtrn += " " + outgoingEdges.size() + " connected nodes";
            return rtrn;
        }
    }

    private class DeBruijnEdge{
        Deque<Integer> connectingEdges = new ArrayDeque<>();
        final int index;
        final String str;
        public  DeBruijnEdge(int index, String str){
            this.index = index;
            this.str = str;
        }

        public int getIndex() {
            return index;
        }

        public String getStr() {
            return str;
        }

        public void addConnectingEdge(Integer edge){
            connectingEdges.push(edge);
        }

        public Integer pop(){
            return connectingEdges.pop();
        }

        public int size(){
            return connectingEdges.size();
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

    Deque<Integer> makeEulerianCircuit(List<DeBruijnEdge> edges, int numberOfNodes)
    {

        // return empty list for empty graph
        if (edges.size()==0)
            return new ArrayDeque<>();

        // Stack for the path in the current iteration
        Deque<Integer> currentPath = new ArrayDeque<>();

        // queue for the final circuit
        Deque<Integer> circuit = new ArrayDeque<>();

        // start from any vertex
        currentPath.push(0);
        int currentVertexNumber = 0; // Current vertex

        while (!currentPath.isEmpty())
        {
            //if there are outgoing edges
            if (edges.get(currentVertexNumber).size() > 0)
            {
                currentPath.push(currentVertexNumber);
                currentVertexNumber = edges.get(currentVertexNumber).pop();
            }

            // otherwise step back
            else
            {
                circuit.add(currentVertexNumber);
                currentVertexNumber = currentPath.pop();
            }
        }
        //FIXME: it's returning each mini-circuit twice. 
        return circuit;

    }


}
