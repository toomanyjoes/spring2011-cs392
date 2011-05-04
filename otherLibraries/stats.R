summary <- function(data.file) {
    outfileName = "summary.txt"
    data <- unlist(read.table(data.file))
    write(data.file, outfileName, append=TRUE)
    write("mean: ", outfileName, append=TRUE)
    write(mean(data), outfileName, append=TRUE)
    write("stdev: ", outfileName, append=TRUE)
    write(sd(data), outfileName, append=TRUE)
    write("", outfileName, append=TRUE)
}

read.data <- function(filename) {
    return(unlist(read.table(filename)))
}

# returns TRUE if you can reject null hypothesis that mean1 <= mean2 at alpha, FALSE otherwise
ttest <- function(data.file1, data.file2, alpha) {
    data1 <- read.data(data.file1)
    data2 <- read.data(data.file2)
    t.test(data1, data2)
}

gen.graphs <- function() {
    png("results/plots/mstPrim.TestV500E45K.bench.png")
    jdsl.jung.yfiles("mstPrim", "TestV500E45K.bench", "MST Prim\n500 Vertices, 44850 Edges\nOriginal Benchmark")
    dev.off()
    png("results/plots/connected.TestV500E45K.bench.png")
    jgrapht.yfiles("connected", "TestV500E45K.bench", "Connected\n500 Vertices, 44850 Edges\nOriginal Benchmark")
    dev.off()
    png("results/plots/cycle.TestV500E45K.bench.png")
    jdsl.jgrapht.yfiles("cycle", "TestV500E45K.bench", "Cycle\n500 Vertices, 44850 Edges\nOriginal Benchmark")
    dev.off()
    png("results/plots/mstKruskal.TestV500E45K.bench.png")
    jgrapht.yfiles("mstKruskal", "TestV500E45K.bench", "MST Kruskal\n500 Vertices, 44850 Edges\nOriginal Benchmark")
    dev.off()
    png("results/plots/strongC.TestV500E45K.bench.png")
    jgrapht.yfiles("strongC", "TestV500E45K.bench", "Strongly Connected\n500 Vertices, 44850 Edges\nOriginal Benchmark")
    dev.off()
    
    png("results/plots/mstPrim.randommultigraph.txt.png")
    jdsl.jung.yfiles("mstPrim", "randomGraphs.randommultigraph.txt", "MST Prim\n2000 Vertices, 214440 Edges\nRandom Multigraph")
    dev.off()
    png("results/plots/connected.randommultigraph.txt.png")
    jgrapht.yfiles("connected", "randomGraphs.randommultigraph.txt", "Connected\n2000 Vertices, 214440 Edges\nRandom Multigraph")
    dev.off()
    png("results/plots/cycle.randommultigraph.txt.png")
    jdsl.jgrapht.yfiles("cycle", "randomGraphs.randommultigraph.txt", "Cycle\n2000 Vertices, 214440 Edges\nRandom Multigraph")
    dev.off()
    png("results/plots/mstKruskal.randommultigraph.txt.png")
    jgrapht.yfiles("mstKruskal", "randomGraphs.randommultigraph.txt", "MST Kruskal\n2000 Vertices, 214440 Edges\nRandom Multigraph")
    dev.off()
    png("results/plots/strongC.randommultigraph.txt.png")
    jgrapht.yfiles("strongC", "randomGraphs.randommultigraph.txt", "Strongly Connected\n2000 Vertices, 214440 Edges\nRandom Multigraph")
    dev.off()
    
    png("results/plots/mstPrim.randompseudograph.txt.png")
    jdsl.jung.yfiles("mstPrim", "randomGraphs.randompseudograph.txt", "MST Prim\n2000 Vertices, 216743 Edges\nRandom Pseudograph")
    dev.off()
    png("results/plots/connected.randompseudograph.txt.png")
    jgrapht.yfiles("connected", "randomGraphs.randompseudograph.txt", "Connected\n2000 Vertices, 216743 Edges\nRandom Pseudograph")
    dev.off()
    png("results/plots/cycle.randompseudograph.txt.png")
    jdsl.jgrapht.yfiles("cycle", "randomGraphs.randompseudograph.txt", "Cycle\n2000 Vertices, 216743 Edges\nRandom Pseudograph")
    dev.off()
    png("results/plots/mstKruskal.randompseudograph.txt.png")
    jgrapht.yfiles("mstKruskal", "randomGraphs.randompseudograph.txt", "MST Kruskal\n2000 Vertices, 216743 Edges\nRandom Pseudograph")
    dev.off()
    png("results/plots/strongC.randompseudograph.txt.png")
    jgrapht.yfiles("strongC", "randomGraphs.randompseudograph.txt", "Strongly Connected\n2000 Vertices, 216743 Edges\nRandom Pseudograph")
    dev.off()
    
    png("results/plots/mstPrim.randomsimplegraph.txt.png")
    jdsl.jung.yfiles("mstPrim", "randomGraphs.randomsimplegraph.txt", "MST Prim\n2000 Vertices, 201486 Edges\nRandom Simple Graph")
    dev.off()
    png("results/plots/connected.randomsimplegraph.txt.png")
    jgrapht.yfiles("connected", "randomGraphs.randomsimplegraph.txt", "Connected\n2000 Vertices, 201486 Edges\nRandom Simple Graph")
    dev.off()
    png("results/plots/cycle.randomsimplegraph.txt.png")
    jdsl.jgrapht.yfiles("cycle", "randomGraphs.randomsimplegraph.txt", "Cycle\n2000 Vertices, 201486 Edges\nRandom Simple Graph")
    dev.off()
    png("results/plots/mstKruskal.randomsimplegraph.txt.png")
    jgrapht.yfiles("mstKruskal", "randomGraphs.randomsimplegraph.txt", "MST Kruskal\n2000 Vertices, 201486 Edges\nRandom Simple Graph")
    dev.off()
    png("results/plots/strongC.randomsimplegraph.txt.png")
    jgrapht.yfiles("strongC", "randomGraphs.randomsimplegraph.txt", "Strongly Connected\n2000 Vertices, 201486 Edges\nRandom Simple Graph")
    dev.off()
    
    png("results/plots/mstPrim.simplelineargraph.txt.png")
    jdsl.jung.yfiles("mstPrim", "randomGraphs.simplelineargraph.txt", "MST Prim\n4400 Vertices, 4399 Edges\nLinear Graph")
    dev.off()
    png("results/plots/connected.simplelineargraph.txt.png")
    jgrapht.yfiles("connected", "randomGraphs.simplelineargraph.txt", "Connected\n4400 Vertices, 4399 Edges\nLinear Graph")
    dev.off()
    png("results/plots/cycle.simplelineargraph.txt.png")
    jgrapht.yfiles("cycle", "randomGraphs.simplelineargraph.txt", "Cycle\n4400 Vertices, 4399 Edges\nLinear Graph")
    dev.off()
    png("results/plots/mstKruskal.simplelineargraph.txt.png")
    jgrapht.yfiles("mstKruskal", "randomGraphs.simplelineargraph.txt", "MST Kruskal\n4400 Vertices, 4399 Edges\nLinear Graph")
    dev.off()
    #png("results/plots/strongC.simplelineargraph.txt.png")
    #jgrapht.yfiles("strongC", "randomGraphs.simplelineargraph.txt", "Strongly Connected\n5000 Vertices, 4999 Edges\nLinear Graph")
    #dev.off()
    dev.off()
    dev.off()
    dev.off()
    dev.off()
    dev.off()
    dev.off()
}

cap.leading <- function(str) {
   ff <- function(x) {r <- x; r[1]<-cap(x[1]); r}
   capply(str,ff)
}

capply <- function(str, ff) {
   sapply(lapply(strsplit(str, NULL), ff), paste, collapse="")
}

cap <- function(char) {
   # change lower letters to upper, others leave unchanged    
   if (any(ind <- letters==char)) LETTERS[ind]    
   else char
}

error.bar <- function(x, y, upper, lower=upper, length=0.1,...){
    if(length(x) != length(y) | length(y) !=length(lower) | length(lower) != length(upper))
        stop("vectors must be same length")
    arrows(x,y+upper, x, y-lower, angle=90, code=3, length=length, ...)
}

jdsl.jung.yfiles <- function(algo, graph, plot.main) {
    rows <- 6
    color <- c("cyan4", "coral3", "chocolate", "violet", "skyblue1", "wheat4")
    name.vec <- c("AL","NL","EL", "yfiles", "jdsl", "jung")
    al <- read.data(paste("results/gplResults/", algo, "AL.", graph, ".txt", sep=""))
    nl <- read.data(paste("results/gplResults/", algo, "NL.", graph, ".txt", sep=""))
    el <- read.data(paste("results/gplResults/", algo, "EL.", graph, ".txt", sep=""))
    jdsl <- read.data(paste("results/jdslResults/jdslTests.jdsl", algo, "Test.", graph, ".txt", sep=""))
    jung <- read.data(paste("results/jungResults/jungTests.", algo, "Test.", graph, ".txt", sep=""))
    yfiles <- read.data(paste("results/yfilesResults/yfilesTests.yfiles", algo, "Test.", graph, ".txt", sep=""))
    
    m <- rbind(al, nl, el, yfiles, jdsl)
    m.means <- apply(m,1,mean)
    m.means <- c(m.means, mean(jung))
    m.sd <- apply(m,1,sd)
    m.sd <- c(m.sd, sd(jung))
    yy <- matrix(m.means,rows,1,byrow=TRUE)
    #ee <- matrix(m.sd,rows,1,byrow=TRUE)*1.965/sqrt(500)
    ee <- matrix(m.sd,rows,1,byrow=TRUE)
    for(x in c(1:(rows-1))) {
        ee[x,1] <- ee[x,1]*1.965/sqrt(500)
    }
    ee[rows,1] <- ee[rows,1]*1.965/sqrt(10)
    barx <- barplot(yy, beside=TRUE,col=color, ylim=c(0,max(m.means[1:rows-1])+10), names.arg=name.vec, main=plot.main, xlab="Implementation", ylab="Time (milliseconds)")
    error.bar(barx,yy,ee)
}

jgrapht.yfiles <- function(algo, graph, plot.main) {
    rows <- 5
    color <- c("cyan4", "coral3", "chocolate", "violet", "skyblue1", "wheat4")
    name.vec <- c("AL","NL","EL", "yfiles", "jgrapht")
    if(length(grep("connected",algo))) {
        al <- read.data(paste("results/gplResults/", algo, "DFSAL.", graph, ".txt", sep=""))
        nl <- read.data(paste("results/gplResults/", algo, "DFSNL.", graph, ".txt", sep=""))
        el <- read.data(paste("results/gplResults/", algo, "DFSEL.", graph, ".txt", sep=""))
    } else {
        al <- read.data(paste("results/gplResults/", algo, "AL.", graph, ".txt", sep=""))
        nl <- read.data(paste("results/gplResults/", algo, "NL.", graph, ".txt", sep=""))
        el <- read.data(paste("results/gplResults/", algo, "EL.", graph, ".txt", sep=""))
    }
#    jdsl <- read.data(paste("results/jdslResults/jdslTests.jdsl", algo, "Test.", graph, ".txt", sep=""))
#    jung <- read.data(paste("results/jungResults/jungTests.", algo, "Test.", graph, ".txt", sep=""))
    yfiles <- read.data(paste("results/yfilesResults/yfilesTests.yfiles", cap.leading(algo), "Test.", graph, ".txt", sep=""))
    jgrapht <- read.data(paste("results/jgraphtResults/jgraphtTests.jgrapht", algo, "Test.", graph, ".txt", sep=""))
    
    m <- rbind(al, nl, el, yfiles, jgrapht)
    m.means <- apply(m,1,mean)
    m.sd <- apply(m,1,sd)
    yy <- matrix(m.means,rows,1,byrow=TRUE)
    #ee <- matrix(m.sd,rows,1,byrow=TRUE)*1.965/sqrt(500)
    ee <- matrix(m.sd,rows,1,byrow=TRUE)
    for(x in c(1:(rows))) {
        ee[x,1] <- ee[x,1]*1.965/sqrt(500)
    }
    barx <- barplot(yy, beside=TRUE,col=color, ylim=c(0,max(m.means)+10), names.arg=name.vec, main=plot.main, xlab="Implementation", ylab="Time (milliseconds)")
    error.bar(barx,yy,ee)
}

jdsl.jgrapht.yfiles <- function(algo, graph, plot.main) {
    rows <- 6
    color <- c("cyan4", "coral3", "chocolate", "violet", "skyblue1", "wheat4")
    name.vec <- c("AL","NL","EL", "yfiles", "jgrapht", "jdsl")
    al <- read.data(paste("results/gplResults/", algo, "AL.", graph, ".txt", sep=""))
    nl <- read.data(paste("results/gplResults/", algo, "NL.", graph, ".txt", sep=""))
    el <- read.data(paste("results/gplResults/", algo, "EL.", graph, ".txt", sep=""))
    jdsl <- read.data(paste("results/jdslResults/jdslTests.jdsl", algo, "Test.", graph, ".txt", sep=""))
#    jung <- read.data(paste("results/jungResults/jungTests.", algo, "Test.", graph, ".txt", sep=""))
    yfiles <- read.data(paste("results/yfilesResults/yfilesTests.yfiles", algo, "Test.", graph, ".txt", sep=""))
    jgrapht <- read.data(paste("results/jgraphtResults/jgraphtTests.jgrapht", algo, "Test.", graph, ".txt", sep=""))
    
    m <- rbind(al, nl, el, yfiles, jgrapht, jdsl)
    m.means <- apply(m,1,mean)
    m.sd <- apply(m,1,sd)
    yy <- matrix(m.means,rows,1,byrow=TRUE)
    #ee <- matrix(m.sd,rows,1,byrow=TRUE)*1.965/sqrt(500)
    ee <- matrix(m.sd,rows,1,byrow=TRUE)
    for(x in c(1:(rows))) {
        ee[x,1] <- ee[x,1]*1.965/sqrt(500)
    }
    barx <- barplot(yy, beside=TRUE,col=color, ylim=c(0,max(m.means)+10), names.arg=name.vec, main=plot.main, xlab="Implementation", ylab="Time (milliseconds)")
    error.bar(barx,yy,ee)
}

