$(document).ready(function() {

	$( window ).bind('keypress', function(e) {

		if (e.keyCode == 13 && $('.registration-panel').length > 0) {
			$('.submit-nickname').trigger('click');
		}

	});

	var globalRecordNames = [];

	$('body').on('click', '.submit-nickname', function() {

		$('section.loader').fadeIn(500).css('display', 'table-cell');
		var nickname = $('input[name=user_name]').val();

		if (nickname == '') alert('Please enter a valid nickname.');
		else $.ajax({
			url: './php/getUser.php',
			data: { username: nickname },
			dataType: 'json',
			success: function(data) {
				globalRecordNames = data.record_name;
				if (globalRecordNames.length > 0) {
					$('.record-name').text(globalRecordNames[0].record_name);
					$('.username').text(data.user_name);

					if (data.new_user)
						$('.remaining-records').text('Remaining records: ' + data.remaining_records[0][0]);
					else $('.remaining-records').text('Remaining records: ' +  data.user_data[0][1]);
					
					changeRecord(globalRecordNames[0].record_name);
					globalRecordNames.shift();
					
					$('.registration-panel').fadeOut(500);
					setTimeout(function() {
						$('section.loader').fadeOut(500);
					}, 500);
					setTimeout(function() {
						$('.registration-panel').remove();
						$('.records-panel').fadeIn(500).css('display', 'table-cell');
					}, 1000);
				} else {
					$('.registration-panel').fadeOut(500);
					setTimeout(function() {
						$('section.loader').fadeOut(500);
					}, 500);
					setTimeout(function() {
						$('.registration-panel').remove();
						$('.congratulations-panel').fadeIn(500).css('display', 'table-cell');
					}, 1000);
				}
				
			},
			error: function(err) {
				console.log(err.responseText);
			}
		})

	});

	$('body').on('click', '.emotion', function() {

		$('.active-emotion').removeClass('active-emotion');
		$(this).addClass('active-emotion');

		if ($('.active-color').data('color') == 'black') {
			$('.emotion:not(.active-emotion)').css('background-color', 'transparent');
			$('.emotion:not(.active-emotion)').css('color', '#000');
		} else if ($('.active-color').data('color') == 'blue') {
			$('.emotion:not(.active-emotion)').css('background-color', 'transparent');
			$('.emotion:not(.active-emotion)').css('color', '#fff');
		} else if ($('.active-color').data('color') == 'purple') {
			$('.emotion:not(.active-emotion)').css('background-color', 'transparent');
			$('.emotion:not(.active-emotion)').css('color', '#fff');
		}

	});

	$('body').on('click', '.play-record', function() {

		$('#record-player').trigger('play');

	})

	$('body').on('click', '.next-record', function() {

		if ($('.active-emotion').length < 1) alert('Please select an emotion first.');
		else {
			var data = {};
			
			data.emotion = $('.active-emotion').text();
			$('.active-emotion').removeClass('active-emotion');

			if ($('.active-color').data('color') == 'black') {
				$('.emotion:not(.active-emotion)').css('background-color', 'transparent');
				$('.emotion:not(.active-emotion)').css('color', '#000');
			} else if ($('.active-color').data('color') == 'blue') {
				$('.emotion:not(.active-emotion)').css('background-color', 'transparent');
				$('.emotion:not(.active-emotion)').css('color', '#fff');
			} else if ($('.active-color').data('color') == 'purple') {
				$('.emotion:not(.active-emotion)').css('background-color', 'transparent');
				$('.emotion:not(.active-emotion)').css('color', '#fff');
			}

			data.username = $('.username').text();
			data.recordName = $('.record-name').text();

			$.ajax({
				url: './php/pickEmotion.php',
				data: data,
				dataType: 'json',
				success: function(data) {
					if (globalRecordNames.length > 0) {
						$('.record-name').text(globalRecordNames[0].record_name);
						changeRecord(globalRecordNames[0].record_name);
						globalRecordNames.shift();

						$('.remaining-records').text('Remaining records: ' + (parseInt($('.remaining-records').text().split(" ")[2]) - 1));
						$('.minus-one').fadeIn(500);
						$('.minus-one').css('bottom', '50px');
						setTimeout(function() {
							$('.minus-one').fadeOut(500);
						}, 1000);
						setTimeout(function() {
							$('.minus-one').css('bottom', '10px');
						}, 1600);
					} else {
						$('.records-panel').fadeOut(500);
						setTimeout(function() {
							$('.records-panel').remove();
							$('.congratulations-panel').fadeIn(500).css('display', 'table-cell');
						}, 600);
					}
				},
				error: function(err) {
					console.log(err.responseText);
				}
			})
		}

	});
		
	$('body').on('click', '.color', function() {
		$('.active-emotion').removeClass('active-emotion');
		$('.button-style').css('background-color', 'transparent');
	})

	$('body').on('click', '.color.black', function() {

		$('.active-color').removeClass('active-color');
		$(this).addClass('active-color');

		$('.button-style').css('transition', 'none');

		setTimeout(function() {

			$('body').css('background', '#fff');
			$('body').css('color', '#000');

			$('.button-style').css('border-color', '#000');
			$('.button-style:not(.active-emotion)').css('color', '#000');

			$('input').css('border-color', '#000');

			$('.button-style').hover(function() {
				$(this).css('background-color', '#000');
				$(this).css('color', '#fff');
			}, function() {
				if (!$(this).hasClass('active-emotion')) {
					$(this).css('background-color', 'transparent');
					$(this).css('color', '#000');
				}
			});

		}, 100)

		setTimeout(function() {
			$('.button-style').css('transition', 'all .3s ease-in-out');
		}, 200)
		
	});

	$('body').on('click', '.color.blue', function() {

		$('.active-color').removeClass('active-color');
		$(this).addClass('active-color');

		$('.button-style').css('transition', 'none');

		setTimeout(function() {

			$('body').css('background', 'linear-gradient(135deg,  #d4e4ef 0%,#86aecc 100%)');
			$('body').css('color', '#fff');

			$('.button-style').css('border-color', '#fff');
			$('.button-style:not(.active-emotion)').css('color', '#fff');

			$('input').css('border-color', '#fff');

			$('.button-style').hover(function() {
				$(this).css('background-color', '#fff');
				$(this).css('color', '#81abca');
			}, function() {
				if (!$(this).hasClass('active-emotion')) {
					$(this).css('background-color', 'transparent');
					$(this).css('color', '#fff');
				}
			});

		}, 100)

		setTimeout(function() {
			$('.button-style').css('transition', 'all .3s ease-in-out');
		}, 200)

	});

	$('body').on('click', '.color.purple', function() {

		$('.active-color').removeClass('active-color');
		$(this).addClass('active-color');

		$('.button-style').css('transition', 'none');

		setTimeout(function() {

			$('body').css('background', 'linear-gradient(135deg,  #606c88 0%,#3f4c6b 100%)');
			$('body').css('color', '#fff');

			$('.button-style').css('border-color', '#fff');
			$('.button-style:not(.active-emotion)').css('color', '#fff');

			$('input').css('border-color', '#fff');

			$('.button-style').hover(function() {
				$(this).css('background-color', '#fff');
				$(this).css('color', '#606c88');
			}, function() {
				if (!$(this).hasClass('active-emotion')) {
					$(this).css('background-color', 'transparent');
					$(this).css('color', '#fff');
				}
			});

		})

		setTimeout(function() {
			$('.button-style').css('transition', 'all .3s ease-in-out');
		}, 200)

	});

});

function changeRecord(rec) {

	var recordPlayer = $('#record-player');

	$('#wav-src').attr('src', './records/' + rec);
	recordPlayer[0].pause();
	recordPlayer[0].load();

}