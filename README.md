# csvunifier

CSVUnifier is simply a Java package that "unifies" CSV files. It has been a quick and dirty solution to support a research involving visualization of football data.

We are not just talking about "merging" two or more CSV files having the same columns. When using CSVUnifier, the CSV files that needs to be unified do not have to have the same exact columns with the same order. The csvunifier package handles misalignment of the same columns belonging to distinct CSV files.

The CSV files can be located anywhere in a given directory hierarchy. In other words, given a path to a directory, all the files residing there will be treated as CSV files. (In the next version, CSV files will be automatically identified.)

For example, let a.csv and b.csv two files to be merged.

a.csv--------
Col1,Col2,Col3
a,b,c
d,e,f
g,h,i

b.csv--------
Col2,Col1,Col4
j,k,l
m,n,o
p,q,r

uni.csv------
Col1,Col2,Col3,Col4
a,b,c,,
d,e,f,,
g,h,i,,
k,j,,l
n,m,,o
q,p,,r

Usage:
