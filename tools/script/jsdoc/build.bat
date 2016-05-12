@echo off

REM remove help in this folder (if any)
if exist "%CD%\help" rmdir /s /q "%CD%\help"

REM run jsdoc
call jsdoc -R readme.md %1 -c "%CD%\jsdoc.conf" -d "%CD%\help"

dir help

pause