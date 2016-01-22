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

    // ##################### Code to populate records table with names form records folder #####################

	// $stmt = $conn->prepare("INSERT INTO records (record_name) VALUES (:name)");
	// $stmt->bindParam(':name', $name);

 	// $dir = './records';
	// $files = scandir($dir);

	// foreach ($files as $file) {
	// 	if ($file != "." && $file != "..") {
	// 		$name = $file;
	// 		$stmt->execute();
	// 	}
	// }

	// ##################### Code to populate records table with names form records folder #####################

?>