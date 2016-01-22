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

    $stmt = $conn->prepare("INSERT INTO user_records (user_name, record_name, emotion) VALUES (:name, :recName, :emotion)");
    $stmt->bindParam(':name', $name);
    $stmt->bindParam(':recName', $recName);
    $stmt->bindParam(':emotion', $emotion);
    $name = $_GET['username'];
    $recName = $_GET['recordName'];
    $emotion = $_GET['emotion'];
    $stmt->execute();

    $stmt = $conn->prepare("UPDATE users SET remaining_records = remaining_records - 1 WHERE user_name LIKE :name");
    $stmt->bindParam(':name', $name);
    $stmt->execute();

    echo json_encode("message: done");

?>