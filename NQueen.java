'''
argv[1] : Board size
'''

// java モジュール
import java.util.Collections;

public class NQueen {
    //Boardを出力
    public static void board_print(int gene, int size){
        for (int i = 0; i < gene; i++){
            for (int j = 0; j < size; j++):
                if (i == j){
                    System.out.printf("1" + " ");
                }else{
                    System.out.printf("0" + " ");
                }
            System.out.println();
    }


    //遺伝子生成
    public static int makeIniGene(int gene_num, int size){
        Ini_gene<String> ini_gene [] = new ArrayList<String>();
        for cnt in range(0, gene_num){
            Line<String> line [] = new ArrayList<String>();
            for(int i = 0; i < size; i++){
                line.add(i);
            }
            Collections.shuffle(line);
            ini_gene.add(line);
        }
        return ini_gene;
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


    public static int getCell(int board, String pos[], String ofst[], int size) {
        int posx;
        int posy;
        posx = pos[0] + ofst[0]
        posy = pos[1] + ofst[1]
        if (posx >= 0 && posy >= 0 && posx < size && posy < size) {
            val = board[posx][posy]
        } else {
            val = -1
        }
        return val;
    }


    //順列表現と順序表現へ変換
    public static int p_to_o(int gene, int size) {
      Board<String> board [] = new ArrayList<String>();
        Converted<String> converted [] = new ArrayList<String>();
        Num_list<String> num_list [] = new ArrayList<String>();
        //数値が1からサイズ分まで順番に入っているリストを作成
        for(String num: size){
            num_list.append(num)
        }

        //遺伝子変換
        for (String num: gene){
            converted.add(num_list.index(num));
            num_list.removeAll(num);
        }
        return converted;
    }

    //順序表現から順列表現へ変換
    def o_to_p(gene, size):
        converted = []
        num_list = []

        for num in range(size):
            num_list.append(num)

        for num in gene:
            converted.append(num_list.pop(num))

        return converted


    //交叉
    //遺伝子のリストを渡すと交叉する
    def cross (ini_gene, size):
        for i in range(int(len(ini_gene)/2)):
            n = i * 2
            num = random.randint(1,size-2)

            tmp = ini_gene[n][0:num]
            tmp.extend(ini_gene[n+1][num:])

            ini_gene[n+1] = ini_gene[n+1][0:num]
            ini_gene[n+1].extend(ini_gene[n][num:])

            ini_gene[n] = tmp


    //突然変異
    def mutation(gene_list, size):
        if random.randint(0, 4) == 0:
            gene = random.randint(0,len(gene_list)-1)
            random.shuffle(gene_list[gene])
    
    //適応度を基準にソートし，淘汰と増殖を行う(Java)

    public static int[][] geneSort(int geneList[][], int size) {
        
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
            System.out.println("終わり");
            // boardの出力
            // 時間の出力
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
        size = int(argv[1])

        //１世代あたりの遺伝子の数
        gene_num = 4

        //初期集団の生成
        gene_list = makeIniGene(gene_num, size)

        //ループに入る
        n = 1
        while (true){
            //遺伝子が評価され，淘汰され，増殖する
            gene_list = gene_sort(gene_list, size, n)

            //交叉する
            cross_gene = []
            for gene in gene_list:
                cross_gene.append(p_to_o(gene, size))

            cross(cross_gene, size)

            gene_list = []
            for gene in cross_gene:
                gene_list.append(o_to_p(gene, size))

            //突然変異する
            mutation(gene_list, size)

            n += 1
        }
    }

}
