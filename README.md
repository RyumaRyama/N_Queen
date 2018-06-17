# GAで解くNQueen問題
簡単な説明，結果を[nqueen.pdf](https://github.com/RyumaRyama/N_Queen/blob/develop/nqueen.pdf)にまとめた

## GA(遺伝的アルゴリズム)とは
>遺伝的アルゴリズムはデータ（解の候補）を遺伝子で表現した「個体」を複数用意し、適応度の高い個体を優先的に選択して交叉（組み換え）・突然変異などの操作を繰り返しながら解を探索する。適応度は適応度関数によって与えられる。
この手法の利点は、評価関数の可微分性や単峰性などの知識がない場合であっても適用可能なことである。

引用wiki　https://ja.wikipedia.org/wiki/遺伝的アルゴリズム

## NQueen問題とは

N*NのマスにN個のクイーンを置くときにお互いに取ることができない配置を考える問題。Nが増えると解が爆発的に増加してしまう

## 目的

GAを用いてNQuenn問題を解いてみる

ついでに言語別で実行速度に違いが出るか測定してみちゃう


## 実行環境

- MacBook HighSierra

  - MacBook Pro (Retina, 13-inch, Early 2015)

  - プロセッサ　2.7GHz Intel Core i5

  - メモリ　8GB 1867MHz DDR3

- python2
- python3
- clang-902
- Java9
- gnuplot ver.5.2


## コーディング案

1. 遺伝子型の決定

　　一次元配列で行列を表現

　　例:　　[1, 3, 0, 2]

2. 初期遺伝子集団生成

　　ランダム生成 (配列内に同じ数値を入れない)

　　以下，3 ~ 6 を繰り返す

3. 適応度を評価

　　適応度をぶつかるクイーンの数で測定し，0になる(コマ同士がぶつからない)と終了

4. 遺伝子の淘汰と増殖

　　優秀なもので優秀でないものを置き換える

5. 交叉

　　順序表現の遺伝子同士を一点交叉　(配列内に同じ値ができる可能性を防ぐ)

　　例:
  
```
　　[3, 1, 2, 0] -> [3, 1, | 1, 0 |] -> [3, 1, 0, 0]
　　[2, 1, 0, 3] -> [2, 1, | 0, 0 |] -> [2, 1, 1, 0]
```

6. 突然変異

　　ランダムで1つの遺伝子を選び，要素を入れ替える

## 実行方法

引数にNの値を設定

### python2

```
python2 nqueen_py2.py N
```

### python3

```
python3 nqueen.py N
```

### C

```
clang nqueen.c
./a.out N
```

### C (最適化)

```
clang -o a.opt -O nqueen.c
./a.opt N
```

### Java

```
javac NQueen.java Gene.java Fitness.java CrossGene.java
java NQueen N
```

## 言語別処理速度測定

nqueen.shを実行すると，N=100までのそれぞれの言語の処理速度がグラフ化される

```
sh nqueen.sh
```

nqueen_java_c.shを実行すると，N=300までのCとC(最適化),Javaの処理速度がグラフ化される

```
sh nqueen_java_c.sh
```

*※いぞれもかなり計測時間がかかるので注意。当実行環境では10時間以上かかった*


##  実行結果

Nの数を10~100の10刻みで10回ずつ測定し，平均処理速度の移り変わりをsvgファイルに示した

- output.svg
  - 全ての実行環境
  
  ![output.svg](https://github.com/RyumaRyama/N_Queen/blob/develop/output.svg)
  
- output_java_c.svg
  - java, c, c(最適化)
  
  ![output_java_c.svg](https://github.com/RyumaRyama/N_Queen/blob/develop/output_java_c.svg)

- output_py.svg
  - python2, python3

  ![output_py.svg](https://github.com/RyumaRyama/N_Queen/blob/develop/output_py.svg)
  
___

Nの数を10~300の10刻みで10回ずつ測定し，平均処理速度の移り変わりをsvgファイルに示した

- output_java_c_300.svg
  - java, c, c(最適化)
  
  ![output_java_c_300.svg](https://github.com/RyumaRyama/N_Queen/blob/develop/output_java_c_300.svg)

## 実行結果からわかったこと
- Cの最適化とJavaが最も高速
- 最適化での差は大きい
- Nの増加量に対して，大きな速度変化はなし(おそらくn log n)
- Python系は比較的低速
  - 2系と3系に大きな速度変化はなし
- 膨大なデータ量なら高速なC・Javaを，小規模ならコード量の少ないPython系を用いたい

## 感想
Cとじゃゔぁしゅごぃぃぃぃぃぃ(一同)

## 参考サイト
- https://www.slideshare.net/akihitonagai96/n-73283027
- https://qiita.com/kanlkan/items/f1110b9546de567f7075



