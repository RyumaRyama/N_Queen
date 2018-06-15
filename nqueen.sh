#!/usr/bin/env sh

#if ls *.txt > /dev/null 2>&1 ;
#    then rm *.txt
#fi

#変数宣言
gene=0;

#コンパイル
#clang nqueen.c 
#clang -o a.opt -O nqueen.c 
#javac CrossGene.java Fitness.java Gene.java NQueen.java

# 10 ~ gene 世代まで測定
for i in `seq 1 10`
do 
    for j in `seq 1 10` # 10回測定した平均
    do  
        gene=`expr $i \* 10`
        
<< COMMENTOUT 
        echo "n="$gene
        (./a.out $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> c_$gene.txt
        echo "c"
        (./a.opt $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> c_opt_$gene.txt
        echo "c_opt"
        (java NQueen $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> java_$gene.txt 
        echo "java"
COMMENTOUT
        
        (python2 nqueen_py2.py $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> py2_$gene.txt
        echo "python2"
        (python3 nqueen.py $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> py3_$gene.txt
        echo "python3"
        
    done
done

#初期化
gene=0

#平均を求める
for i in `seq 1 10` #世代
do
    gene=`expr $i \* 10`
   
<< COMMENTOUT 
    /bin/echo -n $gene" " >> c_ave.txt
    cat c_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> c_ave.txt
    
    /bin/echo -n $gene" " >> c_opt_ave.txt
    cat c_opt_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> c_opt_ave.txt
    
    /bin/echo -n $gene" " >> java_ave.txt
    cat java_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> java_ave.txt
COMMENTOUT
    
    /bin/echo -n $gene" " >> py2_ave.txt
    cat py2_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> py2_ave.txt
    
    /bin/echo -n $gene" " >> py3_ave.txt
    cat py3_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> py3_ave.txt
done

#グラフ出力
gnuplot nqueen.plt
