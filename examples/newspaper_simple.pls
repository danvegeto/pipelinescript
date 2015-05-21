function head 		= !"head"
function scrape 	= !"newspaper/scrape.py"
function download 	= !"newspaper/download.py"

"http://www.nytimes.com" 	-> "source.txt"
scrape("source.txt") 		-> "urls.csv"
head("urls.csv") 			-< "url#.txt"
&download("url#.txt") 		=> "text#.txt"

print head("text#.txt")