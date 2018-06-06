#include <stdio.h>
#include <stdlib.h>

/* 構造体宣言 */
struct gene_struct{
    int *gene;
    int fitness;
};

/* 関数プロトタイプ宣言 */
void board_print(int size);
void calcFitness(int size);
void makeIniGene(struct gene_struct *genes, int gene_num, int size);
void p_to_o(int size); 
void o_to_p(int size);
void cross(int size);
void mutation(int size);
void gene_sort(int size,int count);


/* NQueen本体 */
int main(int argc, char const* argv[])
{
    if(argc <= 1){
        puts("Use '[FILE NAME] [QUEEN NUM]'.");
        return 1;
    }

    int size, gene_num, n;
    
    // Nの数
    size = strtol(argv[1], NULL, 10);
    
    //1世代あたりの遺伝子数
    gene_num = 10;
    
    //初期集団の生成
    struct gene_struct gene_list[gene_num];
    makeIniGene(gene_list, gene_num, size);
    for(int i=0; i<size; i++){
        printf("%d ", gene_list[0].gene[i]);
    }
    printf("\n");
    //学習ループ
    n = 1;
/*
    while(1){
        
    }
*/
    return 0;
}

/* Boardを出力 */
void board_print(int size) {
    
}

/* 遺伝子生成 */
void makeIniGene(struct gene_struct *genes, int gene_num, int size){
    for(int i=0; i<gene_num; i++){              //遺伝子数ループさせる
        int gene[size];                         //構造体へ保存する配列の宣言
        for(int j=0; j<size; j++){
            gene[j] = j;
        }

        for(int j=0; j<size; j++){              //遺伝子配列のシャッフル
            int n = rand() % size;
            int tmp = gene[n];
            gene[n] = gene[j];
            gene[j] = tmp;
        }

        genes->gene = gene;
        genes++;
        /*
        for(int i=0; i<size; i++){
            printf("%d ", genes->gene[i]);
        }
        printf("\n");
        */
    }
}

/* 評価関数 */
void calcFitness(int size) {
    
}

/* 順列表現と順序表現へ変更 
void p_to_o(gene_struct gene_struct,int size) {
    int converted[size];
    //数値が1からサイズ分まで順番に入っているリストを作成
    int num_list[size];  
    
    //geneの配列数の取得
    int gene_number = sizeof(gene_struct.gene) / sizeof(gene_struct.gene[0]);
    //遺伝子変換
    for (int i = 0; i < gene_number; i++) {
       converted[i] = index(num_list,size);
    }
    return converted; 
}
*/

/* 要素のindexを返す */
int index(int *list,int element) {
    int list_number = sizeof(list) / sizeof(list[0]);        //配列数取得
    for (int i = 0; i < list_number; i++) {                  //要素に対するindexを検索
        if (list[i] == element) {                            //見つけた場合，indexを返す
            return i;      
        }
    }
    return -1;                                               //見つからなかった場合，-1を返す
}

/* 順序表現から順列表現へ変換 */
void o_to_p(int size) {
    
}

/* 交叉*/
/* 遺伝子のリストを渡すと交叉する */
void cross(int size) {
    
}

/* 突然変異 */
void mutation(int size) {
    
}

/* 適応度を基準にソートし，淘汰と増殖を行う */
void gene_sort(int size,int count){
}
