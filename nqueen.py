'''
argv[1] : Board size
'''

import random
import sys
from operator import itemgetter
import time

start = 0

#Boardを出力
def board_print(gene, size):
    for i in gene:
        for j in range(size):
            if i == j:
                print("Q", end=" ")
            else:
                print(".", end=" ")
        print("")


#遺伝子生成
def makeIniGene(gene_num, size):
    ini_gene = []
    for cnt in range(0, gene_num):
        line = []
        for i in range(0, size):
            line.append(i)

        random.shuffle(line)
        ini_gene.append(line)

    return ini_gene


#評価関数
def calcFitness(gene, size):
    fitness = 0
    board = []
    line = []
    for i in range(0, size):
        line = []
        for j in range(0, size):
            if j == gene[i]:
                line.append(1)
            else:
                line.append(0)
        board.append(line)
    

    gVec = [(-1,-1), (-1,0), (-1,1), (0,-1), (0,1), (1,-1), (1,0), (1,1)]
    for i in range(0, size):
        for j in range(0, size):
            val = getCell(board, (i,j), (0,0), size)
            if val == 1:
                for vec in gVec:
                    for k in range(1, size):
                        valofst = getCell(board, (i,j), (vec[0]*k, vec[1]*k), size)
                        if valofst == 1:
                            fitness += 1
                        elif valofst == -1:
                            break

    return fitness


def getCell(board, pos, ofst, size):
    posx = pos[0] + ofst[0]
    posy = pos[1] + ofst[1]
    if posx >= 0 and posy >= 0 and posx < size and posy < size:
        val = board[posx][posy]
    else:
        val = -1

    return val


#順列表現と順序表現へ変換
def p_to_o(gene, size):
    converted = []
    num_list = []
    #数値が1からサイズ分まで順番に入っているリストを作成
    for num in range(size):
        num_list.append(num)
    
    #遺伝子変換
    for num in gene:
        converted.append(num_list.index(num))
        num_list.remove(num)

    return converted


#順序表現から順列表現へ変換
def o_to_p(gene, size):
    converted = []
    num_list = []
    
    for num in range(size):
        num_list.append(num)

    for num in gene:
        converted.append(num_list.pop(num))

    return converted


#交叉
#遺伝子のリストを渡すと交叉する
def cross (ini_gene, size):
    for i in range(int(len(ini_gene)/2)):
        n = i * 2
        num = random.randint(1,size-2)

        tmp = ini_gene[n][0:num]
        tmp.extend(ini_gene[n+1][num:])

        ini_gene[n+1] = ini_gene[n+1][0:num]
        ini_gene[n+1].extend(ini_gene[n][num:])
        
        ini_gene[n] = tmp


#突然変異
def mutation(gene_list, size):
    gene = random.randint(0,len(gene_list)-1)
    num1 = random.randint(0, size-1)
    num2 = random.randint(0, size-1)

    tmp = gene_list[gene][num1]
    gene_list[gene][num1] = gene_list[gene][num2]
    gene_list[gene][num2] = tmp


#適応度を基準にソートし，淘汰と増殖を行う
def gene_sort(gene_list, size, count):
    fit_gene = []
    for gene in gene_list:
        add_gene = []
        add_gene.append(gene[:])
        add_gene.append(calcFitness(gene, size))
        fit_gene.append(add_gene)

    fit_gene.sort(key=itemgetter(1))

    print(fit_gene[0][1])
    '''
    for i in range(len(gene_list)):
        print(fit_gene[i][0])
    print("")
    '''
    #board_print(fit_gene[0][0], size)
    
    #適応度が0ならプログラム終了
    if fit_gene[0][1] == 0:
        print("count {}".format(count))
        board_print(fit_gene[0][0], size)
        end = time.time()
        print("Time:{}[sec]".format(end - start))
        sys.exit(0)

    gene_list = []
    for gene in fit_gene:
        gene_list.append(gene.pop(0))

    #淘汰は一旦1つだけ行う
    gene_list[-1] = gene_list[0]

    return gene_list


#NQueen本体
def main(argv):
    #NxNのBoard，N個のQueen，のN
    size = int(argv[1])

    #１世代あたりの遺伝子の数
    gene_num = 10

    #初期集団の生成
    gene_list = makeIniGene(gene_num, size)

    #ループに入る
    n = 1
    while True:
        #遺伝子が評価され，淘汰され，増殖する
        gene_list = gene_sort(gene_list, size, n)

        #交叉する
        cross_gene = []
        for gene in gene_list:
            cross_gene.append(p_to_o(gene, size))

        cross(cross_gene, size)

        gene_list = []
        for gene in cross_gene:
            gene_list.append(o_to_p(gene, size))

        #突然変異する
        mutation(gene_list, size)

        n += 1


if __name__ == "__main__":
    start = time.time()
    main(sys.argv)

