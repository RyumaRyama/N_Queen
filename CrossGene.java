// java モジュール
import java.util.*;

class CrossGene {

    int size;

    CrossGene(int size){
        this.size = size;
    }

    int [] p_to_o(int [] gene){
        ArrayList<Integer> converted = new ArrayList<Integer>();
        ArrayList<Integer> num_list = new ArrayList<Integer>();
        //数値が1からサイズ分まで順番に入っているリストを作成
        for(int num = 0; num < size; num ++){
            num_list.add(num);
        }
        //遺伝子変換
        for (int i = 0; i < size; i ++){
            converted.add(num_list.indexOf(gene[i]));
            num_list.remove(num_list.indexOf(gene[i]));
        }

        int [] array = new int[size];

        for (int i = 0; i < size; i++){
            array[i] = converted.get(i); 
        }
        return array;
    }

    int [] o_to_p(int [] gene){
        ArrayList<Integer> converted = new ArrayList<Integer>();
        ArrayList<Integer> num_list = new ArrayList<Integer>();

        for(int num = 0; num < size; num ++){
            num_list.add(num);
        }

        for (int i = 0; i < size; i ++){
            converted.add(num_list.get(gene[i]));
            num_list.remove(gene[i]);
        }

        int [] array = new int[size];

        for (int i = 0; i < size; i++){
            array[i] = converted.get(i); 
        }
        return array;
    }

    int [][] cross(int [][] geneList){

        Random rand = new Random();

        int len = geneList.length/2;
        for (int i = 0; i < len; i ++){
            int n = i * 2;
            int num = rand.nextInt(size - 1) + 1;
            int [] tmpList = geneList[n].clone();

            for(int j = num; j < size; j ++){
                geneList[n][j] = geneList[n+1][j];
                geneList[n+1][j] = tmpList[j];
            }
        }
        return geneList;
    }

    int [][] mutation(int [][] geneList){
        Random rand = new Random();

        int gene = rand.nextInt(geneList.length);
        int num1 = rand.nextInt(size);
        int num2 = rand.nextInt(size);

        int tmp = geneList[gene][num1];
        geneList[gene][num1] = geneList[gene][num2];
        geneList[gene][num2] = tmp;

        return geneList;
    }
}

