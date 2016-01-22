<!DOCTYPE html>
<html>
	
	<head>
		<title>Artificial Inteligence - Voice Recognition</title>
		<link href="libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type='text/css'>
		<link href='css/styles.css' rel='stylesheet' type='text/css'>

		<script src="libs/jquery/jquery-2.1.1.js" type='text/javascript'></script>
	</head>

	<body>

		<?php
			// $servername = "localhost";
			// $username = "root";
			// $password = "";

			$servername = "fenrir";
			$username = "aivr";
			$password = "ggCfSwz0Th";

			try {
		    	$conn = new PDO("mysql:host=$servername;dbname=aivr", $username, $password);
		   		$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		    } catch (PDOException $e) {
		    	echo "Connection failed: " . $e->getMessage();
		    }
		?>

		<section class='loader'>
			<img src='loading.gif'>
		</section>

		<section class='colors-panel'>
			<span class='color black' data-color='black'></span>
			<span class='color blue' data-color='blue'></span>
			<span class='color purple' data-color='purple'></span>
		</section>

		<section class='audio-panel'>
			<audio controls id='record-player'>
				<source id='wav-src' type='audio/wav' src=''>
			</audio>
		</section>

		<section class='registration-panel'>
			<input type='text' name='user_name'>
			<span class='button-style submit-nickname'>submit nickname</span>
			<p>Enter a nickname. Based on it, you will continue where you left off or start fresh.</p>
		</section>

		<section class='records-panel'>
			<section class='user-data'>
				<p class='username'></p>
				<p class='remaining-records'></p>
				<span class='minus-one'>-1</span>
			</section>
			
			<div>
				<span class='button-style play-record'>play record</span>
				<span class='record-name'></span>
				<span class='button-style next-record'>next record</span>
			</div>
			
			<section class='emotions-panel'>
				<article class='left-side'>
					<span class='button-style emotion'>neutral</span>
					<span class='button-style emotion'>joy</span>
					<span class='button-style emotion'>sadness</span>
					<span class='button-style emotion'>fury</span>
				</article>
				<article class='right-side'>
					<span class='button-style emotion'>anxiety</span>
					<span class='button-style emotion'>boredom</span>
					<span class='button-style emotion'>disgust</span>
					<span class='button-style emotion'>don't know</span>
				</article>
			</section>
		</section>

		<section class='congratulations-panel'>
			<p>Congratulations, you are done !</p>
		</section>

	</body>

	<script src="js/main.js"></script>
</html>