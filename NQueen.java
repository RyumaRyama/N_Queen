//argv[1] : Board size

// java モジュール
import java.util.*;

public class NQueen {
    //Boardを出力
    public static void board_print(int gene, int size){
        for (int i = 0; i < gene; i++){
            for (int j = 0; j < size; j++){
                if (i == j){
                    System.out.print("Q" + " ");
                }else{
                    System.out.print("." + " ");
                }
            }
            System.out.println(" ");
         }
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
        int [][] array = ini_gene.toArray(new int[ini_gene.size()][size]);
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

/*
    public static int getCell(int board, int pos[], int ofst[], int size) {
        int posx;
        int posy;
        posx = pos[0] + ofst[0];
        posy = pos[1] + ofst[1];
        if (posx >= 0 && posy >= 0 && posx < size && posy < size) {
            val = board[posx][posy];
        } else {
            val = -1;
        }
        return val;
    }
*/


    //順列表現と順序表現へ変換
    public static int [] p_to_o(int gene[], int size) {

        ArrayList<Integer> converted = new ArrayList<Integer>();
        ArrayList<Integer> num_list = new ArrayList<Integer>();
        //数値が1からサイズ分まで順番に入っているリストを作成
        for(int num = 1; num <= size; num ++){
            num_list.add(num);
        }

        //遺伝子変換
        for (int i = 0; i < size; i ++){
            converted.add(num_list.indexOf(gene[i]));
            num_list.remove(gene[i]);
        }

        int [] array = converted.toArray(new int[size]);
        return array;
    }

    //順序表現から順列表現へ変換
    public static int [] o_to_p(int gene[], int size){
        ArrayList<Integer> converted = new ArrayList<Integer>();
        ArrayList<Integer> num_list = new ArrayList<Integer>();

        for(int num = 1; num <= size; num ++){
            num_list.add(num);
        }

        for (int i = 0; i < size; i ++){
            converted.add(num_list.get(gene[i]));
            num_list.remove(gene[i]);
        }

        int [] array = converted.toArray(new int[size]);
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
    public static int[][] geneSort(int geneList[][], int size, int n) {

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
            board_print(fitGene[0][0], size);
            
            System.exit(0);
        }

        for (int i = 0; i < geneList.length; i++){
                int [] addGene = fitGene[i];
                for (int j = 0; j < size; j++){
                    sortList[i][j] = addGene[j];
                }
        }

        sortList[geneList.length-1] = sortList[0];
        return sortList;

    }



    //NQueen本体
    public static void main(String args[]) {
        //NxNのBoard，N個のQueen，のN
        int size = Integer.parseInt(args[1]);

        //１世代あたりの遺伝子の数
        int geneNum = 4;

        //初期集団の生成
        int [][] geneList = makeIniGene(geneNum, size);

        //ループに入る
        int n = 1;
        while (true){
            //遺伝子が評価され，淘汰され，増殖する
            geneList = geneSort(geneList, size, n);

            //交叉する
            int [][] crossGene = new int[geneList.length][size];

            /*
            for gene in geneList:
                crossGene.append(p_to_o(gene, size))
            */

            for (int i = 0; i < geneList.length; i ++){

                crossGene[i] = p_to_o(geneList[i], size);

            }


            
            geneList = cross(crossGene, size);



            for (int i = 0; i < geneList.length; i ++){

                crossGene[i] = o_to_p(geneList[i], size);

            }
            /*
            for gene in cross_gene:
                gene_list.append(o_to_p(gene, size));
            */

            //突然変異する
            mutation(geneList, size);

            n += 1;
        }
    }

}
