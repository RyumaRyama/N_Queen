#!/usr/bin/env sh

mkdir -p ./CalcTimeCandJava 2>/dev/null

if ls ./CalcTimeCandJava/*.txt > /dev/null 2>&1 ;
    then rm ./CalcTimeCandJava/*.txt
fi

#変数宣言
gene=0;

#コンパイル
clang nqueen.c 
clang -o a.opt -O nqueen.c 
javac CrossGene.java Fitness.java Gene.java NQueen.java

# 10 ~ gene 世代まで測定
for i in `seq 1 30`
do 
    for j in `seq 1 10` # 10回測定した平均
    do  
        gene=`expr $i \* 10`
        
        echo "n="$gene","$j
        (./a.out $gene $RANDOM) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> ./CalcTimeCandJava/c_$gene.txt
        echo "c"
        (./a.opt $gene $RANDOM) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> ./CalcTimeCandJava/c_opt_$gene.txt
        echo "c_opt"
        (java NQueen $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> ./CalcTimeCandJava/java_$gene.txt 
        echo "java"
        echo ""
    done
done

#初期化
gene=0

#平均を求める
for i in `seq 1 30` #世代
do
    gene=`expr $i \* 10`

    /bin/echo -n $gene" " >> ./CalcTimeCandJava/c_ave.txt
    cat ./CalcTimeCandJava/c_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> ./CalcTimeCandJava/c_ave.txt
    
    /bin/echo -n $gene" " >> ./CalcTimeCandJava/c_opt_ave.txt
    cat ./CalcTimeCandJava/c_opt_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> ./CalcTimeCandJava/c_opt_ave.txt
    
    /bin/echo -n $gene" " >> ./CalcTimeCandJava/java_ave.txt
    cat ./CalcTimeCandJava/java_$gene.txt | awk '{x++;sum+=$1}END {print sum/x}' >> ./CalcTimeCandJava/java_ave.txt
done

#グラフ出力
gnuplot nqueen_java_c.plt
