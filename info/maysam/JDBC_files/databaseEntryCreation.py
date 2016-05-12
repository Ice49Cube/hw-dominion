file = open("wordlist", "r")
reading = file.readlines()
dbfile = open("dbEntries.txt", "w+")
for i in range(len(reading)):
    dbfile.writelines("INSERT INTO words(word, disabled) VALUES(\"%s\", FALSE);\n" % reading[i])
dbfile.close()