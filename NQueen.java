//argv[1] : Board size

// java モジュール
import java.util.*;

public class NQueen {
    //Boardを出力
    public static void boardPrint(int gene[], int size, long start){
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
        System.out.println((end - start)  + "ms");
    }


    //遺伝子生成
    public static int[][] makeIniGene(int gene_num, int size){
        ArrayList<ArrayList<Integer>> ini_gene = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < gene_num; i++){
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

    public static int calcFitness(int gene[], int size){

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

    //順列表現と順序表現へ変換
    public static int [] p_to_o(int gene[], int size) {

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

    //順序表現から順列表現へ変換
    public static int [] o_to_p(int gene[], int size){
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

    //交叉
    //遺伝子のリストを渡すと交叉する
    public static int[][] cross(int geneList[][], int size){

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

    //突然変異
    public static int[][] mutation(int geneList[][], int size){

        Random rand = new Random();

        int gene = rand.nextInt(geneList.length);
        int num1 = rand.nextInt(size - 1);
        int num2 = rand.nextInt(size - 1);

        int tmp = geneList[gene][num1];
        geneList[gene][num1] = geneList[gene][num2];
        geneList[gene][num2] = tmp;

        return geneList;
    }

    //適応度を基準にソートし，淘汰と増殖を行う(Java)
    public static int[][] geneSort(int geneList[][], int size, int n, long start) {

        int [][] fitGene = new int [geneList.length][size+1];
        int [][] sortList = new int [geneList.length][size];

        for (int i = 0; i < geneList.length; i++){
                int [] addGene = geneList[i];
                for (int j = 0; j < size+1; j++){
                    if(j == size){
                        fitGene[i][j] = calcFitness(addGene, size);
                    }else{
                        fitGene[i][j] = addGene[j];
                    }
                }
        }

        Arrays.sort(fitGene, (a, b) -> Integer.compare(a[geneList[0].length], b[geneList[0].length]));

        if(fitGene[0][size]==0){
            System.out.println("count = " + n);
            boardPrint(fitGene[0], size, start);
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



    //NQueen本体
    public static void main(String args[]) {
        //時間の測定
        long start = System.currentTimeMillis();
        //NxNのBoard，N個のQueen，のN
        int size = Integer.parseInt(args[0]);

        //１世代あたりの遺伝子の数
        int geneNum = 4;

        //初期集団の生成
        int [][] geneList = makeIniGene(geneNum, size);

        //ループに入る
        int n = 1;
        while (true){
            //遺伝子が評価され，淘汰され，増殖する
            geneList = geneSort(geneList, size, n, start);

            //交叉する
            int [][] crossGene = new int[geneList.length][size];

            for (int i = 0; i < geneList.length; i ++){
                crossGene[i] = p_to_o(geneList[i], size);
            }
            
            geneList = cross(crossGene, size);

            for (int i = 0; i < geneList.length; i ++){
                crossGene[i] = o_to_p(geneList[i], size);
            }

            //突然変異する
            mutation(geneList, size);

            n += 1;
        }
    }

}
