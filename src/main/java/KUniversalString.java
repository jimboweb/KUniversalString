import java.util.*;

public class KUniversalString {

    private void run(){
        int k = getInput();
        List<String> binaryStrings = createBinaryStrings(k);
        List<DeBruijnNode> nodes = createNodes(binaryStrings,k);
        Deque<Integer> circuit = makeEulerianCircuit(nodes,nodes.size());
        String kUniversalString = composeKuinversalString(circuit,nodes);
        //kUniversalString = removeCircularCharacters(kUniversalString);
        System.out.println(kUniversalString);
    }

    private String composeKuinversalString(Deque<Integer> circuit, List<DeBruijnNode> nodes){
        String rtrn = nodes.get(circuit.pollLast()).str;
        while(circuit.size()>2){
            DeBruijnNode nextNode = nodes.get(circuit.pollLast());
            String nextStr = nextNode.str;
            rtrn += nextStr.charAt(nextStr.length()-1);
        }
        return rtrn;
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
            DeBruijnNode from = nodes.get(prefix);
            int suffix = Integer.parseInt(binaryString.substring(1), 2);
            DeBruijnNode to = nodes.get(suffix);
            from.pushConnectedNode(to.index);
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
        private Deque<Integer> connectedNodes;

        public DeBruijnNode(int index, String str) {
            this.index = index;
            this.str = str;
            connectedNodes = new ArrayDeque<>();
        }

        public int getIndex() {
            return index;
        }

        public String getStr() {
            return str;
        }


        public void addOutgoingEdge(int e){
            connectedNodes.add(e);
        }

        public void pushConnectedNode(Integer n){
            connectedNodes.push(n);
        }

        public Integer popConnectedNode(){
            return connectedNodes.pop();
        }

        public int size(){
            return connectedNodes.size();
        }

        public boolean isEmpty(){
            return connectedNodes.isEmpty();
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

    Deque<Integer> makeEulerianCircuit(List<DeBruijnNode> nodes, int numberOfNodes)
    {

        // return empty list for empty graph
        if (nodes.size()==0)
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
            //if there are outgoing nodes
            if (nodes.get(currentVertexNumber).size() > 0)
            {
                currentPath.push(currentVertexNumber);
                currentVertexNumber = nodes.get(currentVertexNumber).popConnectedNode();
            }

            // otherwise step back
            else
            {
                circuit.add(currentVertexNumber);
                currentVertexNumber = currentPath.pop();
            }
        }
        return circuit;

    }

    private String removeCircularCharacters(String str){
        while(str.charAt(str.length()-1) ==str.charAt(0)){
            str = str.substring(0,str.length()-1);
        }
        return str;
    }

}
