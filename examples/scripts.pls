function words = !"tokenize.py"
function counts = !"count.py"

"the boy and the girl played with the dog and the cat" -> "text.txt"

words("text.txt") -> "words.txt"
counts("words.txt") -> "counts.csv"

print @"counts.csv"