function head = !"head"
function download = !"newspaper/download.py"

head("urls.csv") -< "url#.txt"

&download("url#.txt") -> "text#.txt"