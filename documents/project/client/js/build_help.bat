@echo off

REM set js=..\..\..\..\Project\Server\dominion\WebContent\assets\js\
set js=..\..\..\..\project\html-frontend2\assets\js\
set doc=%CD%\

REM remove help in this folder (if any)
if exist "%js%help" rmdir /s /q "%js%help"
if exist "%doc%help" rmdir /s /q "%doc%help"

REM go to folder with help files
pushd %cd%
cd %js%

REM run jsdoc
call jsdoc ./ -R readme.md %1 -c "%doc%jsdoc.conf" -d "%doc%help"

REM go back to the old folder
popd

dir help

pause