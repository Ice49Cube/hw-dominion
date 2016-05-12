@Echo Off

If Exist "%VS140COMNTOOLS%\vsvars32.bat" GoTo VS140
If Exist "%VS120COMNTOOLS%\vsvars32.bat" GoTo VS120
If Exist "%VS100COMNTOOLS%\vsvars32.bat" GoTo VS100
If Exist "%VS90COMNTOOLS%\vsvars32.bat" GoTo VS90
If Exist "%VS80COMNTOOLS%\vsvars32.bat" GoTo VS80
GoTo VSHELL

REM ###########################################################################

:VS140
Call "%VS140COMNTOOLS%\vsvars32.bat"
GoTo Compile
:VS120
Call "%VS120COMNTOOLS%\vsvars32.bat"
GoTo Compile
:VS100
Call "%VS100COMNTOOLS%\vsvars32.bat"
GoTo Compile
:VS90
Call "%VS90COMNTOOLS%\vsvars32.bat"
GoTo Compile
:VS80
Call "%VS80COMNTOOLS%\vsvars32.bat"
GoTo Compile

REM ###########################################################################

:Compile
If Exist "%1" (
	CD "%~d1%~p1"
	csc /target:exe "%1"
	GoTo THEEND
)

REM ###########################################################################

:NOOB
Echo Pass a c# program as commandline argument , ...
GoTo THEEND

REM ###########################################################################

:VSHELL
Echo No Visual Studio , ... Check bat file
GoTo THEEND

REM ###########################################################################

:THEEND
pause