//argv[1] : Board size

// java モジュール
import java.util.*;

public class NQueen {
    public static void main(String args[]) {

        //時間の測定
        long start = System.currentTimeMillis();

        //NxNのBoard，N個のQueen，のN
        int size = Integer.parseInt(args[0]);

        //１世代あたりの遺伝子の数
        int geneNum = 4;

        MakeIniGene make = new MakeIniGene(geneNum, size);

        //初期集団の生成
        int [][] geneList = make.makeGene();
       
        //ループに入る
        int n = 1;

        while (true) {

            Fitness fitness = new Fitness(n, size, start);
            CrossGene cross = new CrossGene(size);

            //遺伝子が評価され，淘汰され，増殖する
            geneList = fitness.sort(geneList);

            //交叉する
            int [][] crossGene = new int[geneList.length][size];

            //順列表現から順序表現へ
            for (int i = 0; i < geneList.length; i ++){
                crossGene[i] = cross.p_to_o(geneList[i]);
            }
            
            geneList = cross.cross(crossGene);

            //順序表現から順列表現へ
            for (int i = 0; i < geneList.length; i ++){
                crossGene[i] = cross.o_to_p(geneList[i]);
            }

            //突然変異する
            cross.mutation(geneList);

            n += 1;

        }
    }
}

class MakeIniGene {

    int geneNum;
    int size;

    MakeIniGene(int geneNum, int size) {
        this.geneNum = geneNum;
        this.size = size;
    }

    int [][] makeGene(){

        ArrayList<ArrayList<Integer>> ini_gene = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < geneNum; i++){
            ArrayList<Integer> line = new ArrayList<Integer>();
            for(int j = 0; j < size; j++){
                line.add(j);
            }
            Collections.shuffle(line);
            ini_gene.add(line);
        }

        int [][] array = new int[ini_gene.size()][size];
        for(int i = 0; i < ini_gene.size(); i ++){
            for (int j = 0; j < size; j ++){
               array[i][j] = ini_gene.get(i).get(j); 
            }
        }
        return array;
    }
}

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
        long end = System.currentTimeMillis();
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
        sortList[geneList.length-1] = sortList[0];
        sortList[1] = sortList[0];
        return sortList;
    }

}

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
            int num = rand.nextInt(size -2) + 1;
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
        int num1 = rand.nextInt(size - 1);
        int num2 = rand.nextInt(size - 1);

        int tmp = geneList[gene][num1];
        geneList[gene][num1] = geneList[gene][num2];
        geneList[gene][num2] = tmp;

        return geneList;
    }
}

