#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/* 構造体宣言 */
typedef struct {
    int* gene;
    int* fitness;
}gene_struct;

typedef struct cell{
    int n;
    struct cell* next;
}list; 

/* 関数プロトタイプ宣言 */
void board_print(int* gene, int size);
void calcFitness(gene_struct* gene_list, int gene_num, int size);
void makeIniGene(gene_struct* gene_list, int gene_num, int size);
void p_to_o(gene_struct* gene_list,int gene_num,int size);
int index_num(int *list,int element,int gene_num,int size);
void o_to_p(int size);
void cross(gene_struct* gene_list,int gene_num, int size);
void mutation(int size);
void gene_sort(gene_struct* gene_list, int gene_num, int size, int count);

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
    calcFitness(gene_list, gene_num, size);
    for(int i=0; i<gene_num; i++){
        for(int j=0; j<size; j++){
            printf("%d ", gene_list[i].gene[j]);
        }
        printf("  : %d \n", *gene_list[i].fitness);
    }
    gene_sort(gene_list, gene_num, size, 0);
    printf("\n");
    for(int i=0; i<gene_num; i++){
        for(int j=0; j<size; j++){
            printf("%d ", gene_list[i].gene[j]);
        }
        printf("  : %d \n", *gene_list[i].fitness);
    }
    
    //学習ループ
    n = 1;
/*
    while(1){
        
    }
*/
    for(int i=0; i<gene_num; i++){       //領域の開放
        free(gene_list[i].gene);
        free(gene_list[i].fitness);
    }
    return 0;
}

/* Boardを出力 */
void board_print(int* gene, int size) {
    for(int i=0; i<size; i++){
        for(int j=0; j<size; j++){
            if(gene[i] == j)
                printf("Q ");
            else
                printf(". ");
        }
        printf("\n");
    }
}

/* 遺伝子生成 */
void makeIniGene(gene_struct* gene_list, int gene_num, int size){
    for(int i=0; i<gene_num; i++){
        gene_list[i].gene = malloc(sizeof(int) * size); //遺伝子分の領域を確保
        gene_list[i].fitness = malloc(sizeof(int));
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
void calcFitness(gene_struct* gene_list, int gene_num, int size) {
    int gVec[4][2] = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    for(int i=0; i<gene_num; i++){
        *gene_list[i].fitness = 0;
        for(int j=0; j<size; j++){
            for(int vec=0; vec<4; vec++){
                for(int k=1; k<size; k++){
                    int x = gVec[vec][1] * k + gene_list[i].gene[j];
                    int y = gVec[vec][0] * k + j;
                    
                    if(x < 0 || x >= size || y < 0 || y >= size)
                        break;
                        
                    if(x == gene_list[i].gene[y])
                        *gene_list[i].fitness += 1;
                }
            }
        }
    }
}

/* 順列表現と順序表現へ変更 */
void p_to_o(gene_struct* gene_list,int gene_num,int size){
    int converted[size];
    
    
    //遺伝子変換
    for (int i = 0; i < gene_num; i++) {
        for (int j = 0; j < size; j++) {
             
        }    
        for (int j = 0; j < size; j++) {
           gene_list[i].gene[j] = index_num(num_list,gene_list[i].gene[j],size);
        }
    }
}

/* 要素のindexを返す */
int index_num(list* num_list,int gene_el,int size) {
    //一番目に要素がある場合
    if (num_list->n == gene_el){ 
        num_list = num_list->next;
        return 0;
    }
    
    //それ以降
    int cnt = 1;
    list* next = num_list->next;
    while (next->next != NULL) {
        if (next->n == gene_el) {                            //見つけた場合，indexを返す
            num_list->next = next->next;
            return cnt;      
        }
        cnt++;    
        num_list = next;
        next = next->next;
    }
    return -1;                                               //見つからなかった場合，-1を返す
}

/* 順序表現から順列表現へ変換 */
void o_to_p(gene_struct* gene_list,int gene_num,int size) {
    int converted[size];    
    int num_list[size];  //数値が1からサイズ分まで順番に入っているリストを作成
    
    //遺伝子変換
    for (int i = 0; i < gene_num; i++) {
        for (int j = 0; j < size; j++) {
           converted[i] = index_num(num_list,i,gene_num,size);
           gene_list[i].gene[j] = converted[j];
        }
    }
}

/* 交叉*/
/* 遺伝子のリストを渡すと交叉する */
void cross(gene_struct* gene_list, int gene_num,  int size) {
    for(int i=0; i<gene_num/2; i++){
        int n = i * 2;
        int num = (rand() % size-3) + 1;
        int tmp[size];

        for(int j=0; j<size; j++){                  //n,n+1の配列を作成
            if(j < num)
                tmp[j] = gene_list[n].gene[j];
            else
                tmp[j] = gene_list[n+1].gene[j];
        }

        for(int j=0; j<size; j++){                  //n+1,nの配列を作成
            if(j >= num)
                gene_list[n+1].gene[j] = gene_list[n].gene[j];
        }

        for(int j=0; j<size; j++)
            gene_list[n].gene[j] = tmp[j];
    }
}

/* 突然変異 */
void mutation(int size) {
}

/* 適応度を基準にソートし，淘汰と増殖を行う */
void gene_sort(gene_struct* gene_list, int gene_num, int size, int count){
    calcFitness(gene_list, gene_num, size);
    
    //適応度をもとにソーティングを行う
    for(int i=1; i<gene_num; i++){
        int n = i;
        while(n > 0){
            if(*gene_list[n].fitness < *gene_list[n-1].fitness){
                gene_struct tmp = gene_list[n];
                gene_list[n] = gene_list[n-1];
                gene_list[n-1] = tmp;
            }
            n--;
        }
    }

    //最も良い適応度を出力
    printf("%d\n", *gene_list[0].fitness);

    //適応度が0ならプログラムを終了させる
    if(*gene_list[0].fitness == 0){
        printf("count : %d\n", count);
        board_print(gene_list[0].gene, size);
        exit(0);
    }

    for(int i=0; i<size; i++){
        gene_list[gene_num-1].gene[i] = gene_list[0].gene[i];
        gene_list[1].gene[i] = gene_list[0].gene[i];
    }
}
