function scrape = !"newspaper/scrape.py"
function download = !"newspaper/download.py"
function tokenize = !"tokenize.py"
function count = !"count.py"

text source = $1
table urls = scrape(source)
table articles = download(&urls)
table words = tokenize(&articles)
table counts = count(words)

print counts
