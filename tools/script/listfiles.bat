@echo off

forfiles /p %1 /m *.jpg /c "cmd /c echo @fname"

