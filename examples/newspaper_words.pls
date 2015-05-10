function scrape = !"newspaper/scrape.py"
function head = !"head"
function download = !"newspaper/download.py"
function tokenize = !"tokenize.py"
function count = !"count.py"

"http://www.nytimes.com" -> "source.txt"
scrape("source.txt") -> "urls.csv"
head("urls.csv") -> "urls.csv"
download("urls.csv") -> "text.csv"
tokenize("text.csv") -> "words.csv"
count("words.csv") -> "counts.csv"

print @"counts.csv"