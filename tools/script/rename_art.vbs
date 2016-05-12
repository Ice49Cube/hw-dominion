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
	offset = InStr(file.Name, "Art.jpg")
	If offset <> 0 Then
		file.Name = LCase(Left(file.Name, offset - 1) & "_" & Mid(file.Name, offset))
	End If	
Next

WScript.Echo "Press enter..."
WScript.StdIn.ReadLine()