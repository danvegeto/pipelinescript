function scrape 	= !"newspaper/scrape.py"
function head 		= !"head"
function download 	= !"newspaper/download.py"
function tokenize 	= !"tokenize.py"
function count 		= !"count.py"

"http://www.nytimes.com" 	-> "source.txt"
scrape("source.txt") 		-> "urls.csv"
head("urls.csv") 			-< "url#.txt"
&download("url#.txt") 		=> "text#.txt"
&tokenize("text#.txt") 		=> "words#.txt"
count("words#.txt") 		-> "counts.csv"

print head("counts.csv")