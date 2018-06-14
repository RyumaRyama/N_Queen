// java モジュール
import java.util.*;

public class NQueen {

    public static void main(String[] args) {
        //時間の測定
        long start = System.currentTimeMillis();

        //NxNのBoard，N個のQueen，のN
        int size = Integer.parseInt(args[0]);

        //１世代あたりの遺伝子の数
        int geneNum = 10;

        int [][] geneList = new int [geneNum][size];

        for (int i = 0; i < geneNum; i ++){
            //初期集団の生成
            Gene gene = new Gene(geneNum, size);
            gene.getGene();
            geneList[i] = gene.setGene();
        }
       
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
