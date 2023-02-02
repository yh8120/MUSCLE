function addTrainingSetRow() {
	let allayNum = $('#trainingSetListBody tr:last-child .trainingSetOrder').text();
	if (!allayNum) allayNum = 0;

	const clone = $($('#trainingSetTemplate').html());
	clone.find('.trainingSetOrderForm').attr('name', `trainingSetList[${allayNum}].order`);
	clone.find('.trainingSetWeight').attr('name', `trainingSetList[${allayNum}].weight`);
	clone.find('.trainingSetRep').attr('name', `trainingSetList[${allayNum}].rep`);
	clone.find('.trainingSetOneRepMaxForm').attr('name', `trainingSetList[${allayNum}].oneRepMax`);
	allayNum++;
	clone.find('.trainingSetOrder').text(`${allayNum}`);
	clone.find('.trainingSetOrderForm').val(`${allayNum}`);
	clone.find('.trainingSetRep').change(function() {
		const rep = clone.find('.trainingSetRep').val();
		const weight = clone.find('.trainingSetWeight').val();
		let oneRepMax = Math.round((weight * rep / 40 + Number(weight)) * 10) / 10;
		clone.find('.trainingSetOneRepMax').text(oneRepMax);
		clone.find('.trainingSetOneRepMaxForm').val(oneRepMax);
	});
	clone.find('.trainingSetWeight').change(function() {
		const rep = clone.find('.trainingSetRep').val();
		const weight = clone.find('.trainingSetWeight').val();
		let oneRepMax = Math.round((weight * rep / 40 + Number(weight)) * 10) / 10;
		clone.find('.trainingSetOneRepMax').text(oneRepMax);
		clone.find('.trainingSetOneRepMaxForm').val(oneRepMax);
	});

	$('#trainingSetListBody').append(clone);

}

function removeTrainingSetRow() {
	let allayNum = $('#trainingSetListBody tr:last-child .trainingSetOrder').text();
	if (allayNum != 1) {
		$('#trainingSetListBody tr:last-child').remove();
	}
}
$(document).ready(function() {
	$('#addTrainingSet').click(addTrainingSetRow);
	$('#removeTrainingSet').click(removeTrainingSetRow);
	$('.trainingSetRep').change(function() {
		const rep = $(this).val();
		const weight = $('.trainingSetWeight').val();
		let oneRepMax = Math.round((weight * rep / 40 + Number(weight)) * 10) / 10;
		$('.trainingSetOneRepMax').text(oneRepMax);
	});
	
	if (!$('#trainingSetListBody tr:last-child .trainingSetOrder').text()) {
		$('#addTrainingSet').trigger("click");
	}
});
