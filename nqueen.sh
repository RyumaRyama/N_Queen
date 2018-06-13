#!/usr/bin/env sh

if ls *.txt > /dev/null 2>&1 ;
    then rm *.txt
fi

#変数宣言
gene=0;

# コンパイル
#clang $1
#clang -o Prime_opt -O $1
javac $1

# 10 ~ gene 世代まで測定
for i in `seq 1 3`
do 
    for j in `seq 1 5` # 5回測定した平均
    do  
        gene=`expr $i \* 10`
        
        #(./a.out $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> c_$gene.txt
        #(./Prime_opt $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> c_opt_$gene.txt
        (java NQueen $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> java_$gene.txt 
        #(python3 $gene) | grep "Time" | egrep -o '[0-9]+[\.]+[0-9]+[0-9]'>> py_$gene.txt
        
    done
done
