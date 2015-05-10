function scrape = !"newspaper/scrape.py"
function head = !"head"
function download = !"newspaper/download.py"
function get_names = !"stanford-ner/ner.py"
function count = !"count.py"

"http://www.nytimes.com" -> "source.txt"
scrape("source.txt") -> "urls.csv"
head("urls.csv") -> "urls.csv"
download("urls.csv") -> "text.csv"
get_names("text.csv") -> "names.csv"
count("names.csv") -> "counts.csv"

print @"counts.csv"