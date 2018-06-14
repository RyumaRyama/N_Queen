'''
argv[1] : Board size
'''

import random
import sys
from operator import itemgetter
import time

start = 0

def board_print(gene, size):
    for i in gene:
        for j in range(size):
            if i == j:
                print 'Q ',
            else:
                print '. ',
        print ''

def makeIniGene(gene_num, size):
    ini_gene = []
    for cnt in range(0, gene_num):
        line = []
        for i in range(0, size):
            line.append(i)

        random.shuffle(line)
        ini_gene.append(line)

    return ini_gene

def calcFitness(gene, size):
    fitness = 0
    gVec = [(-1,-1), (-1,1), (1,-1), (1,1)]

    for i in range(size):
        for vec in gVec:
            for j in range(1, size):
                x = vec[1] * j + gene[i]
                y = vec[0] * j + i
                if x < 0 or x >= size or y < 0 or y >= size:
                    break
                
                if x == gene[y]:
                    fitness += 1
    
    return fitness

def p_to_o(gene, size):
    converted = []
    num_list = []

    for num in range(size):
        num_list.append(num)
    
    for num in gene:
        converted.append(num_list.index(num))
        num_list.remove(num)

    return converted

def o_to_p(gene, size):
    converted = []
    num_list = []
    
    for num in range(size):
        num_list.append(num)

    for num in gene:
        converted.append(num_list.pop(num))

    return converted

def cross (ini_gene, size):
    for i in range(int(len(ini_gene)/2)):
        n = i * 2
        num = random.randint(1,size-2)

        tmp = ini_gene[n][0:num]
        tmp.extend(ini_gene[n+1][num:])

        ini_gene[n+1] = ini_gene[n+1][0:num]
        ini_gene[n+1].extend(ini_gene[n][num:])
        
        ini_gene[n] = tmp

def mutation(gene_list, size):
    gene = random.randint(0,len(gene_list)-1)
    num1 = random.randint(0, size-1)
    num2 = random.randint(0, size-1)

    tmp = gene_list[gene][num1]
    gene_list[gene][num1] = gene_list[gene][num2]
    gene_list[gene][num2] = tmp

def gene_sort(gene_list, size, count):
    fit_gene = []
    for gene in gene_list:
        add_gene = []
        add_gene.append(gene[:])
        add_gene.append(calcFitness(gene, size))
        fit_gene.append(add_gene)

    fit_gene.sort(key=itemgetter(1))

    print fit_gene[0][1]
    '''
    for i in range(len(gene_list)):
        print(fit_gene[i][0])
    print("")
    '''
    
    if fit_gene[0][1] == 0:
        print "count {}".format(count)
        board_print(fit_gene[0][0], size)
        end = time.time()
        print "Time:{}[sec]".format(end - start)
        sys.exit(0)

    gene_list = []
    for gene in fit_gene:
        gene_list.append(gene.pop(0))

    gene_list[-1] = gene_list[0]
    gene_list[1] = gene_list[0]

    return gene_list

def main(argv):
    size = int(argv[1])

    gene_num = 10

    gene_list = makeIniGene(gene_num, size)

    n = 1
    while True:

        gene_list = gene_sort(gene_list, size, n)

        cross_gene = []
        for gene in gene_list:
            cross_gene.append(p_to_o(gene, size))

        cross(cross_gene, size)

        gene_list = []
        for gene in cross_gene:
            gene_list.append(o_to_p(gene, size))

        mutation(gene_list, size)

        n += 1

if __name__ == "__main__":
    start = time.time()
    main(sys.argv)
