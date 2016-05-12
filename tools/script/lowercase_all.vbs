Const HOST = "CSCRIPT.EXE"
If UCase(Right(WScript.FullName, 11)) <> HOST Then
	Set ws = CreateObject("WScript.Shell")
	ws.Run HOST & " """ & WScript.ScriptFullName & """"
	WScript.Quit
End If

Set fso = CreateObject("Scripting.FileSystemObject")

If Not fso.FolderExists(WScript.Arguments(0)) Then
	WScript.Echo "Script needs a folder argument..."
	WScript.StdIn.ReadLine()
	WScript.Quit
End If

Set folder = fso.GetFolder(WScript.Arguments(0))
For Each file In folder.Files
	If Left(file.Name, 1) <> "_" Then file.Name = "_" & file.Name
	WScript.Echo file.Name
Next

Set folder = fso.GetFolder(WScript.Arguments(0))
For Each file In folder.Files
	file.Name = LCase(Mid(file.Name, 2))
	WScript.Echo file.Name
Next

WScript.Echo "Press enter..."
WScript.StdIn.ReadLine()