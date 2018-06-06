#include <stdio.h>
struct gene_struct{
    int *gene;
    int fitness;
};

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
    makeIniGene(gene_list, size);

/*
    //学習ループ
    n = 1;
    while(1){
        
    }
*/
    return 0;
}

/*
// Boardを出力 
void board_print() {
    if (condition) {
        
    }else
}

// 遺伝子生成
void {
    
}

// 評価関数
void calcFitness(int gene,int size) {
    
}

// 順列表現と順序表現へ変更
void p_to_o(int gene,int size) {
      
}

// 順序表現から順列表現へ変換
void o_to_p(int gene,int size) {
    
}

// 交叉
// 遺伝子のリストを渡すと交叉する
void cross(int_gene,) {
    
}
*/
