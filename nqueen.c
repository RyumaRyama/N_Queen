#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/* 構造体宣言 */
typedef struct {
    int* gene;
    int fitness;
}gene_struct;

/* 関数プロトタイプ宣言 */
void board_print(int size);
void calcFitness(int size);
void makeIniGene(gene_struct* gene_list, int gene_num, int size);
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

    srand(time(NULL));

    int size, gene_num, n;
    
    // Nの数
    size = strtol(argv[1], NULL, 10);
    
    //1世代あたりの遺伝子数
    gene_num = 10;
    
    //初期集団の生成
    gene_struct gene_list[gene_num];
    makeIniGene(gene_list, gene_num, size);
    for(int i=0; i<gene_num; i++){
        for(int j=0; j<size; j++){
            printf("%d ", gene_list[i].gene[j]);
        }
        printf("\n");
    }
    //学習ループ
    n = 1;
/*
    while(1){
        
    }
*/
    for(int i=0; i<gene_num; i++)       //領域の開放
        free(gene_list[i].gene);
    return 0;
}

/* Boardを出力 */
void board_print(int size) {
    
}

/* 遺伝子生成 */
void makeIniGene(gene_struct* gene_list, int gene_num, int size){
    for(int i=0; i<gene_num; i++){
        gene_list[i].gene = malloc(sizeof(int) * size); //遺伝子分の領域を確保
        for(int j=0; j<size; j++){
            gene_list[i].gene[j] = j;
        }

        for(int j=0; j<size; j++){              //遺伝子配列のシャッフル
                int n = rand() % size;
                int tmp = gene_list[i].gene[n];
                gene_list[i].gene[n] = gene_list[i].gene[j];
                gene_list[i].gene[j] = tmp;
        }
    }
}


/* 評価関数 */
void calcFitness(int size) {
    
}

/* 順列表現と順序表現へ変更 
void p_to_o(int* gene_list,int size) {
    int converted[size];
    //数値が1からサイズ分まで順番に入っているリストを作成
    int num_list[size];  
    
    //gene_listの配列数の取得
    int gene_number = sizeof(gene_list) / sizeof(gene_list[0]);
    //遺伝子変換
    for (int i = 0; i < gene_number; i++) {
       converted[i] = index_num(num_list,size);
    }
}
*/

/* 要素のindexを返す 
int index_num(int *list,int element) {
    int list_number = sizeof(list) / sizeof(list[0]);        //配列数取得
    for (int i = 0; i < gene_num; i++) {
        for (int j = 0; j < list_number; j++) {                  //要素に対するindexを検索
            if (list[i*list_number + j] == element) {                            //見つけた場合，indexを返す
                return i*list_number + j;      
            }
        }
    return -1;                                               //見つからなかった場合，-1を返す
    }
}
*/

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
