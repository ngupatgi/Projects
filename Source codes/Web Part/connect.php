<?php 
require 'cresidentials1235.php';

$conid=$_GET["myid"];
$username=$_GET["username"];


$res=$conne->query("select power from manager where connection_id='".$conid."'");

while($me=mysqli_fetch_array($res)){
	if($me[0]==0){
		$conne->query("update manager set power=1,client='$username' where connection_id='".$conid."'");
		echo "Success";
	}
	else
		echo "failed";
}

?>