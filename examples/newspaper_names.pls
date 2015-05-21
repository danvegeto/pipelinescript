function scrape 	= !"newspaper/scrape.py"
function head 		= !"head -n 5"
function download 	= !"newspaper/download.py"
function get_names 	= !"stanford-ner/ner.py"
function count 		= !"count.py"

"http://www.nytimes.com" 	-> "source.txt"
scrape("source.txt") 		-> "urls.csv"
head("urls.csv") 			-< "url#.txt"
&download("url#.txt") 		=> "text#.txt"
&get_names("text#.txt") 	=> "names#.txt"
count("names#.txt") 		-> "counts.csv"

print head("counts.csv")