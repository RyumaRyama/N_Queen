#include <stdio.h>

/* 関数プロトタイプ宣言 */
void board_print(int size);
void calcFitness(int size);
void makeIniGene(int gene_num, int size);
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

    /* 構造体宣言 */
    struct gene_struct{
        int *gene;
        int fitness;
    };
    
    int size, gene_num, n;
    
    // Nの数
    size = strtol(argv[1], NULL, 10);
    
    //1世代あたりの遺伝子数
    gene_num = 10;
    
    //初期集団の生成
    struct gene_struct gene_list[gene_num];
    makeIniGene(gene_list, size);
    
/*
    //学習ループ
    n = 1;
    while(1){
        
    }
*/
    return 0;
}

/* Boardを出力 */
void board_print(int size) {
}

/* 遺伝子生成 */
void makeIniGene(int gene_num, int size){
    
}

/* 評価関数 */
void calcFitness(int size) {
    
}

/* 順列表現と順序表現へ変更 */
void p_to_o(int size) {
      
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
void gene_sort(int size,int count) {
    
}

