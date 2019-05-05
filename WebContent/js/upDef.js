

    function saveSchedule() {
		var optionsOpt = {rowKeyOnly: false};
		var modifiedRowsInfo = grid.getModifiedRows(optionsOpt);
		var createdRowsLength = modifiedRowsInfo.createdRows.length;
		var updatedRowsLength = modifiedRowsInfo.updatedRows.length;
		var deletedRowsLength = modifiedRowsInfo.deletedRows.length;
		
		var resultJsonObject = new Object();
		if (createdRowsLength != 0) {
			var createdJsonArray = new Array();
			for (var i = 0; i < createdRowsLength; i++) {
				var rowKey = modifiedRowsInfo.createdRows[i];
				var rowData = grid.getRowAt(grid.getIndexOfRow(rowKey, false), true);
				
				createdJsonArray.push(rowData);		
			}
			resultJsonObject.Created = createdJsonArray;
		}
		if (updatedRowsLength != 0) {
			var updatedJsonArray = new Array();
			for (var i = 0; i < updatedRowsLength; i++) {
				var rowKey = modifiedRowsInfo.updatedRows[i];
				var rowData = grid.getRowAt(grid.getIndexOfRow(rowKey, false), true);
				
				updatedJsonArray.push(rowData);              
			}
			resultJsonObject.Updated = updatedJsonArray;
		}
		if (deletedRowsLength != 0) {
			var deletedJsonArray = new Array();
			for (var i = 0; i < deletedRowsLength; i++) {
				var idx = modifiedRowsInfo.deletedRows[i].idx;
				deletedJsonArray.push(idx);
				//console.log("delete: " + rowKey);
			}
			resultJsonObject.Deleted = deletedJsonArray;
		}
		var resultJsonString = JSON.stringify(resultJsonObject);
		console.log("JsonString: " + resultJsonString);
		return resultJsonString;
	}

//function saveSchedule() {
//	var optionsOpt = {rowKeyOnly: false};
//	var modifiedRowsInfo = gMyGrid.getModifiedRows(optionsOpt);
//	var createdRowsLength = modifiedRowsInfo.createdRows.length;
//	var updatedRowsLength = modifiedRowsInfo.updatedRows.length;
//	var deletedRowsLength = modifiedRowsInfo.deletedRows.length;
//	
//	var resultJsonObject = new Object();
//	if (createdRowsLength != 0) {
//		var createdJsonArray = new Array();
//		for (var i = 0; i < createdRowsLength; i++) {
//			var rowKey = modifiedRowsInfo.createdRows[i];
//			var rowData = gMyGrid.getRowAt(gMyGrid.getIndexOfRow(rowKey, false), false);
//			var rowJsonObject = makeJsonObject(rowData);
//			createdJsonArray.push(rowJsonObject);
//		}
//		resultJsonObject.Created = createdJsonArray;
//	}
//	if (updatedRowsLength != 0) {
//		var updatedJsonArray = new Array();
//		for (var i = 0; i < updatedRowsLength; i++) {
//			var rowKey = modifiedRowsInfo.updatedRows[i];
//			var rowData = gMyGrid.getRowAt(gMyGrid.getIndexOfRow(rowKey, false), false);
//			var rowJsonObject = makeJsonObject(rowData);
//			updatedJsonArray.push(rowJsonObject);
//		}
//		resultJsonObject.Updated = updatedJsonArray;
//	}
//	if (deletedRowsLength != 0) {
//		var deletedJsonArray = new Array();
//		for (var i = 0; i < deletedRowsLength; i++) {
//			var idx = modifiedRowsInfo.deletedRows[i].idx;
//			deletedJsonArray.push(idx);
//		}
//		resultJsonObject.Deleted = deletedJsonArray;
//	}
//	var resultJsonString = JSON.stringify(resultJsonObject);
//	console.log("JsonString: " + resultJsonString);
//}
//
//function makeJsonObject(rowData) {
//	var rowInfo = new Object();
//	if (typeof rowData.audio.valueOf() == "string") {
//		...
//	} else {
//		for (var i = 0; i < rowData.audio.length; i++) {
//			if (i == 0)
//				rowInfo.audio = rowData.audio[i];
//			else
//				rowInfo.audio += "," + rowData.audio[i];
//		}
//	}
//	if (typeof rowData.caption.valueOf() == "string") {
//		...
//	} else {
//		for (var i = 0; i < rowData.caption.length; i++) {
//			if (i == 0)
//				rowInfo.caption = rowData.caption[i];
//			else
//				rowInfo.caption += "," + rowData.caption[i];
//		}
//	}
//	return rowInfo;
//}