import java.util.*;

public class Gene {

    int geneNum;
    int size;
    private int [] gene;

    Gene(int geneNum, int size){

        this.geneNum = geneNum;
        this.size = size;

    }

    
    int [] makeGene(){

        ArrayList<Integer> line = new ArrayList<Integer>();
        for(int j = 0; j < size; j++){
            line.add(j);
        }
        Collections.shuffle(line);

        int [] array = new int[size];
        for (int j = 0; j < size; j ++){
           array[j] = line.get(j); 
        }
        return array;
    }

    public void getGene(){
        this.gene = makeGene();
    }

    public int [] setGene(){
        return gene;
    }

}
