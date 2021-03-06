// java モジュール
import java.util.*;

class Fitness {

    int n;
    int size;
    long start;

    Fitness(int n, int size, long start){
        this.n = n;
        this.size = size;
        this.start = start;
    }

    int fitness(int [] gene){

        int fitness = 0;
        int [][] gVec = {{-1,-1},{-1,1},{1,-1},{1,1}};

        for(int i = 0; i < size; i ++){
            for(int j = 0; j < gVec.length; j ++){
                for(int k = 1; k < size; k ++){
                    int x = gVec[j][1] * k + gene[i];
                    int y = gVec[j][0] * k + i;
                    if((x < 0) || (x >= size) || (y < 0) || (y >= size)){
                        break;
                    }
                    if(x == gene[y]){
                        fitness ++;
                    }
                }
            }
        }
        return fitness;
    }

    void boardprint(int [] gene){

        long end = System.currentTimeMillis();
        for (int i = 0; i < gene.length-1; i++){
            for (int j = 0; j < size; j++){
                if (gene[i] == j){
                    System.out.print("Q" + " ");
                }else{
                    System.out.print("." + " ");
                }
            }
            System.out.println(" ");
        }
        System.out.println("Time = " + (float)(end - start)/1000  + "sec");
    }

    int [][] sort(int [][] geneList){

        int [][] fitGene = new int [geneList.length][size+1];
        int [][] sortList = new int [geneList.length][size];

        for (int i = 0; i < geneList.length; i++){
                int [] addGene = geneList[i];

                for (int j = 0; j < size+1; j++){
                    if(j == size){
                        fitGene[i][j] = fitness(addGene); 
                    }else{
                        fitGene[i][j] = addGene[j];
                    }
                }
        }

        Arrays.sort(fitGene, (a, b) -> Integer.compare(a[geneList[0].length], b[geneList[0].length]));

        System.out.println(fitGene[0][size]);

        if(fitGene[0][size]==0){
            System.out.println("count = " + n);
            boardprint(fitGene[0]);
            System.exit(0);
        }

        for (int i = 0; i < geneList.length; i++){
                int [] addGene = fitGene[i];
                for (int j = 0; j < size; j++){
                    sortList[i][j] = addGene[j];
                }
        }
        int [] tmp = sortList[0].clone();
        sortList[geneList.length-1] = tmp;
        sortList[1] = tmp;

        return sortList;
    }
}
