

function loadJSON(filename) {
	return JSON.parse(loadFile(filename));
}

function loadFile(filename) {
	var fso = new ActiveXObject("Scripting.FileSystemObject");
	var stream = fso.OpenTextFile(filename);
	var result = stream.ReadAll();
	stream.Close();
	stream = null;
	fso = null;
	return result;
}