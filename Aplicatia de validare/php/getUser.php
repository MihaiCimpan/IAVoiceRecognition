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

    $stmt = $conn->prepare("SELECT user_name, remaining_records FROM users WHERE user_name LIKE :name");
    $stmt->bindParam(':name', $name);
    $name = $_GET['username'];
    $stmt->execute();

    $resultObj = new stdClass;
    $resultObj->user_name = $name;
    $resultObj->new_user = true;

    $result = $stmt->fetchAll();

    if (count($result) > 0) {
    	$stmt = $conn->prepare("SELECT record_name FROM records WHERE record_name NOT IN (SELECT record_name FROM user_records WHERE user_name LIKE :name) ORDER BY RAND()");
    	$stmt->bindParam(':name', $name);
    	$name = $_GET['username'];
	    $stmt->execute();
	    $resultObj->new_user = false;
	    $resultObj->user_data = $result;
	    $resultObj->record_name = $stmt->fetchAll();

	    echo json_encode($resultObj);
    } else {
    	$stmt = $conn->prepare("INSERT INTO users (user_name, remaining_records) VALUES (:name, (SELECT COUNT(*) FROM records))");
	    $stmt->bindParam(':name', $name);
	    $name = $_GET['username'];
	    $stmt->execute();

	    $stmt = $conn->prepare("SELECT record_name FROM records ORDER BY RAND()");
	    $stmt->execute();
	    $resultObj->record_name = $stmt->fetchAll();

	    $stmt = $conn->prepare("SELECT COUNT(*) FROM records");
	    $stmt->execute();
	    $resultObj->remaining_records = $stmt->fetchAll();

	    echo json_encode($resultObj);
    }

?>