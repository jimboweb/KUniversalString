import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KUniversalString {
    private List<String> strings = new ArrayList<>();

    public static void main(String[] args){
        KUniversalString kUniversalString = new KUniversalString();
        kUniversalString.run();

    }

    private void run(){
        int k = getInput();
        strings = createBinaryStrings(k);
    }

    private List<String> createBinaryStrings(int k) {
        int numStrings = 1<<k;
        for(int i=0;i<numStrings;i++){
            String nextString = "";
            for(int j=k-1; j >= 0; j--) {
                char nextChar = (i & 1 << j) >> j == 1 ? '1' : '0';
                nextString+=nextChar;
            }
            strings.add(nextString);
            return strings;
        }
    }

    private int getInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
